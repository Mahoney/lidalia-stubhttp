package uk.org.lidalia.net2

object HostAndPort {
  def apply(host: Host, port: ?[Port] = None): HostAndPort = {
    port.map(ResolvedHostAndPort(host, _)) or HostWithoutPort(host)
  }
}

object ResolvedHostAndPort {
  def apply(host: Host, port: Port) = new ResolvedHostAndPort(host, port)
}

object HostWithoutPort {
  def apply(host: Host) = new HostWithoutPort(host)
}

abstract sealed class HostAndPort extends Immutable {
  val host: Host
  val port: ?[Port]

  override def equals(other: Any) = {
    other match {
      case hostAndPort: HostAndPort =>
        hostAndPort.host == host && hostAndPort.port == port
      case _ => false
    }
  }

  override def hashCode = 31 * (31 * host.hashCode) + port.hashCode
}

final class ResolvedHostAndPort private (val host: Host, val port: Some[Port])
    extends HostAndPort {
  override def toString = s"$host:${port.get}"
}

final class HostWithoutPort private (val host: Host) extends HostAndPort {
  val port = None
  override def toString = host.toString
}
