package uk.org.lidalia.net2

object Socket {
  def apply(ipAddress: IpAddress, port: Port) = new Socket(ipAddress, port)
}

case class Socket private(
  ipAddress: IpAddress,
  port: Port
) {
  override def toString = s"${ipAddress.toUriString}:$port"
}
