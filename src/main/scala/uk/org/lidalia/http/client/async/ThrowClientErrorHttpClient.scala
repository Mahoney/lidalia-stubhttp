package uk.org.lidalia.http.client

import uk.org.lidalia.http.core.Response

import scala.concurrent.ExecutionContext.Implicits.global

import scala.concurrent.Future

class ThrowClientErrorHttpClient(decorated: HttpClient) extends HttpClient {

   def execute[A](request: DirectedRequest[A]): Future[Response[Either[String, A]]] = {
     val futureResponse = decorated.execute(request)
     futureResponse.map(response => {
       if (response.isClientError) throw ClientError(response, request.request)
       else response
     })
   }
 }
