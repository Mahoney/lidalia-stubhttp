package uk.org.lidalia.http.client

import java.util.concurrent.TimeUnit.MILLISECONDS

import org.joda.time.Duration
import uk.org.lidalia.http
import http.core.Request
import scala.concurrent.{duration => scala, Await}

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
