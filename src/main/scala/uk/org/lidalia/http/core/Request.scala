package uk.org.lidalia.http.core

import uk.org.lidalia.net2.Uri

object Request {
  def apply[T](method: Method,
               requestUri: RequestUri,
               responseHandler: ResponseHandler[T],
               headerFields: List[HeaderField] = Nil) = new Request(RequestHeader(method, requestUri, headerFields), responseHandler)
}

class Request[+T] private(private val requestHeader: RequestHeader, val responseHandler: ResponseHandler[T]) extends Message(requestHeader) {

  def withUri(newUri: Uri): Request[T] = {
    Request(method, RequestUri(newUri), responseHandler, header.headerFields)
  }

  val method = requestHeader.method

  val requestUri = requestHeader.requestUri

  def referer: ?[Uri] = requestHeader.referer

}
