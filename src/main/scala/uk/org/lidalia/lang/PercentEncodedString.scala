package uk.org.lidalia.lang

import java.util.regex.Pattern

import uk.org.lidalia.lang.Classes.inSameClassHierarchy
import uk.org.lidalia.net2.UriConstants.Patterns.pctEncoded

abstract class PercentEncodedStringFactory[T <: PercentEncodedString[T]](
  permittedChars: Set[Char]
) extends EncodedStringFactory[T] {

  override def encode(unencoded: String): T = {
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

}

abstract class PercentEncodedString[T <: PercentEncodedString[T]] (
  override final val factory: PercentEncodedStringFactory[T],
  encoded: String
) extends EncodedString[T] {

  require(
    factory.regex.matcher(encoded).matches(),
    s"${getClass.getSimpleName} [$encoded] must match ${factory.regex}"
  )

  override final val toString = encoded

  /**
   * @return the decoded representation of the String
   */
  override final lazy val decode: String = {
    val split = encoded.split(s"""((?=$pctEncoded)|(?<=$pctEncoded))""")
    val decoded = split.map { s =>
      if (pctEncoded.matcher(s).matches())
        Integer.parseInt(s.substring(1), 16).toChar.toString
      else s
    }
    decoded.mkString("")
  }

  override final lazy val hashCode = decode.hashCode

  override final def equals(other: Any): Boolean = other match {
    case that: PercentEncodedString[T] =>
      inSameClassHierarchy(that.getClass, this.getClass) && decode == that.decode
    case _ => false
  }
}

class ConcretePercentEncodedStringFactory(permittedChars: Set[Char]) extends PercentEncodedStringFactory[ConcretePercentEncodedString](permittedChars) {
  override def apply(encoded: String) = new ConcretePercentEncodedString(this, encoded)
}

class ConcretePercentEncodedString(factory: ConcretePercentEncodedStringFactory, encoded: String)
  extends PercentEncodedString[ConcretePercentEncodedString](factory, encoded)
