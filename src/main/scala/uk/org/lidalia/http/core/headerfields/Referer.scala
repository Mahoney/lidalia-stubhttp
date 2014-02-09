package uk.org.lidalia.http.core.headerfields

import uk.org.lidalia
import lidalia.net2.Uri
import lidalia.http.core.{HeaderFieldName, HeaderField}

object Referer extends HeaderFieldName[?[Uri]] {

  def apply(uri: Uri) = new Referer(uri)

  def name: String = "Referer"

  def parse(headerFieldValues: List[String]) = headerFieldValues.headOption.map(Uri(_))

}

class Referer private (uri: Uri) extends HeaderField(Location.name, List(uri.toString))
