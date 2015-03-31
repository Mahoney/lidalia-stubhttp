package uk.org.lidalia.http.client

import java.util.concurrent.TimeUnit

import uk.org.lidalia.http.client.EntityOnlyHttpClient.Is
import uk.org.lidalia.http.core.Response

import scala.concurrent.Await
import scala.concurrent.duration.Duration

object EntityOnlyHttpClient {
  type Is[T] = T
}

class EntityOnlyHttpClient(decorated: ExpectedEntityHttpClient) extends BaseHttpClient[Is] {

  override def execute[T](request: DirectedRequest[T]): T = {
    val response: Response[T] = Await.result(
      decorated.execute(request),
      Duration(5, TimeUnit.SECONDS)
    )
    response.entity
  }
}
