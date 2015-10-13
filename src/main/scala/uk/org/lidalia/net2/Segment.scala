package uk.org.lidalia.net2

import uk.org.lidalia.lang.{PercentEncodedString, PercentEncodedStringFactory}
import uk.org.lidalia.net2.UriConstants.pchar

object Segment extends PercentEncodedStringFactory[Segment](pchar) {

  def apply(pathElementStr: String) = new Segment(pathElementStr)

  val emptySegment = Segment("")

}

class Segment private (pathStr: String)
    extends PercentEncodedString[Segment](Segment, pathStr)

