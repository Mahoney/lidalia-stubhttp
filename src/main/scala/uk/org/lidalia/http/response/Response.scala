package uk.org.lidalia.http.response

import uk.org.lidalia
import lidalia.http.headerfields.{HeaderFieldName, HeaderField}
import lidalia.lang.RichObject

object Response {
  def apply(status: Code, headerFields: List[HeaderField]): Response = new Response(status, headerFields)
  def apply(status: Code, headerFields: HeaderField*): Response = apply(status, headerFields.to[List])
}

class Response private(@Identity val status: Code,
                       @Identity val headerFieldsList: List[HeaderField]) extends RichObject {

  private val headerFields: Map[String, List[String]] = Map(headerFieldsList.map(headerField => headerField.name -> headerField.values):_*)

  def headerField(headerFieldName: String): ?[List[String]] = headerFields.get(headerFieldName)

  def headerField[T](headerFieldName: HeaderFieldName[T]): ?[T] = {
    val fields: ?[List[String]] = headerField(headerFieldName.name)
    fields.map(headerFieldName.parse)
  }

  def requiresRedirect: Boolean = status.requiresRedirect

}
