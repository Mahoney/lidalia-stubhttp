package uk.org.lidalia.net2

import org.scalatest.PropSpec
import org.scalatest.prop.PropertyChecks
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalacheck.Gen
import java.lang.IllegalArgumentException
import uk.org.lidalia.TestUtils.{
  genRandomStringFrom,
  genStringFromChars,
  genNonEmptyStringFromChars
}

@RunWith(classOf[JUnitRunner])
class QueryParamValueTests
    extends PropSpec
    with PropertyChecks {

  val queryChars: Set[Char] = UriConstants.pchar ++ Set('/', '?')

  val pctEncoded =
    for { num <- Gen.choose(0, 255) }
    yield "%"+String.format("%02X", num.asInstanceOf[Object])

  val otherThanPctEncoded =
    genStringFromChars(queryChars - '&')

  def genValidQueryStrings =
    genRandomStringFrom(
      pctEncoded,
      otherThanPctEncoded
    )

  property("Valid query param value strings accepted") {

    forAll((genValidQueryStrings, "query param value string")) { (queryString) =>
      whenever (!queryString.matches("%[^0-9A-F]|%[0-9A-F][^0-9A-F]") && queryString.size < 2048) {
        assert(QueryParamValue(queryString).toString === queryString)
      }
    }
  }

  property("Query param value string can be empty") {
    assert(QueryParamValue("").toString === "")
  }

  property("Query param value string cannot be null") {
    val exception = intercept[NullPointerException] {
      QueryParamValue(null)
    }
  }

  property("Invalid query param value string characters rejected") {

    val allChars: Set[Char] = (' ' to '~').toSet
    val invalidQueryChars = allChars -- queryChars

    forAll((genNonEmptyStringFromChars(invalidQueryChars), "query param value string")) { (queryString) =>
      whenever (!queryString.isEmpty && queryString.size < 1024) {
        val exception = intercept[IllegalArgumentException] {
          QueryParamValue(queryString)
        }
      }
    }
  }

  property("encode works") {
    assert(QueryParamValue.encode("/") === QueryParamValue("%2F"))
  }

  property("decode works") {
    assert(QueryParamValue("%2F").decode === "/")
  }
}
