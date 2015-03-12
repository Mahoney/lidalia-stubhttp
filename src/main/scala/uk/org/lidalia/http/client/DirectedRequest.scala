package uk.org.lidalia.http.client

import uk.org.lidalia.http.core.Request
import uk.org.lidalia.lang.RichObject
import uk.org.lidalia.net2.{HostAndPort, Scheme}

class DirectedRequest[T](
        @Identity val scheme: Scheme,
        @Identity val hostAndPort: HostAndPort,
        @Identity val request: Request[T],
        @Identity val parent: ?[DirectedRequest[T]] = None
        ) extends RichObject {

}
