package uk.org.lidalia.http.client

import org.scalatest.PropSpec

class ConvenientHttpClientTests extends PropSpec {

//  val decoratedClient = mock(classOf[BaseHttpClient[Is]])
//  val client = new ConvenientHttpClient(decoratedClient)
//
//  property("Default has expected type") {
//    val client = ConvenientHttpClient()
//  }
//
//  property("Makes get request") {
//    val accept: Accept[String] = new Accept[String](List()) {
//      override def unmarshal(request: Request, response: ResponseHeader, entityBytes: InputStream): String = IOUtils.toString(entityBytes)
//    }
//    given(decoratedClient.execute(
//      DirectedRequest(
//        HTTP,
//        HostAndPort("localhost"),
//        Request(
//          GET,
//          RequestUri("/blah"),
//          List(
//            Host := HostAndPort("localhost"),
//            accept,
//            Etag := "my-custom-etag"
//          )
//        ),
//        accept
//      )
//    )).willReturn("Result")
//
//    val result: String = client.get(
//      Url("http://localhost/blah"),
//      accept,
//      Etag := "my-custom-etag"
//    )
//
//    assert(result === "Result")
//  }
//
//  property("Makes head request") {
//    val decoratedClient = mock(classOf[BaseHttpClient[Response]])
//    val client = new ConvenientHttpClient(decoratedClient)
//    val accept: Accept[String] = new Accept[String](List()) {
//      override def unmarshal(request: Request, response: ResponseHeader, entityBytes: InputStream): String = IOUtils.toString(entityBytes)
//    }
//
//    given(decoratedClient.execute(
//      DirectedRequest(
//        HTTP,
//        HostAndPort("localhost"),
//        Request(
//          HEAD,
//          RequestUri("/blah"),
//          List(
//            Host := HostAndPort("localhost"),
//            accept,
//            Etag := "my-custom-etag"
//          )
//        ),
//        NoopEntityUnmarshaller
//      )
//    )).willReturn(Response(OK))
//
//    val result = client.head(
//      Url("http://localhost/blah"),
//      accept,
//      Etag := "my-custom-etag"
//    )
//
//    assert(result === Response(OK))
//  }
}
