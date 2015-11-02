package uk.org.lidalia.http.core

import uk.org.lidalia.http.client.{StringEntityMarshaller, NoopEntityMarshaller, ContentType, EntityMarshaller}
import uk.org.lidalia.net2.{Url, Uri}
import org.joda.time.DateTime

object Response {

  def apply[T](
    status: Code,
    contentType: ContentType[T],
    headerFields: List[HeaderField] = Nil,
    body: T
  ): Response[T] = {
    new Response(ResponseHeader(status, contentType :: headerFields), contentType, body)
  }

  def apply[T](
    header: ResponseHeader,
    body: String
  ): Response[String] = {
    new Response(header, StringEntityMarshaller, body)
  }

  def apply(
    status: Code,
    headerFields: List[HeaderField] = Nil
  ): Response[None.type] = {
    new Response(ResponseHeader(status, headerFields), NoopEntityMarshaller, None)
  }
}

class Response[+T] private(override val header: ResponseHeader, entityMarshaller: EntityMarshaller[T], entity: T) extends Message(header, entityMarshaller, entity) {

  val code = header.code

  def requiresRedirect: Boolean = header.requiresRedirect

  def location: ?[Url] = header.location

  def date: ?[DateTime] = header.date

  def isNotError: Boolean = header.isNotError
  def isInformational: Boolean = header.isInformational
  def isSuccessful: Boolean = header.isSuccessful
  def isRedirection: Boolean = header.isRedirection
  def isClientError: Boolean = header.isClientError
  def isServerError: Boolean = header.isServerError
  def isError: Boolean = header.isError
}
