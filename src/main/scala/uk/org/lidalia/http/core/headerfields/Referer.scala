package uk.org.lidalia.http.core.headerfields

import uk.org.lidalia.net2.Url

object Referer extends UrlHeaderFieldName {

  def apply(url: Url) = new Referer(url)

  def name: String = "Referer"

}

class Referer private (url: Url) extends UriHeaderField(Referer.name, url)
