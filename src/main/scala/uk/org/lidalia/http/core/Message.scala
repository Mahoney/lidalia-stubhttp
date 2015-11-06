package uk.org.lidalia.http.core

import java.io.InputStream
import java.lang.System.lineSeparator
import java.nio.charset.StandardCharsets.UTF_8

import org.apache.commons.io.IOUtils
import uk.org.lidalia.http.core.headerfields.ContentType
import uk.org.lidalia.lang.RichObject

abstract class Message[+T] private[http](
  @Identity val header: MessageHeader,
  @Identity val entity: Entity[T]
) extends RichObject {

  def headerFieldValues(headerFieldName: String) = header.headerFieldValues(headerFieldName)

  def headerField(headerFieldName: String) = header.headerField(headerFieldName)

  def headerField[A](headerFieldName: HeaderFieldName[A]) = header.headerField(headerFieldName)

  val headerFields = header.headerFields

  val headerFieldMap = header.headerFieldMap

  val contentType: ?[MediaType] = header.contentType

  override def toString = {
    val contentType = header.headerField(ContentType).getOrElse(MediaType.`application/octet-stream`)
    val marshall: InputStream = entity.marshall(contentType)
    val asString = IOUtils.toString(marshall, contentType.charset.getOrElse(UTF_8))
    header+lineSeparator()+lineSeparator()+asString
  }
}
