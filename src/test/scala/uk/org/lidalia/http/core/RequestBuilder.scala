package uk.org.lidalia.http.core

import Method.GET
import uk.org.lidalia.http.client.Accept
import uk.org.lidalia.net2.Uri
import java.io.InputStream

object RequestBuilder {

  def request[T](
    method: Method = GET,
    uri: RequestUri = RequestUri("/mypath"),
    accept: Accept[T] = new Accept[None.type](List()) {
      def handle(content: InputStream) = None
    }) = {
    Request(method, uri, accept)
  }

  def get(uri: RequestUri = RequestUri("/mypath")) = request(GET, uri)

  def get(uri: Uri) = request(GET, RequestUri(uri))
}
