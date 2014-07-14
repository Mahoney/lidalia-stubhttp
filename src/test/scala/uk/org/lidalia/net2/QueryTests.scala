package uk.org.lidalia.net2

import org.scalatest.PropSpec
import org.scalatest.prop.PropertyChecks
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalacheck.Gen
import java.lang.IllegalArgumentException
import uk.org.lidalia.TestUtils.genRandomStringFrom
import java.util.regex.Pattern

@RunWith(classOf[JUnitRunner])
class QueryTests
    extends PropSpec
    with PropertyChecks {

  val queryChars: Set[Char] = UriConstants.pchar ++ Set('/', '?')

  val pctEncoded =
    for { num <- Gen.choose(0, 255) }
    yield "%"+String.format("%02X", num.asInstanceOf[Object])

  val otherThanPctEncoded =
    for (n <- Gen.someOf(queryChars))
    yield n.mkString

  def genValidQueryStrings =
    genRandomStringFrom(
      pctEncoded,
      otherThanPctEncoded
    )

  property("Valid query strings accepted") {

    forAll((genValidQueryStrings, "query string")) { (queryString) =>
      println("Query string: "+queryString)
      assert(Query(queryString).toString === queryString)
    }
  }

  property("Query string can be empty") {
    assert(Query("").toString === "")
  }

  property("Query string cannot be null") {
    val exception = intercept[IllegalArgumentException] {
      Query(null)
    }
  }

  property("Invalid query strings rejected") {

    val allChars: Set[Char] = (' ' to '~').toSet
    val invalidQueryChars = allChars -- queryChars

    def genInvalidQueryStrings =
      for {
        start <- genValidQueryStrings
        middle <- Gen.someOf(invalidQueryChars)
        end <- genValidQueryStrings
      }
      yield start + middle + end

    forAll((genInvalidQueryStrings, "query string")) { (queryString) =>
      println(queryString)
      val exception = intercept[IllegalArgumentException] {
        Query(queryString)
      }
    }
  }

  property("hex digit") {
    assert(Pattern.compile(Query.hexDigit).matcher("0").matches())
    assert(Pattern.compile(Query.hexDigit).matcher("0").matches())
    assert(Pattern.compile(Query.hexDigit).matcher("9").matches())
    assert(Pattern.compile(Query.hexDigit).matcher("A").matches())
    assert(Pattern.compile(Query.hexDigit).matcher("E").matches())
    assert(!Pattern.compile(Query.hexDigit).matcher("F").matches())
  }
}
