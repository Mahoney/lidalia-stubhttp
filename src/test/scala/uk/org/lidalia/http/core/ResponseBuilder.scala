package uk.org.lidalia.http.core

import Code.OK

object ResponseBuilder {

  def response(status: Code = OK, headerFields: List[HeaderField] = List()): Response = Response(status, headerFields)
}
