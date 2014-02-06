package uk.org.lidalia.http

import uk.org.lidalia.lang.RichObject
import uk.org.lidalia.http.headerfields.HeaderFieldName

abstract class Message private[http](@Identity private val header: MessageHeader) extends RichObject {

  def headerFieldValues(headerFieldName: String) = header.headerFieldValues(headerFieldName)

  def headerField(headerFieldName: String) = header.headerField(headerFieldName)

  def headerField[T](headerFieldName: HeaderFieldName[T]) = header.headerField(headerFieldName)

}
