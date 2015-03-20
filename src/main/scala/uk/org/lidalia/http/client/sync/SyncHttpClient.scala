package uk.org.lidalia.http.client

import uk.org.lidalia.http

import scala.concurrent.{duration => scala, Await}
import org.joda.time.Duration
import java.util.concurrent.TimeUnit

import http.core.Response

class SyncHttpClient(asyncHttpClient: HttpClient) {

  def execute[T](request: DirectedRequest[T], timeout: Duration): Response[Either[String, T]] = {
    val response = asyncHttpClient.execute(request)
    Await.result(response, toScalaDuration(timeout))
  }

  private def toScalaDuration(timeout: Duration): scala.FiniteDuration = {
    scala.Duration(timeout.getMillis, TimeUnit.MILLISECONDS)
  }
}
