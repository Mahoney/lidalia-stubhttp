package uk.org.lidalia.http.client.async

import uk.org.lidalia.http.core
import scala.concurrent.Future
import core.{Response, Request}

trait HttpClient {
  def execute(request: Request): Future[Response] = ???
}
