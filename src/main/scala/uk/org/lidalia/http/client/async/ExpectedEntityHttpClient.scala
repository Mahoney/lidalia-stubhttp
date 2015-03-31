package uk.org.lidalia.http.client

import uk.org.lidalia.http.core.Response

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

object ExpectedEntityHttpClient {
  type FutureResponse[T] = Future[Response[T]]

  def apply(): ExpectedEntityHttpClient = new ExpectedEntityHttpClient(
    new ThrowClientErrorHttpClient(
      new ThrowServerErrorHttpClient(
        new DefaultResolvingHttpClient(
          new Apache4Client()
        )
      )
    )
  )
}

class ExpectedEntityHttpClient(decorated: HttpClient) extends BaseHttpClient[ExpectedEntityHttpClient.FutureResponse] {

  def execute[A](request: DirectedRequest[A]): Future[Response[A]] = {
    val futureResponse = decorated.execute(request)
    futureResponse.map(response => {
      response.entity match {
        case Left(error) => throw new Exception()
        case Right(success) => Response(response.responseHeader, success)
      }
    })
  }
}
