package uk.org.lidalia.http.client

import java.io.{ByteArrayInputStream, InputStream}
import java.nio.charset.StandardCharsets
import java.nio.charset.StandardCharsets.UTF_8

import uk.org.lidalia.http.core.{MessageHeader, Message, Request, ResponseHeader}


trait EntityMarshaller[T] {

  def marshal(header: MessageHeader, entity: T): InputStream

}

object NoopEntityMarshaller extends EntityMarshaller[None.type] {
  override def marshal(header: MessageHeader, entity: None.type ): InputStream = ???
}

object StringEntityMarshaller extends EntityMarshaller[String] {
  override def marshal(header: MessageHeader, entity: String): InputStream = new ByteArrayInputStream(entity.getBytes(UTF_8))
}

object AnyEntityMarshaller extends EntityMarshaller[Any] {
  override def marshal(header: MessageHeader, entity: Any): InputStream = new ByteArrayInputStream(entity.toString.getBytes(UTF_8))
}

class EitherEntityMarshaller[A, B] (
  leftMarshaller: EntityMarshaller[A],
  rightMarshaller: EntityMarshaller[B]
) extends EntityMarshaller[Either[A, B]] {
  override def marshal(header: MessageHeader, entity: Either[A, B]): InputStream = {
    entity match {
      case Left(a) => leftMarshaller.marshal(header, a);
      case Right(b) => rightMarshaller.marshal(header, b);
    }
  }
}
