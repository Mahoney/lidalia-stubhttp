package uk.org.lidalia.http.client

import uk.org.lidalia.http.client.EntityOnlyHttpClient.Is
import uk.org.lidalia.http.core.{Request, Response}

object EntityOnlyHttpClient {
  type Is[T] = T
}

class EntityOnlyHttpClient(decorated: BaseHttpClient[Response]) extends BaseHttpClient[Is] {

  override def execute[T](request: Request[T, _]): T = {
    decorated.execute(request).entity
  }
}
