package uk.org.lidalia.http.core.headerfields

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.PropSpec
import org.scalatest.prop.TableDrivenPropertyChecks
import org.joda.time.{DateTimeZone, DateTime}
import uk.org.lidalia.{DefaultLocale, StaticTime}
import java.util.Locale
import uk.org.lidalia.http.core.{ResponseHeader, HeaderField}

@RunWith(classOf[JUnitRunner])
class DateHeaderFieldTests extends PropSpec with TableDrivenPropertyChecks with StaticTime with DefaultLocale {

  val staticTime = new DateTime("2000-02-13T12:00:00.000Z").toInstant
  val defaultLocale = Locale.FRANCE

  val testName = "Test-Field"

  val rfc1123Date = "Sun, 06 Nov 1994 08:49:37 GMT"
  val rfc850Date = "Sunday, 06-Nov-94 08:49:37 GMT"
  val ascTimeDate = "Sun Nov  6 08:49:37 1994"
  val iso8601Date = "1994-11-06T10:49:37.123+02"

  val dateTime = new DateTime(iso8601Date)
  val rfc1123CompliantDateTime = dateTime.withMillisOfSecond(0).withZone(DateTimeZone.forID("GMT"))

  val fieldName = new DateHeaderFieldName {
    def name = testName
  }

  property("DateHeaderField serialises to RFC 1123 format (http://tools.ietf.org/html/rfc2616#section-3.3)") {
    val field = new DateHeaderField(testName, dateTime) {}
    assert(field.values === List(rfc1123Date))
  }

  val rfc850Past = "Sunday, 01-Jan-50 00:00:00 GMT"
  val dateTimePast = new DateTime("1950-01-01T00:00:00.000Z").withZone(DateTimeZone.forID("GMT"))
  val rfc850Future = "Friday, 31-Dec-49 23:59:59 GMT"
  val dateTimeFuture = new DateTime("2049-12-31T23:59:59.000Z").withZone(DateTimeZone.forID("GMT"))

  val dateFormats =
    Table(
      ("header field values",                              "expected date"),
      (List(rfc1123Date),                                  Some(rfc1123CompliantDateTime)),
      (List(rfc850Date),                                   Some(rfc1123CompliantDateTime)),
      (List(rfc850Past),                                   Some(dateTimePast)),
      (List(rfc850Future),                                 Some(dateTimeFuture)),
      (List(ascTimeDate),                                  Some(rfc1123CompliantDateTime)),
      (List(iso8601Date),                                  Some(dateTime)),
      (List("not a date", rfc1123Date),                    Some(rfc1123CompliantDateTime)),
      (List(rfc1123Date, "Mon, 07 Nov 1994 08:49:37 GMT"), Some(rfc1123CompliantDateTime)),
      (List(rfc1123Date, "not a date"),                    Some(rfc1123CompliantDateTime)),
      (List(),                                             None),
      (List("not a date"),                                 None)
    )

  property("DateHeaderField parses dates ") {
    forAll(dateFormats) { (headerFieldValues, expectedDate) =>
      val parsedDate = fieldName.parse(headerFieldValues)
      assert(parsedDate === expectedDate)
    }
  }
}
