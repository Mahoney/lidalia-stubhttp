package uk.org.lidalia.http.core

import Method.GET
import uk.org.lidalia.http.client.{TargetedRequest, DirectedRequest, Accept}
import uk.org.lidalia.net2.Scheme.HTTP
import uk.org.lidalia.net2.{Url, SchemeWithDefaultPort, Scheme, HostAndPort, Uri}
import java.io.InputStream

object RequestBuilder {

  def request[T](
        method: Method = GET,
        scheme: Scheme = HTTP,
        hostAndPort: HostAndPort = HostAndPort("localhost"),
        uri: RequestUri = RequestUri("/mypath"),
        accept: Accept[T] = new Accept[None.type](List()) {
          def unmarshal(request: Request, response: ResponseHeader, entityBytes: InputStream) = None
        }): DirectedRequest[T] = {
    DirectedRequest(scheme, hostAndPort, Request(method, uri), accept)
  }

  def get(uri: RequestUri = RequestUri("/mypath")) = request(GET, uri = uri)

  def get(url: Url) = request(GET, url.scheme, url.hostAndPort, uri = RequestUri(url.pathAndQuery))
}
