package uk.org.lidalia.http.client.sync

import uk.org.lidalia.http

import scala.concurrent.{duration => scala, Future, Await}
import org.joda.time.Duration
import java.util.concurrent.TimeUnit

import http.client.async.HttpClient
import uk.org.lidalia.http.{Response, Request}

class SyncHttpClient(asyncHttpClient: HttpClient) {

  def execute(request: Request, timeout: Duration): Response = {
    val response = asyncHttpClient.execute(request)
    Await.result(response, toScalaDuration(timeout))
  }

  private def toScalaDuration(timeout: Duration): scala.FiniteDuration = {
    scala.Duration(timeout.getMillis, TimeUnit.MILLISECONDS)
  }
}
