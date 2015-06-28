package uk.org.lidalia.http.core

import uk.org.lidalia.net2.Uri

import scala.collection.immutable.Seq

object Request {
  def apply(
        method: Method,
        requestUri: RequestUri,
        headerFields: Seq[HeaderField] = Nil) =
    new Request(
      RequestHeader(
        method,
        requestUri,
        headerFields
      )
    )
}

class Request private(override val header: RequestHeader) extends Message(header, None) {

  def withMethod(method: Method) = Request(method, requestUri, header.headerFields)

  def withUri(newUri: RequestUri) = Request(method, newUri, header.headerFields)

  val method = header.method

  val requestUri = header.requestUri

  def referer: ?[Uri] = header.referer
}
