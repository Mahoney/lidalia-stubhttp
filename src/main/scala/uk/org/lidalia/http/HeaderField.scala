package uk.org.lidalia.http

import uk.org.lidalia.lang.RichObject

object HeaderField {
  def apply(name: String, values: String*): HeaderField = apply(name, values.to[List])
  def apply(name: String, values: List[String]): HeaderField = new HeaderField(name, values)
}

class HeaderField protected(@Identity val name: String, @Identity val values: List[String]) extends RichObject
