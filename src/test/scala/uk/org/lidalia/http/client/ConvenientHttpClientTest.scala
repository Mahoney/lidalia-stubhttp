package uk.org.lidalia.http.client

import java.io.InputStream

import org.apache.commons.io.IOUtils
import org.mockito.BDDMockito.given
import org.mockito.{BDDMockito, Mockito}
import org.mockito.Mockito.mock
import org.scalatest.{PropSpec, FunSuite}
import uk.org.lidalia.http.client.EntityOnlyHttpClient.Is
import uk.org.lidalia.http.client.ExpectedEntityHttpClient.FutureResponse
import uk.org.lidalia.http.core.headerfields.Host
import uk.org.lidalia.http.core.{Response, RequestUri, Request, ResponseHeader}
import uk.org.lidalia.net2._
import uk.org.lidalia.net2.Scheme._
import uk.org.lidalia.http.core.Method.GET

import scala.concurrent.Future

class ConvenientHttpClientTest extends PropSpec {

  val decoratedClient = mock(classOf[EntityOnlyHttpClient])
  val client: ConvenientHttpClient[Is] = new ConvenientHttpClient(decoratedClient)

  property("Default has expected type") {
    val client: ConvenientHttpClient[FutureResponse] = ConvenientHttpClient()
  }

  property("Makes get request") {
    val accept: Accept[String] = new Accept[String](List()) {
      override def unmarshal(request: Request, response: ResponseHeader, entityBytes: InputStream): String = IOUtils.toString(entityBytes)
    }
    given(decoratedClient.execute(
      new DirectedRequest(
        HTTP,
        HostAndPort("localhost"),
        Request(
          GET,
          RequestUri("/blah"),
          List(
            Host(HostAndPort("localhost")),
            accept
          )),
        accept
      )
    )).willReturn("Result")

    val result: String = client.get(Url("http://localhost/blah"), accept)

    assert(result === "Result")
  }
}
