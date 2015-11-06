package uk.org.lidalia.http.client

import java.time.Duration

import uk.org.lidalia.http.core._
import uk.org.lidalia.http.core.Method.{TRACE, DELETE, HEAD, GET}
import uk.org.lidalia.lang.UnsignedByte
import uk.org.lidalia.net2.Url

import scala.collection.immutable
import scala.concurrent.Future

object MultiTargetHttpClient {

  def get(
    url: Url,
    headerFields: HeaderField*
  ): Response[immutable.Seq[UnsignedByte]] = {
    new SyncHttpClient(
      new ExpectedEntityHttpClient(
        new Apache4Client(url.baseUrl)
      )
    ).execute(Request(GET, RequestUri(Right(url.pathAndQuery)), Nil), Duration.ofSeconds(5))
  }

//  def get[T](
//    url: Url,
//    accept: Accept[T],
//    headerFields: HeaderField*
//  ): Response[T] = ???

//  def execute[T](
//                  method: Method,
//                  url: Url,
//                  accept: Accept[T],
//                  headerFields: HeaderField*): Result[T] = {
//
//    decorated.execute(
//      requestFor(method, url, headerFields.toList, accept, List(
//        Host := url.hostAndPort,
//        accept
//      ))
//    )
//  }
}
