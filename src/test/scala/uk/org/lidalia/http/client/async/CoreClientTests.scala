package uk.org.lidalia.http.client

import com.github.tomakehurst.wiremock
import org.apache.http.impl.client.HttpClientBuilder
import org.scalatest
import uk.org.lidalia
import lidalia.http

import org.scalatest.{Args, PropSpec, Suite, Status}
import scalatest.prop.TableDrivenPropertyChecks
import uk.org.lidalia.net2.Scheme.HTTP

import wiremock.WireMockServer
import wiremock.client.{RequestPatternBuilder, MappingBuilder, WireMock}
import wiremock.junit.Stubbing
import WireMock.{get, urlEqualTo, aResponse}

import uk.org.lidalia.net2.{HostAndPort, Scheme, Target}
import http.core.Method.GET
import uk.org.lidalia.http.core._

import org.apache.commons.io.IOUtils

import scala.concurrent.Await
import scala.concurrent.duration.Duration
import java.util.concurrent.{TimeoutException, TimeUnit}
import java.io.InputStream
import org.joda.time.{DateTimeZone, DateTime}

class CoreClientTests extends PropSpec with TableDrivenPropertyChecks with WireMockTest {

  val apacheClient = HttpClientBuilder.create()
    .setMaxConnPerRoute(Integer.MAX_VALUE)
    .setMaxConnTotal(Integer.MAX_VALUE)
    .build()
  val coreClient = new CoreClient
  lazy val target = Target("127.0.0.1", wireMockServer.port())

  val handler = new Accept[String](List()) {
    def handle(request: TargetedRequest[String], response: ResponseHeader, entityBytes: InputStream) = IOUtils.toString(entityBytes)
  }

  property("Returns response from server") {
    givenThat(
      get(urlEqualTo("/foo")).willReturn(
        aResponse()
      .withStatus(200)
      .withBody("Some text")
      .withHeader("Date", "Sun, 06 Nov 1994 08:49:37 GMT")
      .withHeader("Content-Type", "text/plain")))


    val request = new TargetedRequest(HTTP, target, Request(GET, RequestUri("/foo"), handler))
    val response = Await.result(
      coreClient.execute(request),
      Duration(1, TimeUnit.SECONDS)
    )


    assert(response.code === Code(200))
    assert(response.headerField("Content-Type") === Some(HeaderField("Content-Type", "text/plain")))
    assert(response.body === "Some text")
    assert(response.date === Some(new DateTime("1994-11-06T08:49:37").withZone(DateTimeZone.forID("GMT"))))
  }

  property("Cancelling future disconnects") {
    givenThat(
      get(urlEqualTo("/foo")).willReturn(
        aResponse().withFixedDelay(2000)
      ))
    val request = new TargetedRequest(HTTP, target, Request(GET, RequestUri("/foo"), handler))

    try {
      val response = Await.result(
        coreClient.execute(request),
        Duration(10, TimeUnit.MILLISECONDS)
      )
      fail("Should have timed out!")
    } catch {
      case e: TimeoutException => {
//        apacheClient.getConnectionManager.
      }
    }
  }
}

trait WireMockTest extends Suite with Stubbing {

  val wireMockServer = new WireMockServer(0)
  var wireMock: WireMock = null

  override def run(testName: Option[String], args: Args): Status = {

    wireMockServer.start()
    wireMock = new WireMock("localhost", wireMockServer.port())

    try {
      super.run(testName, args)
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
