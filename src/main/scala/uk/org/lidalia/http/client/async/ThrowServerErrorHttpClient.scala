package uk.org.lidalia.http.client

import uk.org.lidalia.http.core.{Request, Response}

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

class ThrowServerErrorHttpClient(decorated: RawHttpClient) extends RawHttpClient {

  def execute[A](request: Request[A, _]): Future[Response[Either[String, A]]] = {
    val futureResponse = decorated.execute(request)
    futureResponse.map(response => {
      if (response.isServerError) throw ServerError(response, request)
      else response
    })
  }
}
