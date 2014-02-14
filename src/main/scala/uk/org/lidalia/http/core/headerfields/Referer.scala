package uk.org.lidalia.http.core.headerfields

import uk.org.lidalia.net2.Uri

object Referer extends UriHeaderFieldName {

  def apply(uri: Uri) = new Referer(uri)

  def name: String = "Referer"

}

class Referer private (uri: Uri) extends UriHeaderField(Referer.name, uri)
