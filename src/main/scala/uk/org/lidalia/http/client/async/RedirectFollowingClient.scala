package uk.org.lidalia.http.client.async

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global
import uk.org.lidalia.http.{Response, Request}

class RedirectFollowingClient(decorated: HttpClient) extends HttpClient {

  override def execute(request: Request): Future[Response] = {
    val initialResponse = decorated.execute(request)
     initialResponse.flatMap { response =>
      if (response.requiresRedirect) {
        val redirectRequest = request.withUri(response.location.get)
        execute(redirectRequest)
      } else {
        initialResponse
      }
    }
  }
}
