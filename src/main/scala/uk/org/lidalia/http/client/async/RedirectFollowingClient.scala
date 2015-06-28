package uk.org.lidalia.http.client

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global
import uk.org.lidalia.http.core.Response

class RedirectFollowingClient(decorated: HttpClient) extends HttpClient {

  override def execute[T](request: DirectedRequest[T]): Future[Response[Either[String, T]]] = execute(request, List())

  private def execute[T](request: DirectedRequest[T], history: List[DirectedRequest[T]]): Future[Response[Either[String, T]]] = {

    val initialResponse = decorated.execute(request)

    def followRedirect(response: Response[Either[String, T]]) =
      response.location.map(location => {
        if (history.contains(request)) {
          throw InfiniteRedirectException(response, request.request)
        } else {
          execute(request.redirected(location), request :: history)
        }
      }).getOrElse(initialResponse)

    initialResponse.flatMap { response =>
      if (!response.requiresRedirect) {
        initialResponse
      } else {
        followRedirect(response)
      }
    }
  }
}
