package uk.org.lidalia.http.core

object Method {

  object GET     extends SafeMethod("GET") {                final val requestMayHaveEntity = false; final val responseMayHaveEntity = true  }
  object HEAD    extends SafeMethod("HEAD") {               final val requestMayHaveEntity = false; final val responseMayHaveEntity = false }

  object PUT     extends UnsafeIdempotentMethod("PUT") {    final val requestMayHaveEntity = true;  final val responseMayHaveEntity = true  }
  object DELETE  extends UnsafeIdempotentMethod("DELETE") { final val requestMayHaveEntity = false; final val responseMayHaveEntity = true  }

  object POST    extends NonIdempotentMethod("POST") {      final val requestMayHaveEntity = true;  final val responseMayHaveEntity = true  }
  object PATCH   extends NonIdempotentMethod("PATCH") {     final val requestMayHaveEntity = true;  final val responseMayHaveEntity = true  }

  object OPTIONS extends SafeMethod("OPTIONS") {            final val requestMayHaveEntity = true;  final val responseMayHaveEntity = true  }
  object TRACE   extends SafeMethod("TRACE") {              final val requestMayHaveEntity = false; final val responseMayHaveEntity = true  }
}

sealed abstract class Method(val name: String) {

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

abstract class NonIdempotentMethod(name: String) extends Method(name) with UnsafeMethod {
  final override val isIdempotent = false
}

abstract class SafeMethod(name: String) extends Method(name) with IdempotentMethod {
  final override val isSafe = true
}

abstract class UnsafeIdempotentMethod(name: String) extends Method(name) with UnsafeMethod with IdempotentMethod
