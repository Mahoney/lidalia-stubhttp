package uk.org.lidalia.http.client

import uk.org.lidalia.http.core.Request
import uk.org.lidalia.lang.RichObject
import uk.org.lidalia.net2.{Scheme, Target}

class TargetedRequest[T](
        @Identity val scheme: Scheme,
        @Identity val target: Target,
        @Identity val request: Request,
        @Identity val unmarshaller: EntityUnmarshaller[T],
        @Identity val targetedParent: ?[TargetedRequest[T]] = None,
        @Identity val directedParent: ?[DirectedRequest[T]] = None
        ) extends RichObject {
  override def toString() = {
    request+System.lineSeparator()+System.lineSeparator()+"to "+target+" over "+scheme
  }
}
