package uk.org.lidalia.http.core

import java.lang.System.lineSeparator

import uk.org.lidalia.lang.RichObject

abstract class Message[+T] private[http](
  @Identity val header: MessageHeader,
  @Identity val marshallableEntity: Entity[T]
) extends RichObject {

  def headerFieldValues(headerFieldName: String) = header.headerFieldValues(headerFieldName)

  def headerField(headerFieldName: String) = header.headerField(headerFieldName)

  def headerField[A](headerFieldName: HeaderFieldName[A]) = header.headerField(headerFieldName)

  val headerFields = header.headerFields

  val headerFieldMap = header.headerFieldMap

  lazy val contentType: ?[MediaType] = header.contentType

  val entity = marshallableEntity.entity

  lazy val entityString = marshallableEntity.toString(contentType)

  lazy val entityBytes = marshallableEntity.bytes(contentType)

  override def toString = {
    header+lineSeparator()+
    lineSeparator()+
    entityString
  }
}
