package uk.org.lidalia.http.core.headerfields

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.PropSpec
import org.scalatest.prop.TableDrivenPropertyChecks
import uk.org.lidalia.http.core.headerfields.SingleValueHeaderFieldNameTests._
import uk.org.lidalia.net2.Uri

@RunWith(classOf[JUnitRunner])
class RefererTests extends PropSpec with TableDrivenPropertyChecks {
  property("First Parseable Value Returned") {
    firstParseableValueReturned[Uri](
      Referer,
      "http://example.com/1", Uri("http://example.com/1"),
      "http://example.com/2", Uri("http://example.com/2"),
      "not a uri")
  }
}

