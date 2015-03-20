package uk.org.lidalia.http.client

import uk.org.lidalia.http.core.Response

import scala.concurrent.Future

trait TargetedHttpClient {

  def execute[T](request: TargetedRequest[T]): Future[Response[Either[String, T]]]
}
