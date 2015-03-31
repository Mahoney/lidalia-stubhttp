package uk.org.lidalia.http.client

import uk.org.lidalia.http.core.Response

object ServerError {
  def apply(response: Response[Either[String, _]]) = new ServerError(response)
}

class ServerError private (val response: Response[Either[String, _]]) extends HttpResponseCodeError {

}

object ClientError {
  def apply(response: Response[Either[String, _]]) = new ClientError(response)
}

class ClientError private (val response: Response[Either[String, _]]) extends HttpResponseCodeError {

}

class HttpResponseCodeError extends HttpException {

}

class HttpException extends Exception {

}