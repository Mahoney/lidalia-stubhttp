package uk.org.lidalia.http.headerfields

import uk.org.lidalia.http.{HeaderField, HeaderFieldName}
import uk.org.lidalia.net2.Uri

object Referer extends HeaderFieldName[?[Uri]] {

  def apply(uri: Uri) = new Location(uri)

  def name: String = "Referer"

  def parse(headerFieldValues: List[String]) = headerFieldValues.headOption.map(Uri(_))

}

class Referer private (uri: Uri) extends HeaderField(Location.name, List(uri.toString))
