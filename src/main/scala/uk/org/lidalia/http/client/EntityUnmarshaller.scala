package uk.org.lidalia.http.client

import java.io.InputStream

import org.apache.commons.io.IOUtils
import uk.org.lidalia.http.core._
import uk.org.lidalia.lang.UnsignedByte

import scala.collection.immutable

object NoopEntityUnmarshaller extends EntityUnmarshaller[None.type] {
  override def unmarshal(request: Request[None.type, _], response: ResponseHeader, entityBytes: InputStream) = EmptyEntity
}

object BytesUnmarshaller extends EntityUnmarshaller[immutable.Seq[UnsignedByte]] {
  override def unmarshal(request: Request[immutable.Seq[UnsignedByte], _], response: ResponseHeader, entityBytes: InputStream): Entity[immutable.Seq[UnsignedByte]] = {
    new ByteEntity(immutable.Seq(IOUtils.toByteArray(entityBytes).map(UnsignedByte(_)):_*))
  }
}

trait EntityUnmarshaller[T] {

  def unmarshal(request: Request[T, _], response: ResponseHeader, entityBytes: InputStream): Entity[T]

}
