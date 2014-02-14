package uk.org.lidalia.http.core

import uk.org.lidalia
import lidalia.net2.Uri
import lidalia.http.core.headerfields.{LastModified, Age, Location, Date, Etag}
import org.joda.time.{Duration, DateTime}

object ResponseHeader {
    def apply(status: Code, headerFields: List[HeaderField]): ResponseHeader = new ResponseHeader(status, headerFields)
    def apply(status: Code, headerFields: HeaderField*): MessageHeader = apply(status, headerFields.to[List])
}

class ResponseHeader private(@Identity val code: Code,
                             headerFields: List[HeaderField]) extends MessageHeader(headerFields) {

  def requiresRedirect: Boolean = code.requiresRedirect

  lazy val location: ?[Uri] = headerField(Location)

  lazy val date: ?[DateTime] = headerField(Date)

  lazy val age: ?[Duration] = headerField(Age)

  lazy val lastModified: ?[DateTime] = headerField(LastModified)

  lazy val etag: ?[String] = headerField(Etag)
}
