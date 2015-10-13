package uk.org.lidalia.net2

import uk.org.lidalia.lang.{PercentEncodedString, PercentEncodedStringFactory}
import uk.org.lidalia.net2.UriConstants.pchar

object PathElement extends PercentEncodedStringFactory[PathElement](pchar) {

  def apply(pathElementStr: String) = new PathElement(pathElementStr)

}

class PathElement private (pathStr: String)
    extends PercentEncodedString[PathElement](PathElement, pathStr)
