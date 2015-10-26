package uk.org.lidalia.lang

object UnsignedByte {

  val MaxValue = UnsignedByte(Byte.MaxValue)
  val MinValue = UnsignedByte(Byte.MinValue)

  def apply(theByte: Byte): UnsignedByte = new UnsignedByte(theByte)
  def apply(theByte: Int): UnsignedByte = UnsignedByte((theByte - 128).toByte)
  def apply(theByte: String): UnsignedByte = UnsignedByte(theByte.toInt)
}

class UnsignedByte private (@Identity private val theByte: Byte) extends RichObject {

  def toInt = theByte+128

  def toSignedByte = theByte

  override def toString: String = String.valueOf(toInt)
}
