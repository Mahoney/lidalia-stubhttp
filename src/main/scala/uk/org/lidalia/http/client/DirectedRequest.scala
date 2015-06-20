package uk.org.lidalia.http.client

import uk.org.lidalia.http.core.{RequestUri, Request}
import uk.org.lidalia.lang.RichObject
import uk.org.lidalia.net2.{Url, HostAndPort, Scheme}

class DirectedRequest[T](
  @Identity val scheme: Scheme,
  @Identity val hostAndPort: HostAndPort,
  @Identity val request: Request,
  @Identity val unmarshaller: EntityUnmarshaller[T],
  @Identity val parent: ?[DirectedRequest[T]] = None
  ) extends RichObject {

  def forUrl(location: Url): DirectedRequest[T] =
    new DirectedRequest(
      location.scheme,
      location.hostAndPort,
      request.withUri(RequestUri(location.pathAndQuery)),
      unmarshaller
    )
}
