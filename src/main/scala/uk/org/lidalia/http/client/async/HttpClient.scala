package uk.org.lidalia.http.client.async

import uk.org.lidalia.http.core.Method.GET
import uk.org.lidalia.http.client.Accept
import uk.org.lidalia.net2.Uri
import scala.concurrent.Future
import uk.org.lidalia.http.core.{RequestUri, Method, Response, Request}

trait HttpClient {
  def execute[T](request: Request[T]): Future[Response[T]] = ???

  def get[T](uri: Uri, accept: Accept[T]): Future[Response[T]] = execute(Request(GET, RequestUri(uri), accept))
}
