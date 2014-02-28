package uk.org.lidalia.http.core.headerfields

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.PropSpec
import org.scalatest.prop.TableDrivenPropertyChecks
import org.joda.time.{DateTimeZone, DateTime}

@RunWith(classOf[JUnitRunner])
class DateHeaderFieldTests extends PropSpec with TableDrivenPropertyChecks {

  val testName = "Test-Field"
  val dateTime = new DateTime("1994-11-06T10:49:37.123+02")
  val rfc1123CompliantDateTime = dateTime.withMillisOfSecond(0).withZone(DateTimeZone.forID("GMT"))

  val rfc1123Date = "Sun, 06 Nov 1994 08:49:37 GMT"
  val rfc850Date = "Sunday, 06-Nov-94 08:49:37 GMT"

  val fieldName = new DateHeaderFieldName {
    def name = testName
  }

  property("DateHeaderField serialises to RFC 1123 format (http://tools.ietf.org/html/rfc2616#section-3.3)") {
    val field = new DateHeaderField(testName, dateTime) {}
    assert(field.values === List(rfc1123Date))
  }

  val dateFormats =
    Table(
      ("header field values", "expected date"),
      (List(rfc1123Date),     Some(rfc1123CompliantDateTime)),
      (List(rfc850Date),      Some(rfc1123CompliantDateTime))
    )

  property("DateHeaderField parses dates ") {
    forAll(dateFormats) { (headerFieldValues, expectedDate) =>
      val parsedDate = fieldName.parse(headerFieldValues)
      assert(parsedDate === expectedDate)
    }
  }
}
