package uk.org.lidalia.http.client

import uk.org.lidalia.http.core.{Method, RequestUri, Request}
import uk.org.lidalia.lang.RichObject
import uk.org.lidalia.net2.{Url, HostAndPort, Scheme}

class DirectedRequest[T](
  @Identity val scheme: Scheme,
  @Identity val hostAndPort: HostAndPort,
  @Identity val request: Request,
  val unmarshaller: EntityUnmarshaller[T],
  val parent: ?[DirectedRequest[T]] = None
  ) extends RichObject {

  def redirected(location: Url, method: Method = request.method): DirectedRequest[T] =
    new DirectedRequest(
      location.scheme,
      location.hostAndPort,
      request.withUri(RequestUri(location.pathAndQuery)).withMethod(method),
      unmarshaller,
      Some(this)
    )
}
