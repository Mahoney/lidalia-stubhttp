package uk.org.lidalia.http.core.headerfields

import uk.org.lidalia.http.core.{HeaderField, HeaderFieldName}
import org.joda.time.Duration
import scala.util.control.Exception.catching
import scala.util.Try

object Age extends SingleValueHeaderFieldName[Duration] {

  def apply(duration: Duration) = new Age(duration)

  def name: String = "Age"

  def parse(headerFieldValue: String) = Try(Duration.standardSeconds(headerFieldValue.toLong)).toOption
}

class Age private (duration: Duration) extends HeaderField(Age.name, List(duration.toStandardSeconds.toString))
