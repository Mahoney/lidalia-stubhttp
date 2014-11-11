package uk.org.lidalia.http.client.async

import uk.org.lidalia
import lidalia.http
import org.apache

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

import lidalia.net2.Target
import http.core.{HeaderField, Code, Response, Request}

import apache.http.{HttpResponse, HttpHost}
import org.apache.http.impl.client.{CloseableHttpClient, HttpClientBuilder}
import apache.http.message.BasicHttpRequest
import apache.http.client.{ResponseHandler => ApacheResponseHandler}

class CoreClient(apacheClient: CloseableHttpClient = HttpClientBuilder.create()
    .setMaxConnPerRoute(Integer.MAX_VALUE)
    .setMaxConnTotal(Integer.MAX_VALUE)
    .build()) {

  def execute[T](request: Request[T],
                 target: Target): Future[Response[T]] = {
    Future {
      val host = new HttpHost(target.ipAddress.toString, target.port.portNumber)
      val apacheRequest = new BasicHttpRequest(request.method.toString, request.requestUri.toString)
      val apacheResponseHandler = new ApacheResponseHandler[(HttpResponse, T)] {
        def handleResponse(response: HttpResponse): (HttpResponse, T) = (response, request.responseHandler.handle(response.getEntity.getContent))
      }
      val apacheResponseHeaderAndEntity = apacheClient.execute(host, apacheRequest, apacheResponseHandler)
      val apacheResponseHeader = apacheResponseHeaderAndEntity._1
      val entity = apacheResponseHeaderAndEntity._2
      val headerFields = apacheResponseHeader.getAllHeaders.map( headerField => HeaderField(headerField.getName, headerField.getValue)).toList
      Response(Code(apacheResponseHeader.getStatusLine.getStatusCode), headerFields, entity)
    }
  }
}
