package uk.org.lidalia.http.client

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global
import uk.org.lidalia.http.core.{RequestUri, Response}

class RedirectFollowingClient(decorated: HttpClient) extends HttpClient {

  override def execute[T](request: DirectedRequest[T]): Future[Response[T]] = {
    val initialResponse = decorated.execute(request)
    initialResponse.flatMap { response =>
      if (response.requiresRedirect) {
        response.location.flatMap(location => {
          execute(
            new DirectedRequest(
              location.scheme,
              location.hostAndPort.get,
              request.request.withUri(RequestUri(location.pathAndQuery)),
              request.unmarshaller
            )
          )
        }).getOrElse(initialResponse)
      } else {
        initialResponse
      }
    }
  }
}
