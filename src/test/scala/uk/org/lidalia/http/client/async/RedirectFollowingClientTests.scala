package uk.org.lidalia.http.client

import java.util.concurrent.TimeUnit.SECONDS

import org.{mockito, scalatest}
import mockito.BDDMockito.given
import mockito.Mockito.mock
import scalatest.PropSpec
import scalatest.prop.TableDrivenPropertyChecks
import uk.org.lidalia
import lidalia.http
import http.core.Code.Found
import http.core.RequestBuilder.{get, request}
import http.core.ResponseBuilder.response
import http.core.headerfields.Location
import http.core.{RequestUri, Response}
import lidalia.net2.{Url, HostAndPort}

import scala.concurrent.Future.successful
import scala.concurrent.duration.Duration
import scala.concurrent.{Await, Future}

class RedirectFollowingClientTests extends PropSpec with TableDrivenPropertyChecks {

  val decorated = mock(classOf[HttpClient])
  val redirectFollowingHttpClient = new RedirectFollowingClient(decorated)

  def valueOf[T](future: Future[Response[T]]) = Await.result(future, Duration(10, SECONDS))

  property("Returns response from wrapped client when not a redirect") {
    val request = get()
    val expectedResponse = successful(response())
    given(decorated.execute(request)).willReturn(expectedResponse)

    val actualResponse = redirectFollowingHttpClient.execute(request)

    assert(valueOf(actualResponse) === valueOf(expectedResponse))
  }

  property("Follows 302 redirect") {
    val request1 = get()
    val redirectLocation = Url("http://www.example.com/redirectlocation")
    val redirectResponse = successful(response(Found, List(Location(redirectLocation))))
    given(decorated.execute(request1)).willReturn(redirectResponse)

    val request2 = request(
      hostAndPort = HostAndPort("www.example.com"),
      uri = RequestUri("/redirectlocation")
    )
    val expectedResponse = successful(response())
    given(decorated.execute(request2)).willReturn(expectedResponse)

    val actualResponse = redirectFollowingHttpClient.execute(request1)

    assert(valueOf(actualResponse) === valueOf(expectedResponse))
  }

  property("Follows two 302 redirects") {
    val request1 = get()
    val redirectLocation1 = Url("http://www.example.com/redirectlocation")
    val redirectResponse1 = successful(response(Found, List(Location(redirectLocation1))))
    given(decorated.execute(request1)).willReturn(redirectResponse1)

    val request2 = get(redirectLocation1)
    val redirectLocation2 = Url("http://www.example.com/redirectlocation2")
    val redirectResponse2 = successful(response(Found, List(Location(redirectLocation2))))
    given(decorated.execute(request2)).willReturn(redirectResponse2)

    val request3 = get(redirectLocation2)
    val expectedResponse = successful(response())
    given(decorated.execute(request3)).willReturn(expectedResponse)

    val actualResponse = redirectFollowingHttpClient.execute(request1)

    assert(valueOf(actualResponse) === valueOf(expectedResponse))
  }
}
