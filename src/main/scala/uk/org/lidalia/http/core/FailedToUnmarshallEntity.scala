package uk.org.lidalia.http.core

import java.lang.System.lineSeparator

class FailedToUnmarshallEntity(cause: Throwable, val request: Request[_, _], val response: Response[String])
  extends RuntimeException(
    "Failed to unmarshal entity in response"+lineSeparator()+
    response+lineSeparator()+lineSeparator()+
    "Request:"+lineSeparator()+
    request,
    cause) {



}
