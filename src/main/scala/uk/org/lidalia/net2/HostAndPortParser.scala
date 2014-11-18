package uk.org.lidalia.net2

object HostAndPortParser {
  def parse(hostAndPortStr: String): HostAndPort = {
    val hostAndPortSplit = hostAndPortStr.split(":", 2)
    val host = Host(hostAndPortSplit(0))
    if (hostAndPortSplit.size == 2) {
      HostWithPort(
        host,
        Port(hostAndPortSplit(1))
      )
    } else {
      HostWithoutPort(
        host
      )
    }
  }
}
