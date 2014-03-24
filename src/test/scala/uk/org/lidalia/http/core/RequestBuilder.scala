package uk.org.lidalia.http.core

import Method.GET
import uk.org.lidalia.net2.{PartialUri, Uri}
import scala.io.Source
import java.io.InputStream

object RequestBuilder {

  def request[T](
    method: Method = GET,
    uri: RequestUri = RequestUri("/mypath"),
    responseHandler: ResponseHandler[T] = new ResponseHandler[Option[Nothing]]{
      def handle(content: InputStream) = None
    }
                 ) = Request(method, uri, responseHandler)

  def get(
     uri: RequestUri = RequestUri("/mypath")
             ) = request(GET, uri)

  def get(uri: Uri) = request(GET, RequestUri(uri))
}
