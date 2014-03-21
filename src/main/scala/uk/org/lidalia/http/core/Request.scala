package uk.org.lidalia.http.core

import uk.org.lidalia.net2.{PartialUri, Uri}

object Request {
  def apply[T](method: Method,
               uri: Either[Uri, PartialUri],
               responseHandler: ResponseHandler[T],
               headerFields: List[HeaderField] = Nil) = new Request(RequestHeader(method, uri, headerFields), responseHandler)
}

class Request[+T] private(private val requestHeader: RequestHeader, val responseHandler: ResponseHandler[T]) extends Message(requestHeader) {

  def withUri(newUri: Uri): Request[T] = {
    Request(method, Left(newUri), responseHandler, header.headerFields)
  }

  val method = requestHeader.method

  val uri = requestHeader.uri

  val referer: ?[Uri] = requestHeader.referer

}
