package uk.org.lidalia.http.core.headerfields

import uk.org.lidalia.http.core.HeaderFieldName
import scala.util.Try

abstract class SingleValueHeaderFieldName[T] extends HeaderFieldName[?[T]] {
  final def parse(headerFieldValues: List[String]): ?[T] = {
    headerFieldValues.view.flatMap(headerFieldValue => Try(parse(headerFieldValue)).toOption).headOption
  }

  def parse(headerFieldValue: String): T
}
