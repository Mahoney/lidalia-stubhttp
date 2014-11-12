package uk.org.lidalia.net2

import uk.org.lidalia.lang.WrappedValue
import java.util.regex.Pattern
import uk.org.lidalia.net2.UriConstants._

import scala.collection.immutable

object Query extends EncodedStringFactory {



  def apply(queryStr: String): Query = {
    new Query(queryStr)
  }

  def encode(unencoded: String): Query = ???
}

class Query private(queryStr: String)
    extends WrappedValue(queryStr)
    with EncodedString {

  private val queryPattern = Pattern.compile(queryRegex)

  require(queryPattern.matcher(queryStr).matches(),
    "Query must match "+queryRegex)

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

  private val queryPattern = Pattern.compile(queryParamKeyRegex)

  require(queryPattern.matcher(queryParamKeyStr).matches(),
    "QueryParamKey must match "+queryParamKeyRegex)

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

  private val queryValuePattern = Pattern.compile(queryParamValueRegex)

  require(queryValuePattern.matcher(queryParamValueStr).matches(),
    "QueryParamKey must match "+queryParamValueRegex)

  override def decode: String = ???
}
