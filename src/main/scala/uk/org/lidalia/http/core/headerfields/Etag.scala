package uk.org.lidalia.http.core.headerfields

import uk.org.lidalia.http.core.HeaderField

object Etag extends SingleValueHeaderFieldName[String] {
  def parse(headerFieldValue: String) = headerFieldValue

  def name = "Etag"
}

class Etag private (val etag: String) extends HeaderField(Etag.name, List(etag))
