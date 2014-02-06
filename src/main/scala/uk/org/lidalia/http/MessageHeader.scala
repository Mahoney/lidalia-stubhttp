package uk.org.lidalia.http

import uk.org.lidalia
import lidalia.http.headerfields.{HeaderFieldName, HeaderField}
import lidalia.lang.RichObject

abstract class MessageHeader private[http](@Identity private val headerFieldsList: List[HeaderField]) extends RichObject {

  private val headerFields: Map[String, HeaderField] = Map(headerFieldsList.map(headerField => headerField.name -> headerField):_*)

  def headerField[T](headerFieldName: HeaderFieldName[T]): T = {
    val values = headerFieldValues(headerFieldName.name)
    headerFieldName.parse(values)
  }

  def headerFieldValues(headerFieldName: String): List[String] = headerField(headerFieldName).?(_.values) or List()

  def headerField(headerFieldName: String): ?[HeaderField] = headerFields.get(headerFieldName)
}
