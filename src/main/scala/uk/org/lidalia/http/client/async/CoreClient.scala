package uk.org.lidalia.http.client

import uk.org.lidalia
import lidalia.http
import org.apache

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

import lidalia.net2.Target
import uk.org.lidalia.http.core._

import apache.http.{HttpResponse, HttpHost}
import org.apache.http.impl.client.{CloseableHttpClient, HttpClientBuilder}
import apache.http.message.BasicHttpRequest
import apache.http.client.{ResponseHandler => ApacheResponseHandler}

class CoreClient(apacheClient: CloseableHttpClient = HttpClientBuilder.create()
    .setMaxConnPerRoute(Integer.MAX_VALUE)
    .setMaxConnTotal(Integer.MAX_VALUE)
    .build()) extends TargetedHttpClient {

  override def execute[T](targetedRequest: TargetedRequest[T]): Future[Response[T]] = {
    Future {

      val target = targetedRequest.target
      val host = new HttpHost(
        target.ipAddress.toString,
        target.port.portNumber,
        targetedRequest.scheme.toString
      )

      val request = targetedRequest.request
      val apacheRequest = new BasicHttpRequest(
        request.method.toString,
        request.requestUri.toString
      )
      
      val apacheResponseHandler = new ApacheResponseHandler[Response[T]] {
        def handleResponse(response: HttpResponse): Response[T] = {
          val headerFields = response.getAllHeaders.map{
            headerField => HeaderField(headerField.getName, headerField.getValue)
          }.toList

          val responseHeader = ResponseHeader(
            Code(response.getStatusLine.getStatusCode),
            Reason(response.getStatusLine.getReasonPhrase),
            headerFields
          )
          val entity = targetedRequest.unmarshaller.unmarshal(targetedRequest, responseHeader, response.getEntity.getContent)
          Response(responseHeader, entity)
        }
      }

      apacheClient.execute(
        host, 
        apacheRequest, 
        apacheResponseHandler
      )
    }
  }
}
