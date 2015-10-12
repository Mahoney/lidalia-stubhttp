package uk.org.lidalia.lang

import java.util.regex.Pattern

import uk.org.lidalia.net2.UriConstants.Patterns.pctEncoded

final class PercentEncodedStringFactory(
  permittedChars: Set[Char]
) extends EncodedStringFactory[PercentEncodedString] {

  override def encode(unencoded: String): PercentEncodedString = {
    apply(unencoded.flatMap { c =>
      if (permittedChars(c))
        c.toString
      else {
        val asciiDec = c.toInt
        if (asciiDec >= 0 && asciiDec <= 255) {
          val padding = if (asciiDec > 15) "" else "0"
          s"%$padding${asciiDec.toHexString}"
        } else {
          throw new IllegalArgumentException(s"Cannot encode $c as it is not in the ascii range in $unencoded")
        }
      }
    })
  }

  private val metaChars = Set('^', '-', ']', '\\')

  private [lang] val regex: Pattern = {
    if (permittedChars.isEmpty)
      Pattern.compile(s"($pctEncoded)*")
    else {
      val characterClass = permittedChars
        .map( c => if (metaChars(c)) "\\"+c else c.toString )
        .mkString("")
      Pattern.compile(s"($pctEncoded|[$characterClass])*")

    }
  }

  override def apply(encoded: String) = new PercentEncodedString(this, encoded)
}

class PercentEncodedString (
  override final val factory: PercentEncodedStringFactory,
  encodedStr: String
) extends RegexVerifiedWrappedString(encodedStr, factory.regex) with EncodedString[PercentEncodedString] {

  /**
   * @return the decoded representation of the String
   */
  override final def decode: String = {
    val split = encodedStr.split(s"""((?=$pctEncoded)|(?<=$pctEncoded))""")
    val decoded = split.flatMap { s =>
      if (pctEncoded.matcher(s).matches())
        Integer.parseInt(s.substring(1), 16).toChar.toString
      else s
    }
    decoded.mkString("")
  }

}

abstract class PercentEncodedStringFactoryAid[T <: PercentEncodedStringAid[T]](
  permittedChars: Set[Char]
) extends EncodedStringFactory[T] {

  private [lang] val factory = new PercentEncodedStringFactory(permittedChars)

  override final def encode(unencoded: String): T = {
    apply(factory.encode(unencoded).toString)
  }

  private val metaChars = Set('^', '-', ']', '\\')

  private [lang] val regex = factory.regex

}

abstract class PercentEncodedStringAid[T <: PercentEncodedStringAid[T]] (
  override final val factory: PercentEncodedStringFactoryAid[T],
  encodedStr: String
) extends RegexVerifiedWrappedString(encodedStr, factory.regex) with EncodedString[T] {

  override final def decode = factory.factory(encodedStr).decode

}
