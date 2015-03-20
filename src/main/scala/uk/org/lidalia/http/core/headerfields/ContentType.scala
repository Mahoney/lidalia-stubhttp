package uk.org.lidalia.http.core.headerfields

import uk.org.lidalia.http.core.{MediaType, HeaderField}

import scala.util.Try

object ContentType extends SingleValueHeaderFieldName[MediaType] {
  def parse(headerFieldValue: String) = Try(new MediaType(headerFieldValue)).toOption

  def name = "Content-Type"
}

class ContentType private (val contentType: MediaType) extends HeaderField(ContentType.name, List(contentType.toString))
