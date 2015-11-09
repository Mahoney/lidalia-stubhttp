package uk.org.lidalia.http.client

import org.scalatest.prop.TableDrivenPropertyChecks
import org.scalatest.PropSpec

class DefaultResolvingHttpClientTests extends PropSpec with TableDrivenPropertyChecks {

//  val targetedHttpClientMock = mock(classOf[TargetedHttpClient])
//  val defaultResolvingHttpClient = new DefaultResolvingHttpClient(targetedHttpClientMock)
//
//  property("Returns response from wrapped client when not a redirect") {
//    val request = get()
//    val targetedRequest = TargetedRequest(
//      HTTP,
//      Socket(IpV4Address("127.0.0.1"), Port(80)),
//      request.request,
//      request.unmarshaller,
//      directedParent = request
//    )
//    val expectedResponse = successful(response())
//    given(targetedHttpClientMock.execute(targetedRequest)).willReturn(expectedResponse)
//
//    val actualResponse = defaultResolvingHttpClient.execute(request)
//
//    assert(valueOf(actualResponse) === valueOf(expectedResponse))
//  }
//
//  def valueOf[T](future: Future[Response[T]]) = Await.result(future, Duration(10, SECONDS))
}
