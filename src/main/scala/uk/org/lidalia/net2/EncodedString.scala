package uk.org.lidalia.net2

trait EncodedString extends CharSequence {

  def decode: String

  def length = toString.length

  def charAt(index: Int) = toString.charAt(index)

  def subSequence(start: Int, end: Int) = toString.subSequence(start, end)
}

trait EncodedStringFactory {

  def apply(encoded: String): EncodedString
  def encode(unencoded: String): EncodedString

}
