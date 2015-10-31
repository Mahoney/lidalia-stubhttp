package uk.org.lidalia.http.client

import java.io.InputStream

import uk.org.lidalia.http.core.{Message, Request, ResponseHeader}


trait EntityMarshaller[T] {

  def marshal(message: Message[T]): InputStream

}

object NoopEntityMarshaller extends EntityMarshaller[None.type] {
  override def marshal(message: Message[None.type]): InputStream = ???
}