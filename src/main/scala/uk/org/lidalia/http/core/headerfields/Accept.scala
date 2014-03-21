package uk.org.lidalia.http.core.headerfields

import uk.org.lidalia.http.core.{MediaType, MediaRangePref, HeaderField, HeaderFieldName}

object Accept extends HeaderFieldName[Accept] {
  def parse(headerFieldValues: List[String]) = ???

  def name = "Accept"
}

class Accept(val mediaRangePrefs: List[MediaRangePref]) extends HeaderField(Accept.name, mediaRangePrefs.map(_.toString)) {

  def preferredTypeFor(availableTypes: List[MediaType]): MediaType = ???
}

