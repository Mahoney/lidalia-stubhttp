package uk.org.lidalia.http.client

import java.io.InputStream

import uk.org.lidalia.http.core.{ResponseHeader, MediaRangePref}

import scala.collection.immutable.Seq

abstract class Accept[T](mediaRangePrefs: Seq[MediaRangePref])
  extends uk.org.lidalia.http.core.headerfields.Accept(mediaRangePrefs)
  with EntityUnmarshaller[T] {
}
