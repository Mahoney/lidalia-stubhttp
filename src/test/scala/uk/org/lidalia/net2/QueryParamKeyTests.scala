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
class QueryParamKeyTests
    extends PropSpec
    with PropertyChecks {

  val queryChars: Set[Char] = UriConstants.pchar ++ Set('/', '?')

  val pctEncoded =
    for { num <- Gen.choose(0, 255) }
    yield "%"+String.format("%02X", num.asInstanceOf[Object])

  val otherThanPctEncoded =
    genStringFromChars(queryChars -- Set('&', '='))

  def genValidQueryStrings =
    genRandomStringFrom(
      pctEncoded,
      otherThanPctEncoded
    )

  property("Valid query param key strings accepted") {

    forAll((genValidQueryStrings, "query param key string")) { (queryString) =>
      whenever (!queryString.matches("%[^0-9A-F]|%[0-9A-F][^0-9A-F]|.*%$|%[0-9A-F]$") && queryString.size < 2048) {
        assert(QueryParamKey(queryString).toString === queryString)
      }
    }
  }

  property("Query param key string can be empty") {
    assert(QueryParamKey("").toString === "")
  }

  property("Query param key string cannot be null") {
    val exception = intercept[NullPointerException] {
      QueryParamKey(null)
    }
  }

  property("Invalid query param key string characters rejected") {

    val allChars: Set[Char] = (' ' to '~').toSet
    val invalidQueryChars = allChars -- queryChars

    forAll((genNonEmptyStringFromChars(invalidQueryChars), "query param key string")) { (queryString) =>
      whenever (!queryString.isEmpty && queryString.size < 1024) {
        val exception = intercept[IllegalArgumentException] {
          QueryParamKey(queryString)
        }
        assert(exception.getMessage === s"requirement failed: QueryParamKey [$queryString] must match ([a-zA-Z0-9-._~]|%[0-9A-Fa-f]{2}|[!$$'()*+,;]|[:@]|/|\\?)*")
      }
    }
  }

  property("encode works") {
    assert(QueryParamKey.encode("/") === QueryParamKey("%2F"))
  }

  property("decode works") {
    assert(QueryParamKey("%2F").decode === "/")
  }
}
