package uk.org.lidalia.http.core.headerfields

import uk.org.lidalia.http.core.{MediaType, HeaderField}

import scala.util.Try

object ContentType extends SingleValueHeaderFieldName[MediaType] {
  override def parse(headerFieldValue: String) = Try(new MediaType(headerFieldValue)).toOption

  override def name = "Content-Type"

  override def apply(value: MediaType): HeaderField = new ContentType(value)
}

class ContentType private[http] (val contentType: MediaType) extends HeaderField(ContentType.name, List(contentType.toString))
