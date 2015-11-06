package uk.org.lidalia.http.client

import java.time.Duration

import uk.org.lidalia.http
import http.core.Request
import uk.org.lidalia.http.client.ExpectedEntityHttpClient.FutureResponse
import scala.concurrent.{duration => scala, Await}
import java.util.concurrent.TimeUnit

import http.core.Response

class SyncHttpClient(asyncHttpClient: BaseHttpClient[FutureResponse]) {

  def execute[T](request: Request[T, _], timeout: Duration): Response[T] = {
    val response = asyncHttpClient.execute(request)
    Await.result(response, toScalaDuration(timeout))
  }

  private def toScalaDuration(timeout: Duration): scala.FiniteDuration = {
    scala.Duration(timeout.toMillis, TimeUnit.MILLISECONDS)
  }
}
