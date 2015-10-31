package uk.org.lidalia.http.client

import java.io.InputStream

import org.apache.commons.io.IOUtils
import uk.org.lidalia.http.core.{Request, ResponseHeader}
import uk.org.lidalia.lang.UnsignedByte

import scala.collection.immutable

object NoopEntityUnmarshaller extends EntityUnmarshaller[None.type] {
  override def unmarshal(request: Request[_, None.type], response: ResponseHeader, entityBytes: InputStream): None.type = None
}

object BytesUnmarshaller extends EntityUnmarshaller[immutable.Seq[UnsignedByte]] {
  override def unmarshal(request: Request[_, immutable.Seq[UnsignedByte]], response: ResponseHeader, entityBytes: InputStream): immutable.Seq[UnsignedByte] = {
    immutable.Seq(IOUtils.toByteArray(entityBytes).map(UnsignedByte(_)):_*)
  }
}

trait EntityUnmarshaller[T] {

  def unmarshal(request: Request[_, T], response: ResponseHeader, entityBytes: InputStream): T

}
