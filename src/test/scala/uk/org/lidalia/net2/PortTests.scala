package uk.org.lidalia.net2

import org.scalatest.PropSpec
import org.scalatest.prop.TableDrivenPropertyChecks
import org.scalatest.junit.JUnitRunner
import org.junit.runner.RunWith

@RunWith(classOf[JUnitRunner])
class PortTests extends PropSpec with TableDrivenPropertyChecks {
  val invalidPortStrings =
    Table(
      ("input to constructor", "expected exception type"),
      ("not a number", classOf[NumberFormatException]),
      (null, classOf[NumberFormatException]),
      ("-1", classOf[IllegalArgumentException]),
      ("65536", classOf[IllegalArgumentException])
    )

  val invalidPortNumbers =
    Table(
      "input to constructor",
      -1,
      65536
    )

  property("a Port cannot be constructed with invalid input") {
    forAll(invalidPortStrings) { (input, expectedExceptionType) =>
      val e = intercept[Exception] {
        Port(input)
      }
      assert(e.getClass == expectedExceptionType)
    }
    forAll(invalidPortNumbers) { input =>
      intercept[IllegalArgumentException] {
        Port(input)
      }
    }
  }

  val validPortStrings = Table(
    "input to constructor",
    "0",
    "65535"
  )

  val validPortNumbers = Table(
    "input to constructor",
    0,
    65535
  )

  property("a Port can be constructed with valid input") {
    forAll(validPortStrings) { input =>
      assert(Port(input).toString === input)
    }
    forAll(validPortNumbers) { input =>
      assert(Port(input).portNumber === input)
    }
  }

  property("equals") {
    assert(Port(80) === Port(80))
    assert(Port(80) != Port(81))
  }

  property("hashCode") {
    assert(Port(80).hashCode === Port(80).hashCode)
    assert(Port(80).hashCode != Port(81).hashCode)
  }
}
