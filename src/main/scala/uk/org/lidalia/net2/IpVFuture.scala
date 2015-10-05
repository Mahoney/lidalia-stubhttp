package uk.org.lidalia.net2

object IpVFuture {

  def apply(ipAddressStr: String) = IpVFutureParser(ipAddressStr)

}

class IpVFuture private [net2] (override val toString: String) extends IpAddressInternal {
  override val toUriString: String = s"[$toString]"
}

private object IpVFutureParser {

  def apply(ipVFutureStr: String): IpVFuture = new IpVFuture(ipVFutureStr)

}
