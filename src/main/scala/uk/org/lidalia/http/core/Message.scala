package uk.org.lidalia.http.core

import java.lang.System.lineSeparator

import uk.org.lidalia.http.client.{EntityMarshaller, EntityUnmarshaller}
import uk.org.lidalia.lang.RichObject

abstract class Message[+T] private[http](
  @Identity val header: MessageHeader,
  val marshaller: EntityMarshaller[T],
  @Identity val entity: T
) extends RichObject {

  def headerFieldValues(headerFieldName: String) = header.headerFieldValues(headerFieldName)

  def headerField(headerFieldName: String) = header.headerField(headerFieldName)

  def headerField[A](headerFieldName: HeaderFieldName[A]) = header.headerField(headerFieldName)

  val headerFields = header.headerFields

  val headerFieldMap = header.headerFieldMap

  override def toString = {
    header+lineSeparator()+lineSeparator()+entity.toString
  }
}
