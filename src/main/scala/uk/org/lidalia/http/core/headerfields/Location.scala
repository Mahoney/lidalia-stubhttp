package uk.org.lidalia.http.core.headerfields

import uk.org.lidalia
import lidalia.net2.Uri
import lidalia.http.core.{HeaderFieldName, HeaderField}

object Location extends HeaderFieldName[?[Uri]] {

  def apply(uri: Uri) = new Location(uri)

  def name: String = "Location"

  def parse(headerFieldValues: List[String]) = headerFieldValues.headOption.map(Uri(_))

}

class Location private (uri: Uri) extends HeaderField(Location.name, List(uri.toString))
