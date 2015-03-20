package uk.org.lidalia.http.client

trait BaseHttpClient {

  type Result[T]

  def execute[T](request: DirectedRequest[T]): Result[T]
}
