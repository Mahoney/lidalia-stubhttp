package uk.org.lidalia.net2

object Url {
  def apply(url: String): Url = {
    val uri = Uri(url)
    new Url(
      uri.scheme.asInstanceOf[SchemeWithDefaultPort],
      uri.hierarchicalPart.asInstanceOf[HierarchicalPartWithAuthority],
      uri.query,
      uri.fragment
    )
  }
}

class Url private (
  override val scheme: SchemeWithDefaultPort,
  override val hierarchicalPart: HierarchicalPartWithAuthority,
  query: ?[Query] = None,
  fragment: ?[Fragment] = None) extends Uri(scheme, hierarchicalPart, query, fragment) {
  
  override lazy val path: PathAfterAuthority = hierarchicalPart.path
  override lazy val absoluteUri: Url = if (fragment.isEmpty) this else new Url(scheme, hierarchicalPart, query, None)

  lazy val hostAndPort: HostAndPort = hierarchicalPart.authority.get.hostAndPort
  lazy val resolvedPort: Port = hostAndPort.port.getOrElse(scheme.defaultPort.get)
}
