package uk.org.lidalia.http.core

import uk.org.lidalia
import uk.org.lidalia.net2.{PartialUri, Uri}
import lidalia.http.core.headerfields.Referer

object RequestHeader {
    def apply(method: Method, uri: Either[Uri, PartialUri], headerFields: List[HeaderField]): RequestHeader = new RequestHeader(method, uri, headerFields)
    def apply(method: Method, uri: Either[Uri, PartialUri], headerFields: HeaderField*): MessageHeader = apply(method, uri, headerFields.to[List])
}

class RequestHeader private(
                               @Identity val method: Method,
                               @Identity val uri: Either[Uri, PartialUri],
                               private val reqHeaderFieldsList: List[HeaderField]) extends MessageHeader(reqHeaderFieldsList) {

  lazy val referer: ?[Uri] = headerField(Referer)

}
