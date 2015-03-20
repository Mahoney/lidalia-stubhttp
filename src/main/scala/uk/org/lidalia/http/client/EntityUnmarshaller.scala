package uk.org.lidalia.http.client

import java.io.InputStream

import uk.org.lidalia.http.core.{Request, ResponseHeader}

trait EntityUnmarshaller[T] {

  def unmarshal(request: Request, response: ResponseHeader, entityBytes: InputStream): T

}
