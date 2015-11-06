package uk.org.lidalia.http.core

import Code.OK
import uk.org.lidalia.http.client.{NoopEntityMarshaller, StringEntityMarshaller, EitherEntityMarshaller, ContentType}

object ResponseBuilder {

  def response[T](
    status: Code = OK,
    headerFields: List[HeaderField] = Nil,
    body: Entity[Either[String, T]] = new EitherEntity(Right(EmptyEntity))
  ): Response[Either[String, T]] = {
    Response(
      status,
      headerFields,
      body
    )
  }
}
