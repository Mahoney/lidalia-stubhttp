package uk.org.lidalia.http.client

import scala.concurrent.Future
import uk.org.lidalia.http.core.Response

object HttpClient {
  type FutureResponseStringOr[T] = Future[Response[Either[String, T]]]
}

trait HttpClient extends BaseHttpClient[HttpClient.FutureResponseStringOr] {
  def execute[T](request: DirectedRequest[T]): Future[Response[Either[String, T]]]
}
