package uk.org.lidalia.net2

import uk.org.lidalia.lang.{EncodedStringFactory, EncodedString, RegexVerifiedWrappedString}
import uk.org.lidalia.net2.UriConstants.Patterns

object Fragment extends EncodedStringFactory[Fragment] {
  def apply(fragmentStr: String) = new Fragment(fragmentStr)

  override def encode(unencoded: String) = ???
}

class Fragment private(fragmentStr: String)
    extends RegexVerifiedWrappedString(fragmentStr, Patterns.fragment)
    with EncodedString[Fragment] {

  override def decode = ???

  override val factory: EncodedStringFactory[Fragment] = Fragment
}
