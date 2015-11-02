package uk.org.lidalia.http.client

import java.io.InputStream

import uk.org.lidalia.http.core.{MessageHeader, Message, Request, ResponseHeader}


trait EntityMarshaller[T] {

  def marshal(header: MessageHeader, entity: T): InputStream

}

object NoopEntityMarshaller extends EntityMarshaller[None.type] {
  override def marshal(header: MessageHeader, entity: None.type ): InputStream = ???
}

object StringEntityMarshaller extends EntityMarshaller[String] {
  override def marshal(header: MessageHeader, entity: String): InputStream = ???
}
