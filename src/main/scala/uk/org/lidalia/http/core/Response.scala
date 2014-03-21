package uk.org.lidalia.http.core

import uk.org.lidalia.net2.Uri

object Response {
  def apply[T](
               status: Code,
               headerFields: List[HeaderField] = Nil,
               body: T = None
               ): Response[T] = new Response(ResponseHeader(status, headerFields), body)
}

class Response[+T] private(private val responseHeader: ResponseHeader, val body: T) extends Message(responseHeader) {

  val code = responseHeader.code

  def requiresRedirect: Boolean = responseHeader.requiresRedirect

  val location: ?[Uri] = responseHeader.location

}
