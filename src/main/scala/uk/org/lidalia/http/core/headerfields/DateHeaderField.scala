package uk.org.lidalia.http.core.headerfields

import org.joda.time
import time.{DateTimeZone, DateTime}
import time.format.{ISODateTimeFormat, DateTimeFormat}
import uk.org.lidalia.http.core.HeaderField
import scala.util.Try
import java.util.Locale

object DateHeaderField {

  val rfc1123DateFormat  = forPattern("EEE, dd MMM yyyy HH:mm:ss 'GMT'")
  val rfc850DateFormat   = forPattern("EEEE, dd-MMM-yy HH:mm:ss 'GMT'").withPivotYear(DateTime.now().year().get())
  val ascTimeDateFormat1 = forPattern("EEE MMM dd HH:mm:ss yyyy")
  val ascTimeDateFormat2 = forPattern("EEE MMM  d HH:mm:ss yyyy")


  private def forPattern(pattern: String) = {
    DateTimeFormat.forPattern(pattern).withZone(DateTimeZone.forID("GMT")).withLocale(Locale.ENGLISH)
  }

  private[headerfields] val dateFormats = List(rfc1123DateFormat, rfc850DateFormat, ascTimeDateFormat1, ascTimeDateFormat2, ISODateTimeFormat.dateTimeParser).view

}

abstract class DateHeaderField protected(name: String, val date: DateTime) extends HeaderField(name, List(DateHeaderField.rfc1123DateFormat.print(date)))

abstract class DateHeaderFieldName extends SingleValueHeaderFieldName[DateTime] {
  def parse(headerFieldValue: String) = DateHeaderField.dateFormats.flatMap( dateFormat => Try(dateFormat.parseDateTime(headerFieldValue)).toOption ).headOption
}
