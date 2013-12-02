package uk.org.lidalia.http

import uk.org.lidalia.http.response.{Response, Code}
import Code.OK
import uk.org.lidalia.http.headerfields.HeaderField

object ResponseBuilder {

  def response(status: Code = OK, headerFields: List[HeaderField] = List()): Response = Response(status, headerFields)
}
