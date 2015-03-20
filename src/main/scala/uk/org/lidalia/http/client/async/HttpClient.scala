package uk.org.lidalia.http.client

import scala.concurrent.Future
import uk.org.lidalia.http.core.Response

trait HttpClient extends BaseHttpClient {

  type Result[A] = Future[Response[Either[String, A]]]

}
