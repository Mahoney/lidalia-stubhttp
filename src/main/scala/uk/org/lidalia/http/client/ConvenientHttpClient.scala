package uk.org.lidalia.http.client

import uk.org.lidalia.http.core.Method._
import uk.org.lidalia.http.core.headerfields.Host
import uk.org.lidalia.http.core.{Method, HeaderField, Request, RequestUri}
import uk.org.lidalia.net2.Url

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
    headerFields: HeaderField*): Result[T] = execute(GET, url, accept, headerFields:_*)

  def head[T](
    url: Url,
    accept: Accept[T],
    headerFields: HeaderField*): Result[T] = execute(HEAD, url, accept, headerFields:_*)

  def delete[T](
    url: Url,
    accept: Accept[T],
    headerFields: HeaderField*): Result[T] = execute(DELETE, url, accept, headerFields:_*)

  def options[T](
    url: Url,
    accept: Accept[T],
    headerFields: HeaderField*): Result[T] = execute(TRACE, url, accept, headerFields:_*)

  def execute[T](
    method: Method,
    url: Url,
    accept: Accept[T],
    headerFields: HeaderField*): Result[T] = {

    decorated.execute(
      new DirectedRequest(
        url.scheme,
        url.hostAndPort,
        Request(
          method,
          RequestUri(url.pathAndQuery),
          List(
            Host := url.hostAndPort,
            accept
          ) ++ headerFields.toSeq
        ),
        accept
      )
    )
  }

  override def execute[T](request: DirectedRequest[T]) = decorated.execute(request)
}
