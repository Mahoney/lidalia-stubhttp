package uk.org.lidalia.http.client.sync

import uk.org.lidalia.http

import scala.concurrent.{duration => scala, Await}
import org.joda.time.Duration
import java.util.concurrent.TimeUnit

import http.client.async.HttpClient
import http.response.Response
import uk.org.lidalia.http.request.Request

class SyncHttpClient(asyncHttpClient: HttpClient) {

  def execute(request: Request, timeout: Duration): Response = {
    Await.result(asyncHttpClient.execute(request), toScalaDuration(timeout))
  }

  def toScalaDuration(timeout: Duration): scala.FiniteDuration = {
    scala.Duration(timeout.getMillis, TimeUnit.MILLISECONDS)
  }
}
