package uk.org.lidalia.http.core

import uk.org.lidalia.lang.WrappedValue
import scala.collection.mutable

object Code {


  private val tempCodes: mutable.Map[Int, Code] = mutable.Map()

  val OK:                  SuccessfulCode  = register(successful( 200, Reason("OK")))

  val Found:               RedirectionCode = register(redirection(302, Reason("Found")))

  val BadRequest:          ClientErrorCode = register(clientError(400, Reason("Bad Request")))
  val MethodNotAllowed:    ClientErrorCode = register(clientError(405, Reason("Method Not Allowed")))

  val InternalServerError: ServerErrorCode = register(serverError(500, Reason("Internal Server Error")))

  val codes: Map[Int, Code] = tempCodes.toMap

  def successful( code: Int, defaultReason: ?[Reason] = None): SuccessfulCode =  new SuccessfulCode( code, defaultReason)
  def redirection(code: Int, defaultReason: ?[Reason] = None): RedirectionCode = new RedirectionCode(code, defaultReason)
  def clientError(code: Int, defaultReason: ?[Reason] = None): ClientErrorCode = new ClientErrorCode(code, defaultReason)
  def serverError(code: Int, defaultReason: ?[Reason] = None): ServerErrorCode = new ServerErrorCode(code, defaultReason)

  private def register[T <: Code](code: T): T = {
    tempCodes(code.code) = code
    code
  }

  def apply(code: Int, defaultReason: ?[Reason] = None) = new Code(code, defaultReason)

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
