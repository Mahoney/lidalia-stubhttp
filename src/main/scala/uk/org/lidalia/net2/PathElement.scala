package uk.org.lidalia.net2

import uk.org.lidalia.lang.{EncodedStringFactory, EncodedString, RegexVerifiedWrappedString}
import uk.org.lidalia.net2.UriConstants.Patterns

object PathElement extends EncodedStringFactory[PathElement] {

  def apply(pathElementStr: String) = new PathElement(pathElementStr)

  override def encode(unencoded: String) = ???
}

class PathElement private (pathStr: String)
    extends RegexVerifiedWrappedString(pathStr, Patterns.pchar)
    with EncodedString[PathElement] {

  override def decode = ???

  override val factory: EncodedStringFactory[PathElement] = PathElement
}
