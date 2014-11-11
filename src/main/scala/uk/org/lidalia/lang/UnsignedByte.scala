package uk.org.lidalia.lang

object UnsignedByte {
  private[UnsignedByte] val MAX_BYTE_VALUE = 255

  def apply(theByte: Int) = new UnsignedByte(theByte)
  def apply(theByte: String) = new UnsignedByte(theByte.toInt)
}

class UnsignedByte(@Identity private val theByte: Int) extends RichObject {

  require(theByte >= 0 && theByte <= UnsignedByte.MAX_BYTE_VALUE)

  override def toString: String = String.valueOf(theByte)
}
