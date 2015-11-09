package uk.org.lidalia.http.client

import uk.org.lidalia.http.core.{Response, Request}
import uk.org.lidalia.net2.Url

object MultiTargetHttpClient {

  def apply[Result[_]](
    clientBuilder: (Url) => HttpClient[Result] = {(url: Url) => ExpectedEntityHttpClient(url)}
  ) = {
    new MultiTargetHttpClient(clientBuilder)
  }
}

class MultiTargetHttpClient[Result[_]] private (
  clientBuilder: (Url) => HttpClient[Result]
) {

  def execute[T](
    baseUrl: Url,
    request: Request[T, _]
  ): Result[T] = {
    clientBuilder(baseUrl).execute(request)
  }
}
