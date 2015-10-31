package uk.org.lidalia.http.client

import uk.org.lidalia.http.core.Method._
import uk.org.lidalia.http.core.headerfields.Host
import uk.org.lidalia.http.core.{Method, HeaderField, Request, RequestUri}
import uk.org.lidalia.lang.UnsignedByte
import uk.org.lidalia.net2.Url
import scala.collection.immutable

object ConvenientHttpClient {
  def apply[Result[_]](
    decorated: BaseHttpClient[Result] = ExpectedEntityHttpClient()) = {
    new ConvenientHttpClient(decorated)
  }
}

class ConvenientHttpClient[Result[_]](decorated: BaseHttpClient[Result]) extends BaseHttpClient[Result] {

  def get[T](
    url: Url,
    accept: Accept[T],
    headerFields: HeaderField*
  ) = execute(GET, url, accept, headerFields:_*)

  def get(
    url: Url,
    headerFields: HeaderField*
  ) = execute(GET, url, headerFields:_*)

  def head(
    url: Url,
    headerFields: HeaderField*) = {
    decorated.execute(
      requestFor(
        HEAD,
        url,
        headerFields.toList,
        NoopEntityUnmarshaller,
        List(
          Host := url.hostAndPort
        )
      )
    )
  }

  def delete[T](
    url: Url,
    accept: Accept[T],
    headerFields: HeaderField*) = execute(DELETE, url, accept, headerFields:_*)

  def delete(
     url: Url,
     headerFields: HeaderField*) = execute(DELETE, url, headerFields:_*)

  def options[T](
    url: Url,
    accept: Accept[T],
    headerFields: HeaderField*) = execute(TRACE, url, accept, headerFields:_*)

  def options(
    url: Url,
    headerFields: HeaderField*) = execute(TRACE, url, headerFields:_*)

  def execute[T](
    method: Method,
    url: Url,
    accept: Accept[T],
    headerFields: HeaderField*): Result[T] = {

    decorated.execute(
      requestFor(method, url, headerFields.toList, accept, List(
        Host := url.hostAndPort,
        accept
      ))
    )
  }

  def execute(
    method: Method,
    url: Url,
    headerFields: HeaderField*
  ): Result[immutable.Seq[UnsignedByte]] = {
    decorated.execute(
      DirectedRequest(
        url.scheme,
        url.hostAndPort,
        Request(
          method,
          RequestUri(url.pathAndQuery),
          List(Host := url.hostAndPort) ++ headerFields.toSeq
        ),
        BytesUnmarshaller
      )
    )
  }

  private def requestFor[T](
    method: Method,
    url: Url,
    headerFields: immutable.Seq[HeaderField],
    accept: Accept[T],
    baseFields: List[HeaderField]): DirectedRequest[T] =
  {
    DirectedRequest(
      url.scheme,
      url.hostAndPort,
      Request(
        method,
        RequestUri(url.pathAndQuery),
        accept,
        baseFields ++ headerFields.toSeq
      ),
      accept
    )
  }

  override def execute[T](request: DirectedRequest[T]) = decorated.execute(request)
}
