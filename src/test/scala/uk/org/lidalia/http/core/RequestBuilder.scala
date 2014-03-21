package uk.org.lidalia.http.core

import Method.GET
import uk.org.lidalia.net2.{PartialUri, Uri}
import scala.io.Source
import java.io.InputStream

object RequestBuilder {

  def request[T](
    method: Method = GET,
    uri: Uri = Uri("http://www.example.com/mypath"),
    responseHandler: ResponseHandler[T] = new ResponseHandler[Option[Nothing]]{
      def handle(content: InputStream) = None
    }
                 ) = Request(method, uri, responseHandler)

  def get(
     uri: Uri = Uri("http://www.example.com/mypath")
             ) = request(GET, uri)
}
