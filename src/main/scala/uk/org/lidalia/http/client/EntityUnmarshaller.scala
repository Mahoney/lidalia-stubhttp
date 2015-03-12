package uk.org.lidalia.http.client

import java.io.InputStream

import uk.org.lidalia.http.core.ResponseHeader

trait EntityUnmarshaller[T] {

  def unmarshal(request: TargetedRequest[T], response: ResponseHeader, entityBytes: InputStream): T

}
