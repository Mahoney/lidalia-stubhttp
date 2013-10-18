package uk.org.lidalia.net2

object HostAndPort {
  def apply(host: Host, port: ?[Port]): HostAndPort =
    port.map(ResolvedHostAndPort(host, _)).getOrElse(HostWithoutPort(host))
}

object ResolvedHostAndPort {
  def apply(host: Host, port: Port) = new ResolvedHostAndPort(host, port)
}

object HostWithoutPort {
  def apply(host: Host) = new HostWithoutPort(host)
}

abstract sealed class HostAndPort {
  def host: Host
  def port: ?[Port]

  override def equals(other: Any) = {
    other match {
      case hostAndPort: HostAndPort => hostAndPort.host == host && hostAndPort.port == port
      case _ => false
    }
  }
}

final class ResolvedHostAndPort private (val host: Host, val port: Some[Port]) extends HostAndPort {
  override def toString = s"$host:${port.get}"
}

final class HostWithoutPort private (val host: Host) extends HostAndPort {

  def port = None

  override def toString = host.toString
}
