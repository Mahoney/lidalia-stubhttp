package uk.org.lidalia.http.client

trait BaseHttpClient[Result[_]] {

  def execute[T](request: DirectedRequest[T]): Result[T]
}
