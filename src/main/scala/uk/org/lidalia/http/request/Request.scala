package uk.org.lidalia.http.request

import uk.org.lidalia

import lidalia.net2.Uri
import lidalia.lang.RichObject
import uk.org.lidalia.http._
import uk.org.lidalia.http.headerfields.HeaderField

object Request {
  def apply(method: Method, uri: Uri, headerFields: List[HeaderField]) = new Request(RequestHeader(method, uri, headerFields))
  def apply(method: Method, uri: Uri, headerFields: HeaderField*) = new Request(RequestHeader(method, uri, headerFields.to[List]))
}

class Request private(private val header: RequestHeader) extends Message(header) {

  def withUri(newUri: Uri): Request = {
    Request(method, newUri, header.headerFieldsList)
  }

  val method = header.method

}
