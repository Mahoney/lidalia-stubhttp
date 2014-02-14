package uk.org.lidalia.http.core.headerfields

import uk.org.lidalia.http.core.{HeaderField, HeaderFieldName}

object Etag extends HeaderFieldName[?[String]] {
  def parse(headerFieldValues: List[String]) = headerFieldValues.headOption

  def name = "Etag"
}

class Etag private (val etag: String) extends HeaderField(Etag.name, List(etag))
