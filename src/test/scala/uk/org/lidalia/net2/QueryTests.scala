package uk.org.lidalia.net2

import org.scalatest.PropSpec
import org.scalatest.prop.PropertyChecks
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalacheck.Gen
import uk.org.lidalia.TestUtils.{
  genRandomStringFrom,
  genStringFromChars,
  genNonEmptyStringFromChars
}
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
    genStringFromChars(queryChars)

  def genValidQueryStrings =
    genRandomStringFrom(
      pctEncoded,
      otherThanPctEncoded
    )

  property("Valid query strings accepted") {

    forAll((genValidQueryStrings, "query string")) { (queryString) =>
      whenever (queryString.size < 2048 && (!queryString.contains("%") || queryString.matches(".*%[0-9A-Fa-f]{2}.*"))) {
        assert(Query(queryString).toString === queryString)
      }
    }
  }

  property("Query string can be empty") {
    assert(Query("").toString === "")
  }

  property("Query string cannot be null") {
    val exception = intercept[NullPointerException] {
      Query(null)
    }
  }

  property("Invalid query string characters rejected") {

    val allChars: Set[Char] = (' ' to '~').toSet
    val invalidQueryChars = allChars -- queryChars

    forAll((genNonEmptyStringFromChars(invalidQueryChars), "query string")) { (queryString) =>
      whenever (!queryString.isEmpty && queryString.size < 1024) {
        val exception = intercept[IllegalArgumentException] {
          Query(queryString)
        }
      }
    }
  }

  property("hex digit") {
    assert(Pattern.compile(UriConstants.hexDigitRegex).matcher("0").matches())
    assert(Pattern.compile(UriConstants.hexDigitRegex).matcher("9").matches())
    assert(Pattern.compile(UriConstants.hexDigitRegex).matcher("A").matches())
    assert(Pattern.compile(UriConstants.hexDigitRegex).matcher("a").matches())
    assert(Pattern.compile(UriConstants.hexDigitRegex).matcher("F").matches())
    assert(Pattern.compile(UriConstants.hexDigitRegex).matcher("f").matches())
    assert(!Pattern.compile(UriConstants.hexDigitRegex).matcher("G").matches())
    assert(!Pattern.compile(UriConstants.hexDigitRegex).matcher("g").matches())
  }

  property("2 hex digits") {
    val valid = Table(
      "String",
      "%00",
      "%F0",
      "%0F",
      "%f0",
      "%0f"
    )
    forAll(valid) { validhex =>
      assert(Pattern.compile(UriConstants.pctEncodedRegex).matcher(validhex).matches())
    }
    val invalid = Table(
      "String",
      "%G0",
      "%0G",
      "%000",
      "%FFF",
      "%0",
      "%F"
    )
    forAll(invalid) { invalidhex =>
      assert(!Pattern.compile(UriConstants.pctEncodedRegex).matcher(invalidhex).matches())
    }
  }

  property("queryChars") {
    val valid = Table(
      "String",
      "a",
      "z",
      "0",
      "9",
      "A",
      "Z",
      "-",
      ".",
      "_",
      "~",
      "!",
      "$",
      "&",
      "(",
      ")",
      "*",
      "+",
      ",",
      ":",
      "=",
      ";",
      "@"
    )
    forAll(valid) { validq =>
      assert(Pattern.compile(UriConstants.queryRegex).matcher(validq).matches())
    }
    val allChars: Set[String] = (' ' to '~').map(""+_).toSet
    val invalidQueryChars: Set[String] = allChars -- queryChars.map(""+_).toSet
    val invalid = Table(
      "String",
      invalidQueryChars.toSeq:_*
    )
    forAll(invalid) { invalidq =>
      assert(!Pattern.compile(UriConstants.pctEncodedRegex).matcher(invalidq).matches())
    }
  }

  property("params are maintained") {
    val queryStringParamFormats = Table(
      "Query String Param Format",
      "&&",
      "&=",
      "=&",
      "==",
      "a&a=b",
      "a=a&b",
      "a=",
      "=a",
      "&a",
      "a&",
      "a=b&a=c&a2=b&a=d",
      "&"
    )

    forAll(queryStringParamFormats) { queryStringParamFormat =>
      assert(Query(queryStringParamFormat).toString === queryStringParamFormat)
    }
  }

  property("query parameters parsed") {
    val queryStringsToParameters = Table(
      ("Query String",       "Expected parameter map"),
      ("",                    Map(
                                QueryParamKey("")    -> List()
                              )
      ),
      ("foo",                 Map(
                                QueryParamKey("foo") -> List()
                              )
      ),
      ("foo=",                Map(
                                QueryParamKey("foo") -> List(QueryParamValue(""))
                              )
      ),
      ("foo&",                Map(
                                QueryParamKey("foo") -> List(),
                                QueryParamKey("")    -> List()
                              )
      ),
      ("&foo",                Map(
                                QueryParamKey("")    -> List(),
                                QueryParamKey("foo") -> List()
                              )
      ),
      ("=foo",                Map(
                                QueryParamKey("")    -> List(QueryParamValue("foo"))
                              )
      ),
      ("&",                   Map(
                                QueryParamKey("")    -> List()
                              )
      ),
      ("&&",                  Map(
                                QueryParamKey("")    -> List()
                              )
      ),
      ("=&=&",                Map(
                                QueryParamKey("")    -> List(
                                                          QueryParamValue(""),
                                                          QueryParamValue("")
                                                       )
                              )
      ),
      ("foo&a=1&b=2&a=3&bar", Map(
                                QueryParamKey("foo") -> List(),
                                QueryParamKey("a")   -> List(
                                                          QueryParamValue("1"),
                                                          QueryParamValue("3")
                                                       ),
                                QueryParamKey("b")   -> List(
                                                          QueryParamValue("2")
                                                       ),
                                QueryParamKey("bar") -> List()
                              )
      ),
      ("a&a=1&a&a=&a=2",      Map(
                                QueryParamKey("a")   -> List(
                                                          QueryParamValue("1"),
                                                          QueryParamValue(""),
                                                          QueryParamValue("2")
                                                       )
                              )
      ),
      ("a=1=2",               Map(
                                QueryParamKey("a")   -> List(
                                                          QueryParamValue("1=2")
                                                       )
                              )
      )
    )

    forAll(queryStringsToParameters) { (queryString, expectedParameterMap) =>
      assert(Query(queryString).paramMap === expectedParameterMap)
    }
  }

  property("encode works") {
    assert(Query.encode("/") === Query("%2F"))
  }

  property("decode works") {
    assert(Query("%2F").decode === "/")
  }
}
