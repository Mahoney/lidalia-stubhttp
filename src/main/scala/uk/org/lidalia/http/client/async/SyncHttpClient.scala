package uk.org.lidalia.http.client.async

import java.util.concurrent.TimeUnit

import uk.org.lidalia.http.client.{DirectedRequest, BaseHttpClient}
import uk.org.lidalia.http.core.Request

import scala.concurrent.{Future, Await}
import scala.concurrent.duration.Duration

class SyncHttpClient[Result[_]](decorated: FutureHttpClient[Result], timeout: Duration = Duration(5, TimeUnit.SECONDS)) extends BaseHttpClient[Result] {

  override def execute[T](request: Request[T, _]): Result[T] = {
    Await.result(
      decorated.execute(request),
      timeout
    )
  }
}
