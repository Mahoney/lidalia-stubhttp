package uk.org.lidalia.net2

import uk.org.lidalia.lang.WrappedValue
import java.util.regex.Pattern
import scala.collection.immutable

object Query extends EncodedStringFactory {

  private[net2] val hexDigit = "[0-9A-F]"
  private[net2] val twoHexDigits = s"($hexDigit){2}"
  private[net2] val queryParamKeyChars =
      "a-z" +
      "A-Z" +
      "0-9" +
      "-" +
      "." +
      "_" +
      "~" +
      "!" +
      "$" +
      "'" +
      "(" +
      ")" +
      "*" +
      "+" +
      "," +
      ";" +
      ":" +
      "@" +
      "/" +
      "?"
  private[net2] val queryParamValueChars = queryParamKeyChars + "="
  private[net2] val queryChars = queryParamValueChars + "&"

  def apply(queryStr: String): Query = {
    new Query(queryStr)
  }

  def encode(unencoded: String): Query = ???
}

class Query private(queryStr: String)
    extends WrappedValue(queryStr)
    with EncodedString {

  private val queryPatternStr = s"^([${Query.queryChars}]*(%${Query.twoHexDigits})*)*$$"
  private val queryPattern = Pattern.compile(queryPatternStr)

  require(queryPattern.matcher(queryStr).matches(),
    "Query must match "+queryPatternStr)

  lazy val paramMap: Map[QueryParamKey, immutable.Seq[QueryParamValue]] = {
    val keyValuePairStrs = queryStr.split("&", -1).toList

    val keyValuePairs: List[(String, ?[String])] = keyValuePairStrs.map { keyValuePairStr =>
      val keyValuePair = keyValuePairStr.split("=", 2).toList
      keyValuePair match {
        case List(key) => (key, None)
        case List(key, value) => (key, Some(value))
      }
    }

    val groupedKeyValuePairs = keyValuePairs.groupBy(_._1)

    groupedKeyValuePairs.map {
      case (key, keyValuePairsForKey) =>
        val withoutKeys = keyValuePairsForKey.map(_._2)
        val values = withoutKeys.flatten.map(QueryParamValue(_))
        (QueryParamKey(key), values)
    }
  }

  def decode: String = ???
}

sealed abstract class QueryParamElement(str: String)
    extends WrappedValue(str)
    with EncodedString

object QueryParamKey extends EncodedStringFactory {

  def apply(queryParamKeyStr: String): QueryParamKey = {
    new QueryParamKey(queryParamKeyStr)
  }

  def encode(unencoded: String): QueryParamKey = ???
}

class QueryParamKey private(queryParamKeyStr: String)
    extends QueryParamElement(queryParamKeyStr) {

  private val queryPatternStr = s"^([${Query.queryParamKeyChars}]*(%${Query.twoHexDigits})*)*$$"
  private val queryPattern = Pattern.compile(queryPatternStr)

  require(queryPattern.matcher(queryParamKeyStr).matches(),
    "QueryParamKey must match "+queryPatternStr)

  override def decode: String = ???
}

object QueryParamValue extends EncodedStringFactory {

  def apply(queryParamValueStr: String): QueryParamValue = {
    new QueryParamValue(queryParamValueStr)
  }

  def encode(unencoded: String): QueryParamValue = ???
}

class QueryParamValue private(queryParamValueStr: String)
    extends QueryParamElement(queryParamValueStr) {

  private val queryPatternStr = s"^([${Query.queryParamValueChars}]*(%${Query.twoHexDigits})*)*$$"
  private val queryPattern = Pattern.compile(queryPatternStr)

  require(queryPattern.matcher(queryParamValueStr).matches(),
    "QueryParamKey must match "+queryPatternStr)

  override def decode: String = ???
}
