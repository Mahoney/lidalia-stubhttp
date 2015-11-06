package uk.org.lidalia.http.client

import java.time.Duration
import java.util.concurrent.TimeUnit.MILLISECONDS

import uk.org.lidalia.http
import http.core.Request
import uk.org.lidalia.http.client.async.FutureHttpClient
import scala.concurrent.{duration => scala, Await}

class SyncHttpClient[Result[_]](
  asyncHttpClient: FutureHttpClient[Result],
  timeout: Duration
) extends BaseHttpClient[Result] {

  private val scalaTimeout = scala.Duration(timeout.toMillis, MILLISECONDS)

  def execute[T](
    request: Request[T, _]
  ): Result[T] = {

    val response = asyncHttpClient.execute(request)

    Await.result(response, scalaTimeout)
  }
}
