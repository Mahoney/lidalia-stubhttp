package uk.org.lidalia.http.client.async

import uk.org.lidalia.http

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global
import http.response.Response
import http.response.headerfields.Location
import uk.org.lidalia.http.request.Request

class RedirectFollowingClient(decorated: HttpClient) extends HttpClient {

  override def execute(request: Request): Future[Response] = {
    val initialResponse = decorated.execute(request)
    initialResponse.flatMap { response =>
      if (response.requiresRedirect) {
        val redirectRequest = request.withUri(response.headerField(Location).get)
        decorated.execute(redirectRequest)
      } else {
        initialResponse
      }
    }
  }
}
