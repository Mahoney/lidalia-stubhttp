package uk.org.lidalia.net2

import java.util.regex.Pattern

import uk.org.lidalia.lang.{EncodedStringFactory, EncodedString, RegexVerifiedWrappedString}
import uk.org.lidalia.net2.UriConstants.Patterns

import scala.collection.immutable

object Query extends EncodedStringFactory[Query] {

  def apply(queryStr: String): Query = {
    queryStr match {
      case "" => new Query(List())
      case _ =>
        val keyValuePairStrs = queryStr.toString.split("&", -1).toList

        val keyValuePairs = keyValuePairStrs.map {
          keyValuePairStr =>
            val keyValuePair = keyValuePairStr.split("=", 2).toList
            keyValuePair match {
              case List(key) => QueryParamKey(key) -> None
              case List(key, value) => QueryParamKey(key) -> Some(QueryParamValue(value))
            }
        }
        new Query (keyValuePairs)
    }
  }

  def encode(unencoded: String): Query = apply(unencoded)
}

class Query private(val keyValuePairs: immutable.Seq[(QueryParamKey, ?[QueryParamValue])])
    extends EncodedString[Query] {

  lazy val paramMap: Map[QueryParamKey, immutable.Seq[QueryParamValue]] = {

    val groupedKeyValuePairs = keyValuePairs.groupBy(_._1)

    groupedKeyValuePairs.map { case (key, keyAndValues) =>
      key -> keyAndValues.flatMap(_._2)
    }
  }

  def apply(key: String) = getFirstDecoded(key)

  def apply(key: QueryParamKey) = getFirst(key)

  def get(key: QueryParamKey): immutable.Seq[QueryParamValue] = paramMap.getOrElse(key, List())

  def getFirst(key: QueryParamKey): ?[QueryParamValue] = paramMap.get(key).flatMap(_.headOption)

  def getDecoded(key: QueryParamKey): immutable.Seq[String] = get(key).map(_.decode)

  def getFirstDecoded(key: QueryParamKey): ?[String] = getFirst(key).map(_.decode)

  def get(key: String): immutable.Seq[QueryParamValue] = get(QueryParamKey.encode(key))

  def getFirst(key: String): ?[QueryParamValue] = getFirst(QueryParamKey.encode(key))

  def getDecoded(key: String): immutable.Seq[String] = getDecoded(QueryParamKey.encode(key))

  def getFirstDecoded(key: String): ?[String] = getFirstDecoded(QueryParamKey.encode(key))

  def -(key: String): Query = this - QueryParamKey.encode(key)

  def -(key: QueryParamKey): Query = {
    val filtered = keyValuePairs.filter { case (existingKey, value) => existingKey != key }
    new Query(filtered)
  }

  def -(key: String, values: String*): Query = this - (QueryParamKey.encode(key), values.map(QueryParamValue.encode):_*)

  def -(key: QueryParamKey, values: QueryParamValue*): Query = {
    val filtered = keyValuePairs.filterNot { case (existingKey, value) => existingKey == key && value.map(values.contains(_)).or(false) }
    new Query(filtered)
  }

  def ++(key: String, values: String*): Query = this ++ (QueryParamKey.encode(key), values.map(QueryParamValue.encode):_*)

  def ++(key: QueryParamKey, values: QueryParamValue*): Query = {
    val newPairs = values.map(value => key -> Some(value))
    new Query(keyValuePairs ++ newPairs)
  }

  def set(key: String, values: String*): Query = set(QueryParamKey.encode(key), values.map(QueryParamValue.encode):_*)

  def set(key: QueryParamKey, values: QueryParamValue*): Query = (this - key) ++ (key, values:_*)

  def decode: String = toString

  val factory = Query

  override def toString = {
    val strings = keyValuePairs.map { case (key, value) => key + (value.map("=" + _) or "")}
    strings.mkString("&")
  }

  override def equals(other: Any): Boolean = other match {
    case that: Query => keyValuePairs == that.keyValuePairs
    case _ => false
  }

  override def hashCode(): Int = {
    val state = Seq(keyValuePairs)
    state.map(_.hashCode()).foldLeft(0)((a, b) => 31 * a + b)
  }
}

sealed abstract class QueryParamElement[T <: QueryParamElement[T]](str: String, verifier: Pattern)
    extends RegexVerifiedWrappedString(str, verifier)
    with EncodedString[T]

object QueryParamKey extends EncodedStringFactory[QueryParamKey] {

  def apply(queryParamKeyStr: String): QueryParamKey = new QueryParamKey(queryParamKeyStr)

  def encode(unencoded: String): QueryParamKey = apply(unencoded)
}

class QueryParamKey private(queryParamKeyStr: String)
    extends QueryParamElement[QueryParamKey](queryParamKeyStr, Patterns.queryParamKey) {

  override def decode: String = toString

  val factory = QueryParamKey
}

object QueryParamValue extends EncodedStringFactory[QueryParamValue] {

  def apply(queryParamValueStr: String): QueryParamValue = new QueryParamValue(queryParamValueStr)

  def encode(unencoded: String): QueryParamValue = apply(unencoded)
}

class QueryParamValue private(queryParamValueStr: String)
    extends QueryParamElement[QueryParamValue](queryParamValueStr, Patterns.queryParamValue) {

  override def decode: String = toString

  val factory = QueryParamValue
}
