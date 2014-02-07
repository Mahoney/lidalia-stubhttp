package uk.org.lidalia.http

import uk.org.lidalia.net2.Uri

object Response {
  def apply(status: Code, headerFields: List[HeaderField]): Response = new Response(ResponseHeader(status, headerFields))
  def apply(status: Code, headerFields: HeaderField*): Response = apply(status, headerFields.to[List])
}

class Response private(private val responseHeader: ResponseHeader) extends Message(responseHeader) {

  def requiresRedirect: Boolean = responseHeader.requiresRedirect

  val location: ?[Uri] = responseHeader.location

}
