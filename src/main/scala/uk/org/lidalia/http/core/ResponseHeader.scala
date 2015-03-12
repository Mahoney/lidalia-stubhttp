package uk.org.lidalia.http.core

import uk.org.lidalia
import lidalia.net2.Uri
import lidalia.http.core.headerfields.{LastModified, Age, Location, Date, Etag, ContentType}
import org.joda.time.{Duration, DateTime}

object ResponseHeader {
    def apply(
               status: Code,
               reason: Reason,
               headerFields: List[HeaderField]
               ): ResponseHeader = new ResponseHeader(status, reason, headerFields)

    def apply(
             status: Code,
             headerFields: List[HeaderField]
             ): ResponseHeader = new ResponseHeader(status, status.defaultReason or Reason(""), headerFields)
}

class ResponseHeader private(@Identity val code: Code,
                             @Identity val reason: Reason,
                             headerFields: List[HeaderField]) extends MessageHeader(headerFields) {

  def requiresRedirect: Boolean = code.requiresRedirect

  lazy val location: ?[Uri] = headerField(Location)

  lazy val date: ?[DateTime] = headerField(Date)

  lazy val age: ?[Duration] = headerField(Age)

  lazy val lastModified: ?[DateTime] = headerField(LastModified)

  lazy val etag: ?[String] = headerField(Etag)

  lazy val contentType: ?[MediaType] = headerField(ContentType)

  override def toString = s"HTTP/1.1 $code $reason\r\n${super.toString}"
}
