package uk.org.lidalia.http.client

import uk.org.lidalia.http.core.Request
import uk.org.lidalia.net2.Url

class MultiTargetHttpClient[Result[_]](
  clientBuilder: (Url) => HttpClient[Result]
) {

  def execute[T](
    url: Url,
    request: Request[T, _]
  ): Result[T] = {
    clientBuilder(url).execute(request)
  }
}
