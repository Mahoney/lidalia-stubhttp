package uk.org.lidalia.http.core

import java.util.concurrent.ConcurrentHashMap
import uk.org.lidalia.lang.WrappedValue
import scala.collection.immutable.SortedSet
import scala.collection.convert.Wrappers.JConcurrentMapWrapper

object Code {

  private val codes = new JConcurrentMapWrapper(new ConcurrentHashMap[Int, Code])

  val OK:                  SuccessfulCode  = successful( 200, Reason("OK"))

  val Found:               RedirectionCode = redirection(302, Reason("Found"))

  val BadRequest:          ClientErrorCode = clientError(400, Reason("Bad Request"))
  val MethodNotAllowed:    ClientErrorCode = clientError(405, Reason("Method Not Allowed"))

  val InternalServerError: ServerErrorCode = serverError(500, Reason("Internal Server Error"))

  def successful( code: Int, defaultReason: ?[Reason] = None): SuccessfulCode =  register(new SuccessfulCode( code, defaultReason))
  def redirection(code: Int, defaultReason: ?[Reason] = None): RedirectionCode = register(new RedirectionCode(code, defaultReason))
  def clientError(code: Int, defaultReason: ?[Reason] = None): ClientErrorCode = register(new ClientErrorCode(code, defaultReason))
  def serverError(code: Int, defaultReason: ?[Reason] = None): ServerErrorCode = register(new ServerErrorCode(code, defaultReason))

  def apply(code: Int, defaultReason: ?[Reason] = None) = {
    val value = new Code(code, defaultReason)
    codes.putIfAbsent(code, value) or value
  }

  private def register[T <: Code](code: T): T = {
    val existing = codes.putIfAbsent(code.code, code)
    if (!existing.isEmpty) throw new IllegalStateException("Only one instance of a code may exist! Trying to create duplicate of "+code)
    code
  }

  def values(): SortedSet[Code] = codes.values.to[SortedSet]

}

class Code protected(val code: Int, val defaultReason: ?[Reason]) extends WrappedValue(code) with Ordered[Code] {
  require(code >= 100 && code < 1000, s"Code must be between 100 and 999, was $code")
  def compare(that: Code) = code.compare(that.code)

  def requiresRedirect: Boolean = (code == 302)

  protected def validateCode(candidate: Int, lowerBound: Int) {
    val upperBound = lowerBound + 99
    require(candidate >= lowerBound && code <= upperBound, s"Code must be between $lowerBound & $upperBound, was $candidate")
  }
}

class InformationalCode private[core](code: Int, defaultReason: ?[Reason]) extends Code(code, defaultReason) {
  validateCode(code, 100)
}

class SuccessfulCode private[core](code: Int, defaultReason: ?[Reason]) extends Code(code, defaultReason){
  validateCode(code, 200)
}

class RedirectionCode private[core](code: Int, defaultReason: ?[Reason]) extends Code(code, defaultReason){
  validateCode(code, 300)
}

class ClientErrorCode private[core](code: Int, defaultReason: ?[Reason]) extends Code(code, defaultReason){
  validateCode(code, 400)
}

class ServerErrorCode private[core](code: Int, defaultReason: ?[Reason]) extends Code(code, defaultReason){
  validateCode(code, 500)
}
