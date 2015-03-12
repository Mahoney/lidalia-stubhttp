package uk.org.lidalia.http.core.headerfields

import uk.org.lidalia.http.core.{MediaType, HeaderField}

object ContentType extends SingleValueHeaderFieldName[MediaType] {
  def parse(headerFieldValue: String) = ???

  def name = "Content-Type"
}

class ContentType private (val contentType: MediaType) extends HeaderField(ContentType.name, List(contentType.toString))
