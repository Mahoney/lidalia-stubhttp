package uk.org.lidalia.http.client

import uk.org.lidalia.http.client.RawHttpClient.{ResponseStringOr, FutureResponseStringOr}
import uk.org.lidalia.net2.Url

import scala.concurrent.Future
import uk.org.lidalia.http.core.{Request, Response}

object RawHttpClient {
  type StringOr[T] = Either[String, T]
  type ResponseStringOr[T] = Response[StringOr[T]]
  type FutureResponseStringOr[T] = Future[ResponseStringOr[T]]

  def apply(baseUrl: Url) = Apache4Client(baseUrl)

}

trait RawHttpClient extends HttpClient[FutureResponseStringOr] with FutureHttpClient[ResponseStringOr] {
  def execute[T](request: Request[T, _]): Future[Response[Either[String, T]]]
}
