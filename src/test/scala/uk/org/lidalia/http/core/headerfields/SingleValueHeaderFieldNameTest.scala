package uk.org.lidalia.http.core.headerfields

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.PropSpec
import org.scalatest.prop.TableDrivenPropertyChecks

@RunWith(classOf[JUnitRunner])
class SingleValueHeaderFieldNameTest extends PropSpec with TableDrivenPropertyChecks {

  property("Parse returning null becomes None") {
    val nullReturningSingleValueHeaderFieldName = new SingleValueHeaderFieldName[String]() {
      def name = "name"

      def parse(headerFieldValue: String) = null
    }
    assert(nullReturningSingleValueHeaderFieldName.parse(List("")) === None)
  }

  property("Parse throwing exception becomes None") {
    val exceptionThrowingSingleValueHeaderFieldName = new SingleValueHeaderFieldName[String]() {
      def name = "name"

      def parse(headerFieldValue: String) = throw new UnsupportedOperationException
    }
    assert(exceptionThrowingSingleValueHeaderFieldName.parse(List("")) === None)
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

      def parse(headerFieldValue: String) = Integer.parseInt(headerFieldValue)
    }
    forAll(inputsToOutputs) { (input, output) =>
      assert(numberSingleValueHeaderFieldName.parse(input) === Some(output))
    }
  }
}
