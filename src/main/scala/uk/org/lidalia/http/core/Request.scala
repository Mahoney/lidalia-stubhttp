package uk.org.lidalia.http.core

import uk.org.lidalia.http.client.Accept
import uk.org.lidalia.net2.Uri

object Request {
  def apply[T](method: Method,
               requestUri: RequestUri,
               accept: Accept[T],
               headerFields: List[HeaderField] = Nil) = new Request(RequestHeader(method, requestUri, accept :: headerFields), accept)
}

class Request[T] private(private val requestHeader: RequestHeader, val accept: Accept[T]) extends Message(requestHeader) {

  def withUri(newUri: Uri): Request[T] = {
    Request(method, RequestUri(newUri), accept, header.headerFields)
  }

  val method = requestHeader.method

  val requestUri = requestHeader.requestUri

  def referer: ?[Uri] = requestHeader.referer

}
