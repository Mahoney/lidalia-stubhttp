package uk.org.lidalia.http

import uk.org.lidalia
import lidalia.http.headerfields.{HeaderFieldName, HeaderField}
import lidalia.lang.RichObject

object Message {
  def apply(headerFields: List[HeaderField]): Message = new Message(headerFields)
  def apply(headerFields: HeaderField*): Message = apply(headerFields.to[List])
}

class Message private(@Identity val headerFieldsList: List[HeaderField]) extends RichObject {

  private val headerFields: Map[String, List[String]] = Map(headerFieldsList.map(headerField => headerField.name -> headerField.values):_*)

  def headerField(headerFieldName: String): List[String] = headerFields.get(headerFieldName) or List()

  def headerField[T](headerFieldName: HeaderFieldName[T]): T = {
    val fields: List[String] = headerField(headerFieldName.name)
    headerFieldName.parse(fields)
  }
}
