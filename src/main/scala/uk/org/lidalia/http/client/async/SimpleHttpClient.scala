package uk.org.lidalia.http.client

import scala.concurrent.Future
import uk.org.lidalia.http.core.Response

trait SimpleHttpClient extends BaseHttpClient {

  type Result[A] = Future[Response[A]]

}

//trait BaseHttpClient {
//  type T[C]
//  def execute[A](request: DirectedRequest[A]): Future[Response[T[A]]]
//}