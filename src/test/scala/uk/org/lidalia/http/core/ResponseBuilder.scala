package uk.org.lidalia.http.core

import Code.OK

object ResponseBuilder {

  def response[T](status: Code = OK,
               headerFields: List[HeaderField] = Nil,
               body: T = None): Response[T] = Response(status, headerFields, body)
}
