package uk.org.lidalia.http.client

import java.util.concurrent.TimeUnit._

import org.mockito.BDDMockito._
import org.mockito.Mockito.mock
import org.scalatest.prop.TableDrivenPropertyChecks
import org.scalatest.PropSpec
import uk.org.lidalia.http.core.RequestBuilder.get
import uk.org.lidalia.http.core.Response
import uk.org.lidalia.http.core.ResponseBuilder._
import uk.org.lidalia.net2.{Port, IpV4Address, Socket$}
import uk.org.lidalia.net2.Scheme.HTTP

import scala.concurrent.duration.Duration
import scala.concurrent.{Await, Future}
import scala.concurrent.Future._

class DefaultResolvingHttpClientTest extends PropSpec with TableDrivenPropertyChecks {

  val targetedHttpClientMock = mock(classOf[TargetedHttpClient])
  val defaultResolvingHttpClient = new DefaultResolvingHttpClient(targetedHttpClientMock)

  property("Returns response from wrapped client when not a redirect") {
    val request = get()
    val targetedRequest = TargetedRequest(
      HTTP,
      Socket(IpV4Address("127.0.0.1"), Port(80)),
      request.request,
      request.unmarshaller,
      directedParent = request
    )
    val expectedResponse = successful(response())
    given(targetedHttpClientMock.execute(targetedRequest)).willReturn(expectedResponse)

    val actualResponse = defaultResolvingHttpClient.execute(request)

    assert(valueOf(actualResponse) === valueOf(expectedResponse))
  }

  def valueOf[T](future: Future[Response[T]]) = Await.result(future, Duration(10, SECONDS))
}
