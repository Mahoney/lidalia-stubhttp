package uk.org.lidalia.http.client

import javax.json.{Json, JsonObject}

import com.github.tomakehurst.wiremock
import org.scalatest
import uk.org.lidalia
import lidalia.http

import scalatest.{Args, PropSpec, Suite, Status}
import scalatest.prop.TableDrivenPropertyChecks
import lidalia.net2.Scheme.HTTP
import uk.org.lidalia.support.WithResourceTest

import wiremock.WireMockServer
import wiremock.client.{RequestPatternBuilder, MappingBuilder, WireMock}
import wiremock.junit.Stubbing
import WireMock.{get, urlEqualTo, aResponse}

import uk.org.lidalia.net2.{Port, IpV4Address, Socket}
import http.core.Method.GET
import http.core.{MediaRangePref, MediaRange, Request, ResponseHeader, RequestUri, HeaderField, Code}

import org.apache.commons.io.IOUtils

import scala.concurrent.Await
import scala.concurrent.duration.Duration
import java.util.concurrent.{TimeoutException, TimeUnit}
import java.io.{InputStreamReader, InputStream}
import org.joda.time.{DateTimeZone, DateTime}

class TargetedHttpClientTests extends TableDrivenPropertyChecks with WithResourceTest {

//  val coreClient = new Apache4Client()
//  lazy val target = Socket(IpV4Address("127.0.0.1"), Port(wireMock.port()))
//
//  val unmarshaller = new Accept[String](List(new MediaRangePref(new MediaRange("text/plain")))) {
//    def unmarshal(request: Request, response: ResponseHeader, entityBytes: InputStream) = IOUtils.toString(entityBytes)
//  }
//
//  property("Returns response from server") {
//    givenThat(
//      get(urlEqualTo("/foo")).willReturn(
//        aResponse()
//      .withStatus(200)
//      .withBody("Some text")
//      .withHeader("Date", "Sun, 06 Nov 1994 08:49:37 GMT")
//      .withHeader("Content-Type", "text/plain")))
//
//
//    val request = TargetedRequest(HTTP, target, Request(GET, RequestUri("/foo")), unmarshaller)
//    val response = Await.result(
//      coreClient.execute(request),
//      Duration(1, TimeUnit.SECONDS)
//    )
//
//
//    assert(response.code === Code(200))
//    assert(response.headerField("Content-Type") === Some(HeaderField("Content-Type", "text/plain")))
//    assert(response.entity === Right("Some text"))
//    assert(response.date === Some(new DateTime("1994-11-06T08:49:37").withZone(DateTimeZone.forID("GMT"))))
//  }
//
//  property("Cancelling future disconnects") {
//    givenThat(
//      get(urlEqualTo("/foo")).willReturn(
//        aResponse().withFixedDelay(2000)
//      ))
//    val request = TargetedRequest(HTTP, target, Request(GET, RequestUri("/foo")), unmarshaller)
//
//    try {
//      val response = Await.result(
//        coreClient.execute(request),
//        Duration(10, TimeUnit.MILLISECONDS)
//      )
//      fail("Should have timed out!")
//    } catch {
//      case e: TimeoutException => fail("prove here that the connection has gone...")
//    }
//  }
//
//  property("Failure to unmarshal returns body as Left") {
//    givenThat(
//      get(urlEqualTo("/foo")).willReturn(
//        aResponse()
//          .withStatus(200)
//          .withBody("Not json!")
//          .withHeader("Date", "Sun, 06 Nov 1994 08:49:37 GMT")
//          .withHeader("Content-Type", "application/json")))
//
//
//    val request = TargetedRequest(
//      HTTP,
//      target,
//      Request(GET, RequestUri("/foo")),
//      new Accept[JsonObject](List(new MediaRangePref(new MediaRange("application/json")))) {
//        def unmarshal(request: Request, response: ResponseHeader, entityBytes: InputStream) = Json.createReader(new InputStreamReader(entityBytes, "UTF-8")).readObject()
//      }
//    )
//
//    val response = Await.result(
//      coreClient.execute(request),
//      Duration(1, TimeUnit.SECONDS)
//    )
//    assert(response.code === Code(200))
//    assert(response.headerField("Content-Type") === Some(HeaderField("Content-Type", "application/json")))
//    assert(response.date === Some(new DateTime("1994-11-06T08:49:37").withZone(DateTimeZone.forID("GMT"))))
//    assert(response.entity === Left("Not json!"))
//  }
}


