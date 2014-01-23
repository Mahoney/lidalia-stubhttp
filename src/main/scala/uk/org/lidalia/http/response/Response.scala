package uk.org.lidalia.http.response

import uk.org.lidalia
import lidalia.http.headerfields.{HeaderFieldName, HeaderField}
import lidalia.http.Message
import lidalia.lang.RichObject

object Response {
  def apply(status: Code, headerFields: List[HeaderField]): Response = new Response(status, Message(headerFields))
  def apply(status: Code, headerFields: HeaderField*): Response = apply(status, headerFields.to[List])
}

class Response private(@Identity val status: Code,
                       @Identity val message: Message) extends RichObject {

  def headerField(headerFieldName: String): List[String] = message.headerField(headerFieldName)

  def headerField[T](headerFieldName: HeaderFieldName[T]): T = message.headerField(headerFieldName)
  def headerField[T](headerFieldName: HeaderFieldName[T]): T = message.headerField(headerFieldName)

  def requiresRedirect: Boolean = status.requiresRedirect

}
