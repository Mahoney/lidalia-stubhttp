package uk.org.lidalia.http.client

import uk.org.lidalia.http.core.{Request, Response}
import uk.org.lidalia.net2.{HostAndPort, Scheme, Socket$}

import scala.concurrent.Future

abstract class TargetedHttpClient(scheme: Scheme, hostAndPort: HostAndPort) {

  def execute[T](request: Request[T, _]): Future[Response[Either[String, T]]]
}
