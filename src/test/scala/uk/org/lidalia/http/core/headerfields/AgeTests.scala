package uk.org.lidalia.http.core.headerfields

import org.joda.time.Duration
import org.scalatest.PropSpec
import org.scalatest.prop.TableDrivenPropertyChecks
import uk.org.lidalia.http.core.headerfields.SingleValueHeaderFieldNameTests.firstParseableValueReturned

class AgeTests extends PropSpec with TableDrivenPropertyChecks {
  property("First Parseable Value Returned") {
    firstParseableValueReturned[Duration](
      Age,
      "1", Duration.standardSeconds(1),
      "2", Duration.standardSeconds(2),
      "not a number")
  }
}
