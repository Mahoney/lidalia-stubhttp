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

class Request private(private val requestHeader: RequestHeader) extends Message(requestHeader, None) {

  def withUri(newUri: RequestUri): Request = {
    Request(method, newUri, header.headerFields)
  }

  val method = requestHeader.method

  val requestUri = requestHeader.requestUri

  def referer: ?[Uri] = requestHeader.referer
}
