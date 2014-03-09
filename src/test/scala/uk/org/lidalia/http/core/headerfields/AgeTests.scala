package uk.org.lidalia.http.core.headerfields

import org.scalatest.PropSpec
import org.scalatest.prop.TableDrivenPropertyChecks
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import uk.org.lidalia.http.core.headerfields.SingleValueHeaderFieldNameTests.firstParseableValueReturned
import org.joda.time.Duration

@RunWith(classOf[JUnitRunner])
class AgeTests extends PropSpec with TableDrivenPropertyChecks {
  property("First Parseable Value Returned") {
    firstParseableValueReturned[Duration](
      Age,
      "1", Duration.standardSeconds(1),
      "2", Duration.standardSeconds(2),
      "not a number")
  }
}
