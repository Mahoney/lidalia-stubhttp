package uk.org.lidalia.http.core

import Code.{BadRequest, InternalServerError, MethodNotAllowed}

sealed abstract class HttpException(message: String = null, cause: Throwable = null, val code: Code)
  extends Exception(message, cause)

class ClientException(message: String = null, cause: Throwable = null, code: ClientErrorCode = BadRequest)
  extends HttpException(message, cause, code)

class ServerException(message: String = null, cause: Throwable = null, code: ServerErrorCode = InternalServerError)
  extends HttpException(message, cause, code)

class UnknownMethodException(methodName: String)
  extends ClientException(message = s"Method not found: $methodName; consider registering it.", code = MethodNotAllowed)