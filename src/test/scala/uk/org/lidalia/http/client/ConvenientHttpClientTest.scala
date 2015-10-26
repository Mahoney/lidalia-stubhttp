package uk.org.lidalia.http.client

import java.io.InputStream

import org.apache.commons.io.IOUtils
import org.mockito.BDDMockito.given
import org.mockito.Mockito.mock
import org.scalatest.PropSpec
import uk.org.lidalia.http.client.EntityOnlyHttpClient.Is
import uk.org.lidalia.http.core.Code.OK
import uk.org.lidalia.http.core.Method.{GET, HEAD}
import uk.org.lidalia.http.core.{Response, RequestUri, ResponseHeader, Request}
import uk.org.lidalia.http.core.headerfields.{Etag, Host}
import uk.org.lidalia.net2.Scheme.HTTP
import uk.org.lidalia.net2.{Url, HostAndPort}

class ConvenientHttpClientTest extends PropSpec {

  val decoratedClient = mock(classOf[BaseHttpClient[Is]])
  val client = new ConvenientHttpClient(decoratedClient)

  property("Default has expected type") {
    val client = ConvenientHttpClient()
  }

  property("Makes get request") {
    val accept: Accept[String] = new Accept[String](List()) {
      override def unmarshal(request: Request, response: ResponseHeader, entityBytes: InputStream): String = IOUtils.toString(entityBytes)
    }
    given(decoratedClient.execute(
      DirectedRequest(
        HTTP,
        HostAndPort("localhost"),
        Request(
          GET,
          RequestUri("/blah"),
          List(
            Host := HostAndPort("localhost"),
            accept,
            Etag := "my-custom-etag"
          )
        ),
        accept
      )
    )).willReturn("Result")

    val result: String = client.get(
      Url("http://localhost/blah"),
      accept,
      Etag := "my-custom-etag"
    )

    assert(result === "Result")
  }

  property("Makes head request") {
    val decoratedClient = mock(classOf[BaseHttpClient[Response]])
    val client = new ConvenientHttpClient(decoratedClient)
    val accept: Accept[String] = new Accept[String](List()) {
      override def unmarshal(request: Request, response: ResponseHeader, entityBytes: InputStream): String = IOUtils.toString(entityBytes)
    }

    given(decoratedClient.execute(
      DirectedRequest(
        HTTP,
        HostAndPort("localhost"),
        Request(
          HEAD,
          RequestUri("/blah"),
          List(
            Host := HostAndPort("localhost"),
            accept,
            Etag := "my-custom-etag"
          )
        ),
        NoopEntityUnmarshaller
      )
    )).willReturn(Response(OK))

    val result = client.head(
      Url("http://localhost/blah"),
      accept,
      Etag := "my-custom-etag"
    )

    assert(result === Response(OK))
  }
}
