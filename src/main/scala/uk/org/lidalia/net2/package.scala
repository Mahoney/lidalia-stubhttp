package uk.org.lidalia

import scala.language.implicitConversions

package object net2 {
    type ?[T] = Option[T]

    implicit def instanceToSome[T](instance: T) = Some(instance)
    implicit def someToInstance[T](some: Some[T]) = some.get
}
