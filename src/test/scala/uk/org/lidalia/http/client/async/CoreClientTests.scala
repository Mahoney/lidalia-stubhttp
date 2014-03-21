package uk.org.lidalia.http.client.async

import com.github.tomakehurst.wiremock
import org.scalatest
import uk.org.lidalia
import lidalia.http

import scalatest.{PropSpec, Suite, AbstractSuite}
import scalatest.prop.TableDrivenPropertyChecks

import wiremock.WireMockServer
import wiremock.client.{RequestPatternBuilder, MappingBuilder, WireMock}
import wiremock.junit.Stubbing
import WireMock.{get, urlEqualTo, aResponse}
import lidalia.net2.{Port, IpV4Address, PartialUri}
import uk.org.lidalia.http.core.{ResponseHandler, Code, Response, Request}
import http.core.Method.GET
import scala.concurrent.{Future, Await}
import scala.concurrent.duration.Duration
import java.util.concurrent.TimeUnit
import java.io.InputStream
import scala.io.Source
import org.apache.commons.io.IOUtils

class CoreClientTests extends PropSpec with TableDrivenPropertyChecks with WireMockTest {

  val handler: ResponseHandler[String] with Object = new ResponseHandler[String]{
    def handle(content: InputStream) = IOUtils.toString(content)
  }

  property("Returns response from server") {
    givenThat(
      get(urlEqualTo("/foo")).willReturn(
        aResponse()
      .withStatus(200)
      .withBody("Some text")
      .withHeader("Date", "Sun, 06 Nov 1994 08:49:37 GMT")
      .withHeader("Content-Type", "text/plain")))

    val coreClient = new CoreClient
    val request: Request[String] = Request(GET, PartialUri("/foo"), handler)
    val futResponse: Future[Response[String]] = coreClient.execute(request, IpV4Address("127.0.0.1"), Port(wireMockServer.port()))
    val response = Await.result(futResponse, Duration(1, TimeUnit.SECONDS))


    assert(response.code == Code(200))
  }

}

trait WireMockTest extends AbstractSuite with Stubbing {
  self : Suite =>

  val wireMockServer = new WireMockServer()
  lazy val wireMock = new WireMock("localhost", wireMockServer.port())

  override abstract def withFixture(test : NoArgTest) {

    wireMockServer.start()
    WireMock.configureFor("localhost", wireMockServer.port())

    try {
      WireMockTest.super.withFixture(test)
    } finally {
      wireMockServer.stop()
    }
  }

  override def givenThat(mappingBuilder: MappingBuilder) { wireMock.register(mappingBuilder) }

  override def stubFor(mappingBuilder: MappingBuilder) {
    givenThat(mappingBuilder)
  }

  override def verify(requestPatternBuilder: RequestPatternBuilder) {
    wireMock.verifyThat(requestPatternBuilder)
  }

  override def verify(count: Int, requestPatternBuilder: RequestPatternBuilder) {
    wireMock.verifyThat(count, requestPatternBuilder)
  }

  override def findAll(requestPatternBuilder: RequestPatternBuilder ) = wireMock.find(requestPatternBuilder)

  override def setGlobalFixedDelay(milliseconds: Int) {
    wireMock.setGlobalFixedDelayVariable(milliseconds)
  }

  override def addRequestProcessingDelay(milliseconds: Int) {
    wireMock.addDelayBeforeProcessingRequests(milliseconds)
  }

}
