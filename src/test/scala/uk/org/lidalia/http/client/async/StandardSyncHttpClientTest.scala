package uk.org.lidalia.http.client.async

import java.nio.charset.StandardCharsets.UTF_8

import com.github.tomakehurst.wiremock.client.WireMock.{aResponse, get, urlEqualTo}
import org.scalatest.FunSuite
import uk.org.lidalia.http.client.sync.StandardSyncHttpClient
import uk.org.lidalia.lang.UnsignedByte
import uk.org.lidalia.net2.Url
import uk.org.lidalia.slf4jext.Level
import uk.org.lidalia.slf4jtest.TestLoggerFactory
import uk.org.lidalia.support.WireMockTest

class StandardSyncHttpClientTest extends FunSuite with WireMockTest {

  TestLoggerFactory.getInstance().setPrintLevel(Level.INFO)

  testWithDeps("can get bytes") { stub =>
    stub.givenThat(
      get(urlEqualTo("/foo")).willReturn(
        aResponse()
          .withStatus(200)
          .withHeader("Date", "Sun, 06 Nov 1994 08:49:37 GMT")
          .withHeader("Content-Type", "text/plain")
          .withBody("Some text")
      )
    )

    def response = StandardSyncHttpClient.get(Url(s"http://localhost:${stub.port()}/foo"))

    assert(response.entity === "Some text".getBytes(UTF_8).map(UnsignedByte(_)).toSeq)
  }

}
