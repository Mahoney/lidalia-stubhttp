package uk.org.lidalia.http.client

import java.net.InetAddress

import uk.org.lidalia.http.core.Response
import uk.org.lidalia.net2._

import scala.concurrent.Future

class DefaultResolvingHttpClient(decorated: TargetedHttpClient) extends HttpClient {

  override def execute[T](request: DirectedRequest[T]): Future[Response[Either[String, T]]] = {
    decorated.execute(
      TargetedRequest(
        request.scheme,
        resolve(request.scheme, request.hostAndPort),
        request.request,
        request.unmarshaller,
        directedParent = request
      )
    )
  }

  def resolve(scheme: Scheme, hostAndPort: HostAndPort): Target = {
    val address = resolve(hostAndPort.host)
    val port = hostAndPort.port.getOrElse(scheme.defaultPort.get)
    Target(address, port)
  }

  def resolve(host: Host): IpAddress = {
    IpAddress(InetAddress.getByName(host.toString))
  }
}
