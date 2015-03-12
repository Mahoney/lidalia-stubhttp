package uk.org.lidalia.http.core

import Method.GET
import uk.org.lidalia.http.client.{TargetedRequest, DirectedRequest, Accept}
import uk.org.lidalia.net2.Scheme.HTTP
import uk.org.lidalia.net2.{SchemeWithDefaultPort, Scheme, HostAndPort, Uri}
import java.io.InputStream

object RequestBuilder {

  def request[T](
        method: Method = GET,
        scheme: SchemeWithDefaultPort = HTTP,
        hostAndPort: HostAndPort = HostAndPort("localhost"),
        uri: RequestUri = RequestUri("/mypath"),
        accept: Accept[T] = new Accept[None.type](List()) {
          def unmarshal(request: TargetedRequest[None.type], response: ResponseHeader, entityBytes: InputStream) = None
        }): DirectedRequest[T] = {
    new DirectedRequest(scheme, hostAndPort, Request(method, uri), accept)
  }

  def get(uri: RequestUri = RequestUri("/mypath")) = request(GET, uri = uri)

  def get(uri: Uri) = request(GET, uri = RequestUri(uri))
}
