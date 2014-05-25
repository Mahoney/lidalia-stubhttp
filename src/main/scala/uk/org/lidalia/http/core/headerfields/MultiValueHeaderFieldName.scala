package uk.org.lidalia.http.core.headerfields

import uk.org.lidalia.http.core.HeaderFieldName

abstract class MultiValueHeaderFieldName[T] extends HeaderFieldName[List[T]] {

  /**
   * Any String in the input list may contain one or more logical
   * values, comma separated, so this function turns them all into
   * a single comma separated string
   */
  def parse(headerFieldValues: List[String]): List[T] = {
    parse(headerFieldValues.mkString(","))
  }

  /**
   * Input is all the values for this header as a comma separated string
   * It is impossible to know how to split this list as the escape
   * sequence for individual header values which contain a comma may be
   * header specific, so doing so is left as an exercise for the parser.
   */
  def parse(headerFieldValues: String): List[T]
}
