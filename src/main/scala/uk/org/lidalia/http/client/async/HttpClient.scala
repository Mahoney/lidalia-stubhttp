package uk.org.lidalia.http.client

import uk.org.lidalia.http.client.async.FutureHttpClient

import scala.concurrent.Future
import uk.org.lidalia.http.core.Response

object HttpClient {
  type StringOr[T] = Either[String, T]
  type ResponseStringOr[T] = Response[StringOr[T]]
}

trait HttpClient extends FutureHttpClient[HttpClient.ResponseStringOr] {
  def execute[T](request: DirectedRequest[T]): Future[Response[Either[String, T]]]
}
