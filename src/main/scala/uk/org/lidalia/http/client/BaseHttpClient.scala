package uk.org.lidalia.http.client

import uk.org.lidalia.http.core.{Request, Http}

trait BaseHttpClient[Result[_]] extends Http {

  def execute[T](request: Request[T, _]): Result[T]
}
