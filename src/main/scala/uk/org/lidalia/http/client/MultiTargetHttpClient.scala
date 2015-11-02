package uk.org.lidalia.http.client

import uk.org.lidalia.http.core.headerfields.Host
import uk.org.lidalia.http.core.{Method, RequestUri, Request, HeaderField}
import uk.org.lidalia.http.core.Method.{TRACE, DELETE, HEAD, GET}
import uk.org.lidalia.lang.UnsignedByte
import uk.org.lidalia.net2.Url

import scala.collection.immutable

class MultiTargetHttpClient {

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
      Request(
        HEAD,
        RequestUri(url.pathAndQuery),
        List(Host := url.hostAndPort) ++ headerFields.toSeq
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
      Request(
        method,
        RequestUri(url.pathAndQuery),
        List(Host := url.hostAndPort) ++ headerFields.toSeq
      )
    )
  }
}
