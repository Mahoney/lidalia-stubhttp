package uk.org.lidalia.http.core

import uk.org.lidalia.http.core.headerfields.ContentType
import uk.org.lidalia.lang.RichObject

import scala.collection.immutable.Seq

abstract class MessageHeader private[http](val headerFields: Seq[HeaderField]) extends RichObject {

  @Identity val headerFieldMap: Map[String, HeaderField] = headerFields.groupBy(_.name).map({
    case (name, headerFieldsForName) => (name, HeaderField(name, headerFieldsForName.map(_.values).flatten))
  })

  def headerField[T](headerFieldName: HeaderFieldName[T]): T = {
    val values = headerFieldValues(headerFieldName.name)
    headerFieldName.parse(values)
  }

  def headerFieldValues(headerFieldName: String): Seq[String] = headerField(headerFieldName).?(_.values) or List()

  def headerField(headerFieldName: String): ?[HeaderField] = headerFieldMap.get(headerFieldName)

  lazy val contentType = headerField(ContentType)

  override def toString = headerFields.map(_.toString).mkString("\r\n")
}
