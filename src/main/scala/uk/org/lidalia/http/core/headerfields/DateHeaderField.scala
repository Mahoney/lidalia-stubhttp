package uk.org.lidalia.http.core.headerfields

import org.joda.time.{DateTimeZone, DateTime}
import uk.org.lidalia.http.core.{HeaderFieldName, HeaderField}
import org.joda.time.format.{ISODateTimeFormat, DateTimeFormat}
import scala.util.Try

object DateHeaderField {

  val rfc1123DateFormat = DateTimeFormat.forPattern("EEE, dd MMM yyyy HH:mm:ss 'GMT'").withZone(DateTimeZone.forID("GMT"))
  val rfc850DateFormat = DateTimeFormat.forPattern("EEEE, dd-MMM-yy HH:mm:ss 'GMT'").withZone(DateTimeZone.forID("GMT"))
  val ascTimeDateFormat1 = DateTimeFormat.forPattern("EEE MMM dd HH:mm:ss yyyy").withZone(DateTimeZone.forID("GMT"))
  val ascTimeDateFormat2 = DateTimeFormat.forPattern("EEE MMM  d HH:mm:ss yyyy").withZone(DateTimeZone.forID("GMT"))

  private[headerfields] val dateFormats = List(rfc1123DateFormat, rfc850DateFormat, ascTimeDateFormat1, ascTimeDateFormat2, ISODateTimeFormat.dateTimeParser).view

}

abstract class DateHeaderField protected(name: String, val date: DateTime) extends HeaderField(name, List(DateHeaderField.rfc1123DateFormat.print(date)))

abstract class DateHeaderFieldName extends SingleValueHeaderFieldName[DateTime] {
  def parse(headerFieldValue: String): DateTime = DateHeaderField.dateFormats.flatMap( dateFormat => Try(dateFormat.parseDateTime(headerFieldValue)).toOption ).head
}
