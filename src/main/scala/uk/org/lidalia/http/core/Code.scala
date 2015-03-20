package uk.org.lidalia.http.core

import uk.org.lidalia.lang.WrappedValue
import scala.collection.mutable

object Code {


  val OK:                  SuccessfulCode  = successful( 200, Reason("OK"))

  val Found:               RedirectionCode = redirection(302, Reason("Found"))

  val BadRequest:          ClientErrorCode = clientError(400, Reason("Bad Request"))
  val MethodNotAllowed:    ClientErrorCode = clientError(405, Reason("Method Not Allowed"))

  val InternalServerError: ServerErrorCode = serverError(500, Reason("Internal Server Error"))

  def successful( code: Int, defaultReason: ?[Reason] = None): SuccessfulCode =  new SuccessfulCode( code, defaultReason)
  def redirection(code: Int, defaultReason: ?[Reason] = None): RedirectionCode = new RedirectionCode(code, defaultReason)
  def clientError(code: Int, defaultReason: ?[Reason] = None): ClientErrorCode = new ClientErrorCode(code, defaultReason)
  def serverError(code: Int, defaultReason: ?[Reason] = None): ServerErrorCode = new ServerErrorCode(code, defaultReason)

  def apply(code: Int, defaultReason: ?[Reason] = None) = new Code(code, defaultReason)

}

class Code protected(val code: Int, val defaultReason: ?[Reason]) extends WrappedValue(code) with Ordered[Code] {

  require(code >= 100 && code < 1000, s"Code must be between 100 and 999, was $code")
  def compare(that: Code) = code.compare(that.code)

  def requiresRedirect: Boolean = List(302, 307).contains(code)

  protected def validateCode(candidate: Int, lowerBound: Int) {
    val upperBound = lowerBound + 99
    require(candidate >= lowerBound && code <= upperBound, s"Code must be between $lowerBound & $upperBound, was $candidate")
  }

  def isNotError: Boolean = code < 400
  def isInformational: Boolean = code >= 100 && code < 200
  def isSuccessful: Boolean = code >= 200 && code < 300
  def isRedirection: Boolean = code >= 300 && code < 400
  def isClientError: Boolean = code >= 400 && code < 500
  def isServerError: Boolean = code >= 500 && code < 600
  def isError: Boolean = isClientError || isServerError
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
