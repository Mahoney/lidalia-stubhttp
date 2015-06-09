package uk.org.lidalia.http.core

import uk.org.lidalia.net2.{Url, Uri}
import org.joda.time.DateTime

import scala.collection.immutable.Seq

object Response {
  def apply[T](
    status: Code,
    headerFields: Seq[HeaderField] = Nil,
    body: T = None): Response[T] = {

    apply(ResponseHeader(status, headerFields), body)
  }

  def apply[T](
    responseHeader: ResponseHeader,
    body: T): Response[T] = {

    new Response(responseHeader, body)
  }
}

class Response[+T] private(override val header: ResponseHeader, entity: T) extends Message(header, entity) {

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
