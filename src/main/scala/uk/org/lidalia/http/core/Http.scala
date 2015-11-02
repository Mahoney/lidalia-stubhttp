package uk.org.lidalia.http.core

trait Http {

  def execute[A, C](request: Request[A, C]): Response[A]

}
