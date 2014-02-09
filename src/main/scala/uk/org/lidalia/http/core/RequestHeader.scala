package uk.org.lidalia.http.core

import uk.org.lidalia
import lidalia.net2.Uri
import lidalia.http.core.headerfields.Referer

object RequestHeader {
    def apply(method: Method, uri: Uri, headerFields: List[HeaderField]): RequestHeader = new RequestHeader(method, uri, headerFields)
    def apply(method: Method, uri: Uri, headerFields: HeaderField*): MessageHeader = apply(method, uri, headerFields.to[List])
}

class RequestHeader private(
                               @Identity val method: Method,
                               @Identity val uri: Uri,
                               private val reqHeaderFieldsList: List[HeaderField]) extends MessageHeader(reqHeaderFieldsList) {

  lazy val referer: ?[Uri] = headerField(Referer)

}
