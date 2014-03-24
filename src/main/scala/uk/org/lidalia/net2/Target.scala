package uk.org.lidalia.net2

import uk.org.lidalia.lang.RichObject

object Target {
  def apply(ipAddress: String, port: Int) = new Target(IpAddress(ipAddress), Port(port))
  def apply(ipAddress: IpAddress, port: Port) = new Target(ipAddress, port)
}

class Target private(@Identity val ipAddress: IpAddress, @Identity val port: Port) extends RichObject
