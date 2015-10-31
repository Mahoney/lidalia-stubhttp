package uk.org.lidalia.http.client

import uk.org.lidalia.http.core.{MediaType, MediaRange, MediaRangePref}

import scala.collection.immutable.Seq

abstract class ContentType[T](mediaType: MediaType)
  extends uk.org.lidalia.http.core.headerfields.ContentType(mediaType)
  with EntityMarshaller[T] {
}
