package uk.org.lidalia.http.client

import uk.org.lidalia.http.core.Method._
import uk.org.lidalia.http.core.headerfields.Host
import uk.org.lidalia.http.core.{RequestUri, Request, Response}
import uk.org.lidalia.net2.{Url, Uri}

import scala.concurrent.Future

class ConvenientHttpClient(val decorated: BaseHttpClient) extends BaseHttpClient {

  override type Result[T] = decorated.Result[T]

  def get[T](
              url: Url,
              accept: Accept[T]): Result[T] =

    decorated.execute(
      new DirectedRequest(
        url.scheme,
        url.hostAndPort,
        Request(
          GET,
          RequestUri(url.pathAndQuery),
          List(
            Host(url.hostAndPort),
            accept
          )
        ),
        accept
      )
    )

  override def execute[T](request: DirectedRequest[T]): Result[T] = decorated.execute(request)
}
