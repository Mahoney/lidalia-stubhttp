package uk.org.lidalia.http.core

import java.lang.System.lineSeparator

import uk.org.lidalia.http.client.TargetedRequest

class FailedToUnmarshallEntity(cause: Throwable, val request: TargetedRequest[_], val response: Response[String])
  extends RuntimeException(
    "Failed to unmarshal entity in response"+lineSeparator()+
    response+lineSeparator()+lineSeparator()+
    "Request:"+lineSeparator()+
    request,
    cause) {



}
