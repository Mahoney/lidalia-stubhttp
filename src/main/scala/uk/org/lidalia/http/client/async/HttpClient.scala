package uk.org.lidalia.http.client

import uk.org.lidalia.http.core.Method.GET
import uk.org.lidalia.net2.Uri
import scala.concurrent.Future
import uk.org.lidalia.http.core.{RequestUri, Response, Request}

trait HttpClient {

  def execute[T](request: DirectedRequest[T]): Future[Response[T]]

  def get[T](uri: Uri, accept: Accept[T]): Future[Response[T]] =
    execute(
      new DirectedRequest(
        uri.scheme,
        uri.hostAndPort.get,
        Request(GET, RequestUri(uri.pathAndQuery), List(accept)),
        accept
      )
    )
}
