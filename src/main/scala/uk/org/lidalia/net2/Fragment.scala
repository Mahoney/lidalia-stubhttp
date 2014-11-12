package uk.org.lidalia.net2

import uk.org.lidalia.lang.WrappedValue

object Fragment extends EncodedStringFactory {
  def apply(fragmentStr: String) = new Fragment(fragmentStr)

  override def encode(unencoded: String) = ???
}

class Fragment private(override val toString: String)
    extends WrappedValue(toString)
    with EncodedString {
  override def decode = ???
}
