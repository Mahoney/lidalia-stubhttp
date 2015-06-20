package uk.org.lidalia.http.client

import uk.org.lidalia.net2.Url

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global
import uk.org.lidalia.http.core.Response

class RedirectFollowingClient(decorated: HttpClient) extends HttpClient {

  override def execute[T](request: DirectedRequest[T]): Future[Response[Either[String, T]]] = execute(request, List())

  private def execute[T](request: DirectedRequest[T], history: List[Url]): Future[Response[Either[String, T]]] = {
    val initialResponse = decorated.execute(request)

    def followRedirect(response: Response[Either[String, T]]) =
      response.location.map(location => {
        if (history.contains(location)) {
          throw InfiniteRedirectException(response, request.request)
        } else {
          execute(request.forUrl(location), location :: history)
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
