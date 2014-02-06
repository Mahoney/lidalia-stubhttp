package uk.org.lidalia.http.response

import uk.org.lidalia
import lidalia.http.headerfields.HeaderField
import uk.org.lidalia.http.Message

object Response {
  def apply(status: Code, headerFields: List[HeaderField]): Response = new Response(ResponseHeader(status, headerFields))
  def apply(status: Code, headerFields: HeaderField*): Response = apply(status, headerFields.to[List])
}

class Response private(private val header: ResponseHeader) extends Message(header) {

  def requiresRedirect: Boolean = header.requiresRedirect

}
