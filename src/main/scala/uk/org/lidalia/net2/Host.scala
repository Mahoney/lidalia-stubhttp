package uk.org.lidalia.net2

import java.net.{Inet6Address, Inet4Address, InetAddress}

object Host {
  def apply(hostStr: String): Host = ???
}

sealed trait Host {
  val toUriString: String
}

object IpAddress {
  def apply(inetAddress: InetAddress) = {
    inetAddress match {
      case i: Inet4Address => IpV4Address(i.getHostAddress)
      case i: Inet6Address => IpV6Address(i.getHostAddress)
      case i: InetAddress => IpVFuture(i.getHostAddress)
    }
  }
}

sealed trait IpAddress extends Host
private [net2] trait IpAddressInternal extends IpAddress
private [net2] trait RegisteredNameInternal extends Host
