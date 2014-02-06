package uk.org.lidalia.http.headerfields

import uk.org.lidalia.lang.{RichObject, Identity}

object HeaderField {
  def apply(name: String, values: List[String]) = new HeaderField(name, values)
}

class HeaderField protected(@Identity val name: String, @Identity val values: List[String]) extends RichObject {

}
