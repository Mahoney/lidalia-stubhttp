package uk.org.lidalia.http.core

import uk.org.lidalia.http.client.{NoopEntityMarshaller, BytesUnmarshaller, NoopEntityUnmarshaller, EntityMarshaller, EntityUnmarshaller, ContentType, Accept}
import uk.org.lidalia.lang.UnsignedByte
import uk.org.lidalia.net2.Uri

import scala.collection.immutable
import scala.collection.immutable.Seq

object Request {

  def apply(
    method: Method,
    uri: RequestUri,
    headerFields: Seq[HeaderField]
  ): Request[Seq[UnsignedByte], None.type] = {
    new Request(
      RequestHeader(
        method,
        uri,
        headerFields
      ),
      NoopEntityMarshaller,
      BytesUnmarshaller,
      None
    )
  }

  def apply[A](
    method: Method,
    uri: RequestUri,
    accept: Accept[A],
    headerFields: immutable.Seq[HeaderField]
  ) = {
    new Request(
      RequestHeader(
        method,
        uri,
        List(accept) ++ headerFields
      ),
      NoopEntityMarshaller,
      accept,
      None
    )
  }

  def apply[C](
    method: Method,
    uri: RequestUri,
    contentType: ContentType[C],
    headerFields: immutable.Seq[HeaderField],
    entity: C
  ) = {
    new Request(
      RequestHeader(
        method,
        uri,
        List(contentType) ++ headerFields
      ),
      contentType,
      NoopEntityUnmarshaller,
      entity
    )
  }

  def apply(
    method: Method,
    uri: RequestUri,
    contentType: ContentType[_],
    headerFields: immutable.Seq[HeaderField]
  ) = {
    new Request(
      RequestHeader(
        method,
        uri,
        List(contentType) ++ headerFields
      ),
      NoopEntityMarshaller,
      NoopEntityUnmarshaller,
      None
    )
  }

  def apply[A, C](
    method: Method,
    uri: RequestUri,
    accept: Accept[A],
    contentType: ContentType[C],
    headerFields: immutable.Seq[HeaderField],
    entity: C
  ) = {
    new Request(
      RequestHeader(
        method,
        uri,
        List(accept, contentType) ++ headerFields
      ),
      contentType,
      accept,
      entity
    )
  }

  def apply[A](
    method: Method,
    uri: RequestUri,
    accept: Accept[A],
    contentType: ContentType[_],
    headerFields: immutable.Seq[HeaderField]
  ) = {
    new Request(
      RequestHeader(
        method,
        uri,
        List(accept, contentType) ++ headerFields
      ),
      NoopEntityMarshaller,
      accept,
      None
    )
  }

  def apply(reqStr: String): Request[Seq[UnsignedByte], String] = ???
}

class Request[+A, +C] private(
  override val header: RequestHeader,
  marshaller: EntityMarshaller[C],
  unmarshaller: EntityUnmarshaller[A],
  entity: C
) extends Message[C](header, marshaller, entity) {

  def withMethod(method: Method) = new Request(RequestHeader(method, requestUri, header.headerFields), marshaller, unmarshaller, entity)

  def withUri(newUri: RequestUri) = new Request(RequestHeader(method, newUri, header.headerFields), marshaller, unmarshaller, entity)

  val method = header.method

  val requestUri = header.requestUri

  def referer: ?[Uri] = header.referer
}
