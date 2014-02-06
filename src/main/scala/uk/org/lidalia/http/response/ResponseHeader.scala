package uk.org.lidalia.http.response

import uk.org.lidalia.http._
import uk.org.lidalia.http.headerfields.HeaderField

object ResponseHeader {
    def apply(status: Code, headerFields: List[HeaderField]): ResponseHeader = new ResponseHeader(status, headerFields)
    def apply(status: Code, headerFields: HeaderField*): MessageHeader = apply(status, headerFields.to[List])
}

class ResponseHeader private(@Identity val code: Code,
                             private val headerFieldsList: List[HeaderField]) extends MessageHeader(headerFieldsList) {

  def requiresRedirect: Boolean = code.requiresRedirect

}
