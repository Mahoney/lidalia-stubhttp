package uk.org.lidalia.http.client

import uk.org.lidalia.http.client.async.FutureHttpClient
import uk.org.lidalia.http.core.{AnyEntity, Request, Response}
import uk.org.lidalia.net2.Url

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

object ExpectedEntityHttpClient {
  type FutureResponse[T] = Future[Response[T]]

  def apply(
    baseUrl: Url
  ): HttpClient[FutureResponse] = {
    new ExpectedEntityHttpClient(
      new ThrowClientErrorHttpClient(
        new ThrowServerErrorHttpClient(
            new Apache4Client(baseUrl)
        )
      )
    )
  }
}

class ExpectedEntityHttpClient(decorated: RawHttpClient) extends FutureHttpClient[Response] {

  def execute[A](request: Request[A, _]): Future[Response[A]] = {
    val futureResponse = decorated.execute(request)
    futureResponse.map(response => {
      response.entity match {
        case Left(error) => throw new Exception(Response(response.header, error).toString)
        case Right(success) => Response(response.header, new AnyEntity(success))
      }
    })
  }
}
