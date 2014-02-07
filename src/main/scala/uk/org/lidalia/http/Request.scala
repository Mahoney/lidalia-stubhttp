package uk.org.lidalia.http

import uk.org.lidalia

import lidalia.net2.Uri
import lidalia.lang.RichObject
import uk.org.lidalia.http._

object Request {
  def apply(method: Method, uri: Uri, headerFields: List[HeaderField]) = new Request(RequestHeader(method, uri, headerFields))
  def apply(method: Method, uri: Uri, headerFields: HeaderField*) = new Request(RequestHeader(method, uri, headerFields.to[List]))
}

class Request private(private val requestHeader: RequestHeader) extends Message(requestHeader) {

  def withUri(newUri: Uri): Request = {
    Request(method, newUri, header.headerFields)
  }

  val method = requestHeader.method

  val referer: ?[Uri] = requestHeader.referer

}
