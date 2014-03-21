package uk.org.lidalia.http.client.sync

import uk.org.lidalia.http

import scala.concurrent.{duration => scala, Await}
import org.joda.time.Duration
import java.util.concurrent.TimeUnit

import http.client.async.HttpClient
import http.core.{Response, Request}

class SyncHttpClient(asyncHttpClient: HttpClient) {

  def execute[T](request: Request[T], timeout: Duration): Response[T] = {
    val response = asyncHttpClient.execute(request)
    Await.result(response, toScalaDuration(timeout))
  }

  private def toScalaDuration(timeout: Duration): scala.FiniteDuration = {
    scala.Duration(timeout.getMillis, TimeUnit.MILLISECONDS)
  }
}
