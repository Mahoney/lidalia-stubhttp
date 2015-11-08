package uk.org.lidalia.lang

trait ResourceFactory[R] {

  def withA[T](work: (R) => T): T

  def withA[T](work: () => T): T = {
    withA((ignore) => work())
  }
}
