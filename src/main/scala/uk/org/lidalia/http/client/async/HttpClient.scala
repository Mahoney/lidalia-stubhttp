package uk.org.lidalia.http.client.async

import uk.org.lidalia.http
import http.response.Response
import scala.concurrent.Future
import uk.org.lidalia.http.request.Request

trait HttpClient {
  def execute(request: Request): Future[Response] = ???
}
