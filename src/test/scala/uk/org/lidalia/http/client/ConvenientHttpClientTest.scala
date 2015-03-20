package uk.org.lidalia.http.client

import java.io.InputStream

import org.mockito.Mockito
import org.mockito.Mockito.mock
import org.scalatest.{PropSpec, FunSuite}
import uk.org.lidalia.http.core.{Request, ResponseHeader}
import uk.org.lidalia.net2.Url

class ConvenientHttpClientTest extends PropSpec {

  val client = mock(classOf[SimplestHttpClient])
  val convenientClient = new ConvenientHttpClient(client)

  property("Makes get request") {
    convenientClient.get(Url("http://localhost:8080/blah"), new Accept[String](List()) {
      override def unmarshal(request: Request, response: ResponseHeader, entityBytes: InputStream): String = "Hello World"
    })
  }
}
