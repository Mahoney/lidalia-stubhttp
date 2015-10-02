package uk.org.lidalia.net2

object IpVFuture {

  def apply(ipAddressStr: String) = IpVFutureParser(ipAddressStr)

}

class IpVFuture extends IpAddressInternal {
  override val toUriString: String = s"[$toString]"
}

object IpVFutureParser {

  def apply(IpVFutureStr: String): IpVFuture = ???

}
