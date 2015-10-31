package uk.org.lidalia.http.client

import java.lang.System.lineSeparator

import uk.org.lidalia.http.core.{Request, Response}

object ServerError {
  def apply(response: Response[Either[String, _]], request: Request[Either[String, _], _]) = new ServerError(response, request)
}

class ServerError private (response: Response[Either[String, _]], request: Request[Either[String, _], _]) extends HttpResponseCodeError(response, request)

object ClientError {
  def apply(response: Response[Either[String, _]], request: Request[Either[String, _], _]) = new ClientError(response, request)
}

class ClientError private (response: Response[Either[String, _]], request: Request[Either[String, _], _]) extends HttpResponseCodeError(response, request)

class HttpResponseCodeError(response: Response[Either[String, _]], request: Request[Either[String, _], _]) extends HttpException(response, request)

object HttpException {
  def resolve(response: Response[Either[String, _]]): String = {
    val entity = response.entity
    val stringEntity = entity.left.getOrElse(entity.right.toString)
    Response(response.header, stringEntity).toString
  }
}

class HttpException(val response: Response[Either[String, _]], val request: Request) extends Exception(HttpException.resolve(response)+lineSeparator()+"for"+lineSeparator()+request.toString)

object InfiniteRedirectException {
  def apply(response: Response[Either[String, _]], request: Request) = new InfiniteRedirectException(response, request)
}

class InfiniteRedirectException private (response: Response[Either[String, _]], request: Request) extends HttpException(response, request)