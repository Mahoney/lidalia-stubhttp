package uk.org.lidalia.http.client

import uk.org.lidalia.http.core.{EitherEntity, Request, Response}
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
      val eitherEntity = response.marshallableEntity.asInstanceOf[EitherEntity[String, A]]
      eitherEntity.eitherEntity match {
        case Left(error) => throw new Exception(Response(response.header, error).toString)
        case Right(success) => Response(response.header, success)
      }
    })
  }
}
