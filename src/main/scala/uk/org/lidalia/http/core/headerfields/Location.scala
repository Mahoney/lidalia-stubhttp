package uk.org.lidalia.http.core.headerfields

import uk.org.lidalia.net2.Uri

object Location extends UriHeaderFieldName {

  def apply(uri: Uri) = new Location(uri)

  def name: String = "Location"

}

class Location private (uri: Uri) extends UriHeaderField(Location.name, uri)
