package uk.org.lidalia.http.client.async

import uk.org.lidalia.http.client.{DirectedRequest, BaseHttpClient}

import scala.concurrent.Future

trait FutureHttpClient[Result[_]] extends BaseHttpClient[({type FutureResult[T]=Future[Result[T]]})#FutureResult] {

  def execute[T](request: DirectedRequest[T]): Future[Result[T]]

}
