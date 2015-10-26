package uk.org.lidalia.http.client

import uk.org.lidalia.http.core.Request
import uk.org.lidalia.lang.RichObject
import uk.org.lidalia.net2.{HostAndPort, Scheme, Target}

object TargetedRequest {

  def apply[T](
    scheme: Scheme,
    target: Target,
    request: Request,
    unmarshaller: EntityUnmarshaller[T],
    targetedParent: ?[TargetedRequest[T]] = None,
    directedParent: ?[DirectedRequest[T]] = None
  ) = {
    new TargetedRequest[T](
      scheme,
      target,
      request,
      unmarshaller,
      targetedParent,
      directedParent
    )
  }

}

class TargetedRequest[T] private (
  @Identity val scheme: Scheme,
  @Identity val target: Target,
  @Identity val request: Request,
  @Identity val unmarshaller: EntityUnmarshaller[T],
  @Identity val targetedParent: ?[TargetedRequest[T]],
  @Identity val directedParent: ?[DirectedRequest[T]]
) extends RichObject {
  override def toString = {
    request+System.lineSeparator()+System.lineSeparator()+"to "+target+" over "+scheme
  }
}
