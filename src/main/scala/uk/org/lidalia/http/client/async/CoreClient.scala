package uk.org.lidalia.http.client.async

import uk.org.lidalia
import lidalia.http
import org.apache

import scala.concurrent.Future
import scala.concurrent.future
import scala.concurrent.ExecutionContext.Implicits.global

import lidalia.net2.{IpV4Address, Port}
import http.core.{HeaderField, Code, Response, Request}

import apache.http.impl.client.DefaultHttpClient
import apache.http.impl.conn.PoolingClientConnectionManager
import apache.http.{HttpResponse, HttpHost, HttpRequest}
import apache.http.client.methods.AbortableHttpRequest
import apache.http.message.BasicHttpRequest

class CoreClient {

  type ApacheResponseHandler[A] = org.apache.http.client.ResponseHandler[A]

  private val apacheClient = new DefaultHttpClient(buildPoolingClientConnectionManager())

  private def buildPoolingClientConnectionManager() = {
    val connectionManager = new PoolingClientConnectionManager()
    connectionManager.setDefaultMaxPerRoute(Integer.MAX_VALUE)
    connectionManager.setMaxTotal(Integer.MAX_VALUE)
    connectionManager
  }

  def execute[T](request: Request[T], address: IpV4Address, port: Port): Future[Response[T]] = {
    future {
      val host: HttpHost = new HttpHost(address.toString, port.portNumber)
      val apacheRequest = new BasicHttpRequest(request.method.toString, request.uri.toString)
      val apacheResponseHandler = new ApacheResponseHandler[(HttpResponse, T)] {
        def handleResponse(response: HttpResponse): (HttpResponse, T) = (response, request.responseHandler.handle(response.getEntity.getContent))
      }
      val apacheResponseHeaderAndEntity = apacheClient.execute(host, apacheRequest, apacheResponseHandler)
      val apacheResponseHeader = apacheResponseHeaderAndEntity._1
      val entity = apacheResponseHeaderAndEntity._2
      val headerFields = apacheResponseHeader.getAllHeaders.map( headerField => HeaderField(headerField.getName, headerField.getValue.split(", ").toList)).toList
      Response(Code(apacheResponseHeader.getStatusLine.getStatusCode), headerFields, entity)
    }
  }
}
