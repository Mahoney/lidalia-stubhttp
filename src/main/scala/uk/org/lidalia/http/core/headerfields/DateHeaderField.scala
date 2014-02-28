package uk.org.lidalia.http.core.headerfields

import org.joda.time.{DateTimeZone, DateTime}
import uk.org.lidalia.http.core.{HeaderFieldName, HeaderField}
import org.joda.time.format.DateTimeFormat

object DateHeaderField {
  val rfc1123DateFormat = DateTimeFormat.forPattern("EEE, dd MMM yyyy HH:mm:ss 'GMT'").withZone(DateTimeZone.forID("GMT"))
  def toHttpFormat(date: DateTime): String = rfc1123DateFormat.print(date)
}

abstract class DateHeaderField protected(name: String, val date: DateTime) extends HeaderField(name, List(DateHeaderField.toHttpFormat(date)))

abstract class DateHeaderFieldName extends HeaderFieldName[?[DateTime]] {

  def parse(headerFieldValues: List[String]): ?[DateTime] = {
    headerFieldValues.headOption.map(DateHeaderField.rfc1123DateFormat.parseDateTime)
  }

}
