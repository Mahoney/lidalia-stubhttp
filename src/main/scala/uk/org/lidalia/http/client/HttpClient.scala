package uk.org.lidalia.http.client

import uk.org.lidalia.http.core.Request

trait HttpClient[+Result[_]] {

  def execute[T](request: Request[T, _]): Result[T]
}
