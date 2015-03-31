package uk.org.lidalia.http.client

import uk.org.lidalia.http.core.Response

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

class ThrowServerErrorHttpClient(decorated: HttpClient) extends HttpClient {

  def execute[A](request: DirectedRequest[A]): Future[Response[Either[String, A]]] = {
    val futureResponse = decorated.execute(request)
    futureResponse.map(response => {
      if (response.isServerError) throw ServerError(response)
      else response
    })
  }
}
