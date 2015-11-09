package uk.org.lidalia.http.client

import java.util.concurrent.TimeUnit.MILLISECONDS

import org.joda.time.Duration
import uk.org.lidalia.http
import http.core.{Response, Request}
import uk.org.lidalia.net2.Url
import scala.concurrent.{duration => scala, Await}

object SyncHttpClient {

  def apply(
    url: Url,
    timeout: Duration = Duration.standardSeconds(5)
  ): SyncHttpClient[Response] = {
    apply(
      new ExpectedEntityHttpClient(
        new Apache4Client(url.baseUrl)
      ),
      timeout
    )
  }

  def apply[Result[_]](
    asyncHttpClient: FutureHttpClient[Result],
    timeout: Duration
  ) = {
    new SyncHttpClient(
      asyncHttpClient,
      timeout
    )
  }
}

class SyncHttpClient[Result[_]](
  asyncHttpClient: FutureHttpClient[Result],
  timeout: Duration
) extends HttpClient[Result] {

  private val scalaTimeout = scala.Duration(timeout.getMillis, MILLISECONDS)

  def execute[T](
    request: Request[T, _]
  ): Result[T] = {

    val response = asyncHttpClient.execute(request)

    Await.result(response, scalaTimeout)
  }
}
