package uk.org.lidalia.net2

import org.scalatest.FunSuite
import uk.org.lidalia.lang.EncodedStringChecks

class FragmentTest extends FunSuite {

  test("normal encoded string checks") {
    EncodedStringChecks.checks(Fragment)
  }
}
