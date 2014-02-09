package uk.org.lidalia.http.client.async

import org.scalatest
import org.junit
import org.mockito
import uk.org.lidalia
import lidalia.http

import scala.concurrent.{Await, Future}
import scala.concurrent.duration.Duration
import Future.successful
import java.util.concurrent.TimeUnit.SECONDS

import scalatest.PropSpec
import scalatest.prop.TableDrivenPropertyChecks
import scalatest.junit.JUnitRunner

import junit.runner.RunWith

import mockito.Mockito.mock
import mockito.BDDMockito.given

import http.core.headerfields.Location
import http.core.ResponseBuilder.response
import http.core.{RequestBuilder, Response, Code}
import RequestBuilder.get
import Code.Found

import lidalia.net2.Uri

@RunWith(classOf[JUnitRunner])
class RedirectFollowingClientTest extends PropSpec with TableDrivenPropertyChecks {

  val decorated = mock(classOf[HttpClient])
  val redirectFollowingHttpClient = new RedirectFollowingClient(decorated)

  def valueOf(future: Future[Response]) = Await.result(future, Duration(10, SECONDS))

  property("Returns response from wrapped client when not a redirect") {
    val request = get()
    val expectedResponse = successful(response())
    given(decorated.execute(request)).willReturn(expectedResponse)

    val actualResponse = redirectFollowingHttpClient.execute(request)

    assert(valueOf(actualResponse) === valueOf(expectedResponse))
  }

  property("Follows 302 redirect") {
    val request1 = get()
    val redirectLocation = Uri("http://www.example.com/redirectlocation")
    val redirectResponse = successful(response(Found, List(Location(redirectLocation))))
    given(decorated.execute(request1)).willReturn(redirectResponse)

    val request2 = get(redirectLocation)
    val expectedResponse = successful(response())
    given(decorated.execute(request2)).willReturn(expectedResponse)

    val actualResponse = redirectFollowingHttpClient.execute(request1)

    assert(valueOf(actualResponse) === valueOf(expectedResponse))
  }

  property("Follows two 302 redirects") {
    val request1 = get()
    val redirectLocation1 = Uri("http://www.example.com/redirectlocation")
    val redirectResponse1 = successful(response(Found, List(Location(redirectLocation1))))
    given(decorated.execute(request1)).willReturn(redirectResponse1)

    val request2 = get(redirectLocation1)
    val redirectLocation2 = Uri("http://www.example.com/redirectlocation2")
    val redirectResponse2 = successful(response(Found, List(Location(redirectLocation2))))
    given(decorated.execute(request2)).willReturn(redirectResponse2)

    val request3 = get(redirectLocation2)
    val expectedResponse = successful(response())
    given(decorated.execute(request3)).willReturn(expectedResponse)

    val actualResponse = redirectFollowingHttpClient.execute(request1)

    assert(valueOf(actualResponse) === valueOf(expectedResponse))
  }
}
