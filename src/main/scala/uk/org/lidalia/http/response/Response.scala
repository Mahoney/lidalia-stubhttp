package uk.org.lidalia.http.response

import uk.org.lidalia
import lidalia.http.headerfields.{HeaderFieldName, HeaderField}
import lidalia.lang.RichObject

object Response {
  def apply(status: Code, headerFields: List[HeaderField]): Response = new Response(ResponseHeader(status, headerFields))
  def apply(status: Code, headerFields: HeaderField*): Response = apply(status, headerFields.to[List])
}

class Response private(@Identity private val header: ResponseHeader) extends RichObject {

  def requiresRedirect: Boolean = header.requiresRedirect

  def headerFieldValues(headerFieldName: String) = header.headerFieldValues(headerFieldName)

  def headerField(headerFieldName: String) = header.headerField(headerFieldName)

  def headerField[T](headerFieldName: HeaderFieldName[T]) = header.headerField(headerFieldName)
}
