package uk.org.lidalia.http

import uk.org.lidalia.http.headerfields.Location
import uk.org.lidalia.net2.Uri

object ResponseHeader {
    def apply(status: Code, headerFields: List[HeaderField]): ResponseHeader = new ResponseHeader(status, headerFields)
    def apply(status: Code, headerFields: HeaderField*): MessageHeader = apply(status, headerFields.to[List])
}

class ResponseHeader private(@Identity val code: Code,
                             private val respHeaderFieldsList: List[HeaderField]) extends MessageHeader(respHeaderFieldsList) {

  def requiresRedirect: Boolean = code.requiresRedirect

  lazy val location: ?[Uri] = headerField(Location)
}
