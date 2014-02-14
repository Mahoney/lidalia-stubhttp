package uk.org.lidalia.http.core.headerfields

import uk.org.lidalia.http.core.{HeaderField, HeaderFieldName}
import org.joda.time.Duration
import scala.util.control.Exception.catching

object Age extends HeaderFieldName[?[Duration]] {

  def apply(duration: Duration) = new Age(duration)

  def name: String = "Age"

  def parse(headerFieldValues: List[String]) = headerFieldValues.headOption.flatMap( ageStr => catching(classOf[NumberFormatException]) opt Duration.standardSeconds(ageStr.toLong) )
}

class Age private (duration: Duration) extends HeaderField(Age.name, List(duration.toStandardSeconds.toString))
