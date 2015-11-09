package uk.org.lidalia.http.client

import org.joda.time.Duration
import uk.org.lidalia.http.core.Method._
import uk.org.lidalia.http.core.{Response, RequestUri, Request, Method}
import uk.org.lidalia.lang.ByteSeq
import uk.org.lidalia.net2.Url

object ConvenientMultiTargetHttpClient {

  def apply(): ConvenientMultiTargetHttpClient[Response] = {
    apply(
      MultiTargetHttpClient((url) => new SyncHttpClient(
        new ExpectedEntityHttpClient(
          new Apache4Client(url.baseUrl)
        ),
        Duration.standardSeconds(5)
      ))
    )
  }

  def apply[Result[_]](
    delegate: MultiTargetHttpClient[Result]
  ) = {
    new ConvenientMultiTargetHttpClient(delegate)
  }
}

class ConvenientMultiTargetHttpClient[Result[_]] private (
  delegate: MultiTargetHttpClient[Result]
) {

  def get(
    url: Url
  ): Result[ByteSeq] = {
    execute(GET, url)
  }

  def execute(method: Method, url: Url): Result[ByteSeq] = {
    delegate.execute(url, Request(method, RequestUri(Right(url.pathAndQuery)), Nil))
  }
}
