package uk.org.lidalia.http.client

import java.io.{ByteArrayOutputStream, InputStream}
import java.net.InetAddress

import com.google.common.base.Charsets
import org.apache.commons.io.IOUtils
import uk.org.lidalia
import lidalia.http
import org.apache
import uk.org.lidalia.net2.{Url, IpAddress}

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

import uk.org.lidalia.http.core._

import apache.http.{HttpResponse, HttpHost}
import apache.http.impl.client.{CloseableHttpClient, HttpClientBuilder}
import apache.http.message.BasicHttpRequest
import apache.http.client.{ResponseHandler => ApacheResponseHandler}

class Apache4Client(
   target: Url,
   apacheClient: CloseableHttpClient = HttpClientBuilder.create()
    .setMaxConnPerRoute(Integer.MAX_VALUE)
    .setMaxConnTotal(Integer.MAX_VALUE)
    .disableRedirectHandling()
    .build()
) extends HttpClient {

  private def marshallerFor[T](marshaller: EntityMarshaller[T]) = {
    new EntityMarshaller[Either[String, T]] {
      override def marshal(header: MessageHeader, entity: Either[String, T]): InputStream = {
        entity match {
          case Left(e) => StringEntityMarshaller.marshal(header, e)
          case Right(e) => marshaller.marshal(header, e)
        }
      }
    }
  }

  override def execute[T](request: Request[T, _]): Future[Response[Either[String, T]]] = {
    Future {

      val apacheHost = new HttpHost(
        IpAddress(InetAddress.getByName(target.host.toString)).toString,
        target.resolvedPort.portNumber,
        target.scheme.toString
      )

      val apacheRequest = new BasicHttpRequest(
        request.method.toString,
        request.requestUri.toString
      )
      
      val apacheResponseHandler = new ApacheResponseHandler[Response[Either[String, T]]] {



        def handleResponse(response: HttpResponse): Response[Either[String, T]] = {
          val headerFields = response.getAllHeaders.map{
            headerField => HeaderField(headerField.getName, headerField.getValue)
          }.toList

          val responseHeader = ResponseHeader(
            Code(response.getStatusLine.getStatusCode),
            Reason(response.getStatusLine.getReasonPhrase),
            headerFields
          )
          val entity = unmarshal(request, response, responseHeader, request.unmarshaller)
          Response(responseHeader, marshallerFor(request.unmarshaller.marshaller), entity)
        }
      }

      apacheClient.execute(
        apacheHost,
        apacheRequest,
        apacheResponseHandler
      )
    }
  }

  def unmarshal[T](request: Request[T, _], response: HttpResponse, responseHeader: ResponseHeader, unmarshaller: EntityUnmarshaller[T]): Either[String, T] = {

    val content = new CapturingInputStream(response.getEntity.getContent)
    try {
      Right(unmarshaller.unmarshal(request, responseHeader, content))
    } catch {
      case e: Exception =>
        val array: Array[Byte] = content.captured.toByteArray
        val charset = responseHeader.contentType.flatMap(_.charset).getOrElse(Charsets.UTF_8)
        Left(IOUtils.toString(array, charset.name()))
    }
  }
}

class CapturingInputStream(decorated: InputStream, maxSize: Int = 1024 * 512) extends InputStream {

  var firstByte: ?[Int] = None
  var firstByteRead = false

  val captured = new ByteArrayOutputStream(maxSize)

  override def read(): Int = {
    if (firstByteRead) {
      doRead()
    } else {
      firstByteRead = true
      getFirstByte
    }
  }

  def doRead(): Int = {
    val result = decorated.read()
    if (result >= 0 && captured.size() < maxSize)
      captured.write(result)
    result
  }

  def isEmpty = getFirstByte == -1

  private def getFirstByte = {
    if (firstByte.isEmpty) {
      firstByte = Some(doRead())
    }
    firstByte.get
  }
}
