package uk.org.lidalia.http.request

import uk.org.lidalia

import lidalia.net2.Uri
import lidalia.lang.RichObject
import uk.org.lidalia.http._

object Request {
  def apply(method: Method, uri: Uri) = new Request(method, uri)
}

class Request private(
                       @Identity val method: Method,
                       @Identity val uri: Uri) extends RichObject {

  def withUri(newUri: Uri): Request = {
    Request(method, newUri)
  }
}
