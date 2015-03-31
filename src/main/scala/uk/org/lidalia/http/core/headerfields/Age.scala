package uk.org.lidalia.http.core.headerfields

import uk.org.lidalia.http.core.HeaderField
import org.joda.time.Duration
import scala.util.Try

object Age extends SingleValueHeaderFieldName[Duration] {

  def apply(duration: Duration) = new Age(duration)

  override def name = "Age"

  override def parse(headerFieldValue: String) = Try(Duration.standardSeconds(headerFieldValue.toLong)).toOption
}

class Age private (duration: Duration) extends HeaderField(Age.name, List(duration.toStandardSeconds.toString))
