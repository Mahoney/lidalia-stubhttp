package uk.org.lidalia.http.response

import scala.language.implicitConversions
import scala.Some
import scala.annotation.meta.field

package object headerfields {
  type ?[T] = Option[T]
  type Identity = uk.org.lidalia.lang.Identity @field

  implicit def instanceToSome[T](instance: T) = Some(instance)
  implicit def someToInstance[T](some: Some[T]) = some.get

  class ToRichOption[A](val option: Option[A]) {
    def or[B >: A](default: => B): B = option.getOrElse(default)
    def ?[B](f: A => ?[B]): ?[B] = option.flatMap(f)
  }
  implicit def toRichOption[A](option: Option[A]) = new ToRichOption(option)
}
