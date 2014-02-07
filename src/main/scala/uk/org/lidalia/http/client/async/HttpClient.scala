package uk.org.lidalia.http.client.async

import uk.org.lidalia.http
import scala.concurrent.Future
import uk.org.lidalia.http.{Response, Request}

trait HttpClient {
  def execute(request: Request): Future[Response] = ???
}
