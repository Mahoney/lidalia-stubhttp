package uk.org.lidalia.http.client.async

import java.util.concurrent.TimeUnit

import uk.org.lidalia.http.client.{DirectedRequest, BaseHttpClient}

import scala.concurrent.{Future, Await}
import scala.concurrent.duration.Duration

class SyncHttpClient[Result[_]](decorated: FutureHttpClient[Result], timeout: Duration = Duration(5, TimeUnit.SECONDS)) extends BaseHttpClient[Result] {

  override def execute[T](request: DirectedRequest[T]): Result[T] = {
    Await.result(
      decorated.execute(request),
      timeout
    )
  }
}
