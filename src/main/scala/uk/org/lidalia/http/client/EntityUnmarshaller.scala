package uk.org.lidalia.http.client

import java.io.InputStream

import uk.org.lidalia.http.core.{Request, ResponseHeader}

object NoopEntityUnmarshaller extends EntityUnmarshaller[None.type] {
  override def unmarshal(request: Request, response: ResponseHeader, entityBytes: InputStream): None.type = None
}

trait EntityUnmarshaller[T] {

  def unmarshal(request: Request, response: ResponseHeader, entityBytes: InputStream): T

}
