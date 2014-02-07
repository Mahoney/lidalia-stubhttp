package uk.org.lidalia.http

import Method.GET
import uk.org.lidalia.net2.Uri

object RequestBuilder {

  def request(
    method: Method = GET,
    uri: Uri = Uri("http://www.example.com/mypath")
                 ) = Request(method, uri)

  def get(
     uri: Uri = Uri("http://www.example.com/mypath")
             ) = request(GET, uri)
}
