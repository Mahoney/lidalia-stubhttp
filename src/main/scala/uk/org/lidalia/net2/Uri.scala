package uk.org.lidalia.net2

import uk.org.lidalia.lang.RichObject
import uk.org.lidalia.net2.UriConstants.split

/**
 * Models a URI as defined in
 * <a href="http://tools.ietf.org/html/rfc39861">RFC 3986</a>.
 */
object Uri {

  def apply(uri: String) = UriParser.parse(uri)

  def apply(
         scheme: Scheme,
         hierarchicalPart: HierarchicalPart,
         query: ?[Query] = None,
         fragment: ?[Fragment] = None
       ) =
    new Uri(
      scheme,
      hierarchicalPart,
      query,
      fragment
    )
}

class Uri private(@Identity val scheme: Scheme,
                  @Identity val hierarchicalPart: HierarchicalPart,
                  @Identity val query: ?[Query],
                  @Identity val fragment: ?[Fragment])
    extends RichObject
    with Immutable {

  override final def toString = {
    val baseUri = s"$scheme:$hierarchicalPart"
    val uriWithQuery = query map (q=>s"$baseUri?$q") or baseUri
    val uriWithFragment = fragment.map(f=>s"$uriWithQuery#$f") or uriWithQuery
    uriWithFragment
  }

  lazy val hostAndPort: ?[HostAndPort] = hierarchicalPart.authority.?(_.hostAndPort)
  lazy val resolvedPort: ?[Port] = hierarchicalPart.authority.?(_.hostAndPort.port).orElse(scheme.defaultPort)
  lazy val path: Path = hierarchicalPart.path
  lazy val pathAndQuery: PathAndQuery = PathAndQuery(path, query)
  lazy val absoluteUri: Uri = if (fragment.isEmpty) this else Uri(scheme, hierarchicalPart, query, None)
}

private object UriParser {

  def parse(uriStr: String): Uri = {
    try {
      val schemeAndRest = uriStr.split(":", 2)
      val scheme = Scheme(schemeAndRest(0))

      val hierarchicalPartAndRest = parseHierarchicalPartAndRest(schemeAndRest(1))
      val hierarchicalPart = HierarchicalPart(hierarchicalPartAndRest._1)

      val queryAndOrFragmentStr = hierarchicalPartAndRest._2
      val queryAndFragment = queryAndOrFragmentStr.map(parseQueryAndFragment) or (None, None)
      val query = queryAndFragment._1
      val fragment = queryAndFragment._2

      Uri(
        scheme,
        hierarchicalPart,
        query,
        fragment
      )
    } catch {
      case e: Throwable =>
        throw new UriParseException(
                    s"[$uriStr] is not a valid URI",
                    e
                  )
    }
  }

  def parseHierarchicalPartAndRest(hierarchicalPartAndRest: String) = {
    if (hierarchicalPartAndRest.startsWith("?") || hierarchicalPartAndRest.startsWith("#")) {
      ("", Some(hierarchicalPartAndRest))
    } else {
      split(hierarchicalPartAndRest, "(?=[\\?#])")
    }
  }

  private def parseQueryAndFragment(qf: String): (Option[Query], Option[Fragment]) = {
    if (qf.startsWith("?")) {
      val queryAndFragmentStr = split(qf.substring(1), "#")
      val fragment = queryAndFragmentStr._2.map(Fragment(_))
      val query = Query(queryAndFragmentStr._1)
      (Some(query), fragment)
    } else if (qf.startsWith("#")) {
      val fragment = Fragment(qf.substring(1))
      (None, Some(fragment))
    } else {
      (None, None)
    }
  }
}

class UriParseException(
      message: String,
      cause: Throwable
) extends RuntimeException(
      message,
      cause
)
