package uk.org.lidalia.net2

object Url {
  def apply(url: String): Url = ???
}

class Url(
  schemeWithDefaultPort: SchemeWithDefaultPort,
  hierarchicalPartWithAuthority: HierarchicalPartWithAuthority,
  query: ?[Query],
  fragment: ?[Fragment]) extends Uri(schemeWithDefaultPort, hierarchicalPartWithAuthority, query, fragment) {
  
  override val scheme: SchemeWithDefaultPort = schemeWithDefaultPort
  override val hierarchicalPart: HierarchicalPartWithAuthority = hierarchicalPartWithAuthority

  override lazy val hostAndPort: Some[HostAndPort] = hierarchicalPart.authority.hostAndPort
  override lazy val resolvedPort: Some[Port] = Some(hierarchicalPart.authority.get.hostAndPort.port.getOrElse(scheme.defaultPort.get))
  override lazy val path: Path = hierarchicalPart.path
  override lazy val pathAndQuery: PathAndQuery = PathAndQuery(path, query)
  override lazy val absoluteUri: Url = if (fragment.isEmpty) this else new Url(scheme, hierarchicalPart, query, None)
}
