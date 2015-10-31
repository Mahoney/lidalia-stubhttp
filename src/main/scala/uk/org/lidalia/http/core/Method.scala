package uk.org.lidalia.http.core

import uk.org.lidalia.http.client.{EntityUnmarshaller, EntityMarshaller}

import scala.collection.convert.Wrappers.JConcurrentMapWrapper
import java.util.concurrent.ConcurrentHashMap
import scala.collection.immutable.Set

object Method {

  private val methods = new JConcurrentMapWrapper(new ConcurrentHashMap[String, Method])

  val GET     = registerSafeMethod(            "GET",     requestMayHaveEntity = false, responseMayHaveEntity = true )
  val HEAD    = registerSafeMethod(            "HEAD",    requestMayHaveEntity = false, responseMayHaveEntity = false)

  val PUT     = registerUnsafeIdempotentMethod("PUT",     requestMayHaveEntity = true,  responseMayHaveEntity = true )
  val DELETE  = registerUnsafeIdempotentMethod("DELETE",  requestMayHaveEntity = false, responseMayHaveEntity = true )

  val POST    = registerNonIdempotentMethod(   "POST",    requestMayHaveEntity = true,  responseMayHaveEntity = true )
  val PATCH   = registerNonIdempotentMethod(   "PATCH",   requestMayHaveEntity = true,  responseMayHaveEntity = true )

  val OPTIONS = registerSafeMethod(            "OPTIONS", requestMayHaveEntity = true,  responseMayHaveEntity = true )
  val TRACE   = registerSafeMethod(            "TRACE",   requestMayHaveEntity = false, responseMayHaveEntity = true )

  def registerSafeMethod(name: String, requestMayHaveEntity: Boolean, responseMayHaveEntity: Boolean): SafeMethod = {
    register(new SafeMethod(name) with HasEntity with ResponseHasEntity)
  }

  def registerUnsafeIdempotentMethod(name: String, requestMayHaveEntity: Boolean, responseMayHaveEntity: Boolean): UnsafeIdempotentMethod = {
    register(new UnsafeIdempotentMethod(name) with HasEntity with ResponseHasEntity)
  }

  def registerNonIdempotentMethod(name: String, requestMayHaveEntity: Boolean, responseMayHaveEntity: Boolean): NonIdempotentMethod = {
    register(new NonIdempotentMethod(name) with HasEntity with ResponseHasEntity)
  }

  def register[T <: Method](method: T): T = {
    val existing: ?[Method] = methods.putIfAbsent(method.name, method)
    if (!existing.isEmpty) throw new IllegalStateException("Only one instance of a method may exist! Trying to create duplicate of "+method)
    method
  }

  def values(): Set[Method] = methods.values.to[Set]

  def apply(name: String): Method = {
    methods.get(name).or(new NonIdempotentMethod(name) with HasEntity with ResponseHasEntity)
  }
}

sealed abstract class Method(
  val name: String
) {

  val isSafe: Boolean
  final val isUnsafe: Boolean = !isSafe

  val isIdempotent: Boolean
  final val isNotIdempotent: Boolean = !isIdempotent

  val requestMayHaveEntity: Boolean
  final val requestMayNotHaveEntity: Boolean = !requestMayHaveEntity

  val responseMayHaveEntity: Boolean
  final val responseMayNotHaveEntity: Boolean = !responseMayHaveEntity

  override val toString = name
}

sealed trait IdempotentMethod extends Method {
  final override val isIdempotent = true
}

sealed trait UnsafeMethod extends Method {
  final override val isSafe = false
}

sealed trait HasEntity extends Method {
  final override val requestMayHaveEntity = true
}

sealed trait HasNoEntity extends Method {
  final override val requestMayHaveEntity = false
}

sealed trait ResponseHasEntity extends Method {
  final override val responseMayHaveEntity = true
}

sealed trait ResponseHasNoEntity extends Method {
  final override val responseMayHaveEntity = false
}

abstract class NonIdempotentMethod private[core](
  name: String
) extends Method(
  name
) with UnsafeMethod {
  override val isIdempotent = false
}

abstract class SafeMethod private[core](
  name: String
) extends Method(
  name
) with IdempotentMethod {
  override val isSafe = true
}

abstract class UnsafeIdempotentMethod private[core](
  name: String
) extends Method(
  name
) with UnsafeMethod with IdempotentMethod
