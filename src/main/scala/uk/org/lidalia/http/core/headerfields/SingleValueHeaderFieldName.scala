package uk.org.lidalia.http.core.headerfields

import uk.org.lidalia.http.core.HeaderFieldName

abstract class SingleValueHeaderFieldName[T] extends HeaderFieldName[?[T]] {

  /**
   * Returns the result of parsing the first header that was parseable
   */
  final def parse(headerFieldValues: List[String]): ?[T] = {
    headerFieldValues.view.flatMap(headerFieldValue => safeParse(headerFieldValue)).headOption
  }

  private def safeParse(headerFieldValue: String): ?[T] = {
    val parsed = parse(headerFieldValue)
    validOption(parsed, s"${getClass.getSimpleName}.parse(String) may not return $parsed for input [$headerFieldValue]")
  }

  def validOption[A](parsed: ?[A], message: => String): ?[A] = {
    require(parsed != null, message)
    if (parsed.isDefined) require(parsed.get != null, message)
    parsed
  }

  /**
   * When implementing:
   * If the input is parseable, return Some[ParsedType]
   * If the input is not parseable, return None
   * Do not throw an exception or return either null or Some(null)
   */
  def parse(headerFieldValue: String): ?[T]
}
