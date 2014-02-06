package uk.org.lidalia.http.request

import uk.org.lidalia.http._
import uk.org.lidalia.http.headerfields.HeaderField
import uk.org.lidalia.net2.Uri

object RequestHeader {
    def apply(method: Method, uri: Uri, headerFields: List[HeaderField]): RequestHeader = new RequestHeader(method, uri, headerFields)
    def apply(method: Method, uri: Uri, headerFields: HeaderField*): MessageHeader = apply(method, uri, headerFields.to[List])
}

class RequestHeader private(
                               @Identity val method: Method,
                               @Identity val uri: Uri,
                               private val reqHeaderFieldsList: List[HeaderField]) extends MessageHeader(reqHeaderFieldsList) {

}
