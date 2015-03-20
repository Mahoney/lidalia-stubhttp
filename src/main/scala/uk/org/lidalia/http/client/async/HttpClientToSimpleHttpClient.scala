package uk.org.lidalia.http.client

import uk.org.lidalia.http.core.Response

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

class HttpClientToSimpleHttpClient(decorated: HttpClient) extends SimpleHttpClient {

  def execute[A](request: DirectedRequest[A]): Future[Response[A]] = {
    val response = decorated.execute(request)
    response.map(r => {
      val entity: Either[String, A] = r.entity
      entity match {
        case Left(error) => throw new Exception()
        case Right(success) => Response(r.responseHeader, success)
      }
    })
  }

}
