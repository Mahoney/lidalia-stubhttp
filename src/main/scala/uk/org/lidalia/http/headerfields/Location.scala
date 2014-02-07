package uk.org.lidalia.http.headerfields

import uk.org.lidalia.net2.Uri
import uk.org.lidalia.http.{HeaderFieldName, HeaderField}

object Location extends HeaderFieldName[?[Uri]] {

  def apply(uri: Uri) = new Location(uri)

  def name: String = "Location"

  def parse(headerFieldValues: List[String]) = headerFieldValues.headOption.map(Uri(_))

}

class Location private (uri: Uri) extends HeaderField(Location.name, List(uri.toString))
