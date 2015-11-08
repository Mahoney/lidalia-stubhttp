package uk.org.lidalia.http.client

import java.time.Duration

import uk.org.lidalia.http.core.Method.GET
import uk.org.lidalia.http.core._
import uk.org.lidalia.lang.ByteSeq
import uk.org.lidalia.net2.Url

object StandardSyncHttpClient {

  val delegate = new MultiTargetHttpClient((url) => new SyncHttpClient(
    new ExpectedEntityHttpClient(
      new Apache4Client(url.baseUrl)
    ),
    Duration.ofSeconds(5)
  ))

  def get(
    url: Url
  ): Response[ByteSeq] = {
    execute(GET, url)
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
  def execute(method: Method, url: Url): Response[ByteSeq] = {
    delegate.execute(url, Request(method, RequestUri(Right(url.pathAndQuery)), Nil))
  }
}
