package uk.org.lidalia.http.core.headerfields

import uk.org.lidalia.http.core.{MediaType, MediaRangePref, HeaderField}

import scala.collection.immutable.Seq

object Accept extends MultiValueHeaderFieldName[MediaRangePref] {
  override def parse(headerFieldValues: String) = ???

  override def name = "Accept"

  override def apply(value: MediaRangePref, values: MediaRangePref*): HeaderField = new Accept(value :: values.toList)
}

class Accept(val mediaRangePrefs: Seq[MediaRangePref]) extends HeaderField(Accept.name, mediaRangePrefs.map(_.toString)) {

  def preferredTypeFor(availableTypes: Seq[MediaType]): MediaType = ???
}

