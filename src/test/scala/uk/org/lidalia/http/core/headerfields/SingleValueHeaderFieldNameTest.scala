package uk.org.lidalia.http.core.headerfields

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.PropSpec
import org.scalatest.prop.TableDrivenPropertyChecks
import scala.util.Try

@RunWith(classOf[JUnitRunner])
class SingleValueHeaderFieldNameTest extends PropSpec with TableDrivenPropertyChecks {

  class StubSingleValueHeaderFieldName(parseReturn: => ?[String]) extends SingleValueHeaderFieldName[String] {

    def name = "X-Stub"

    def parse(headerFieldValue: String) = parseReturn
  }

  property("Parse returning null throws NullPointerException") {
    val nullReturningSingleValueHeaderFieldName = new StubSingleValueHeaderFieldName(null)
    val exception = intercept[IllegalArgumentException] {
      nullReturningSingleValueHeaderFieldName.parse(List("input value"))
    }
    assert(exception.getMessage === "requirement failed: " +
        "StubSingleValueHeaderFieldName.parse(String) may not return null for input [input value]")
  }

  property("Parse returning Some(null) throws NullPointerException") {
    val someNullReturningSingleValueHeaderFieldName = new StubSingleValueHeaderFieldName(Some(null))
    val exception = intercept[IllegalArgumentException] {
      someNullReturningSingleValueHeaderFieldName.parse(List("input value"))
    }
    assert(exception.getMessage === "requirement failed: " +
        "StubSingleValueHeaderFieldName.parse(String) may not return Some(null) for input [input value]")
  }

  property("Parse throwing exception is propagated") {
    val toThrow = new IllegalStateException()
    val exceptionThrowingSingleValueHeaderFieldName = new StubSingleValueHeaderFieldName(throw toThrow)
    val thrown = intercept[Exception] {
      exceptionThrowingSingleValueHeaderFieldName.parse(List(""))
    }
    assert(thrown === toThrow)
  }

  val inputsToOutputs =
    Table(
      ("input",                   "output"),
      (List("1", "2"),            1       ),
      (List("2", "1"),            2       ),
      (List("not a number", "1"), 1       ),
      (List("1", "not a number"), 1       )
    )

  property("First parseable value returned") {
    val numberSingleValueHeaderFieldName = new SingleValueHeaderFieldName[Int]() {
      def name = "name"

      def parse(headerFieldValue: String) = Try(Integer.parseInt(headerFieldValue)).toOption
    }

    forAll(inputsToOutputs) { (input, output) =>
      assert(numberSingleValueHeaderFieldName.parse(input) === Some(output))
    }
  }
}
