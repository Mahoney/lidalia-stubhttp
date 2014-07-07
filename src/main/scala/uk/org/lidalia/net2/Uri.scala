package uk.org.lidalia.net2

import uk.org.lidalia.lang.RichObject

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
}

private object UriParser {

  def parse(uriStr: String): Uri = {
    try {
      val schemeAndRest = uriStr.split(":", 2)
      val hierarchicalPartAndRest = split(schemeAndRest(1), "(?=[\\?#])")
      val queryAndOrFragmentStr = hierarchicalPartAndRest._2
      val queryAndFragment = queryAndOrFragmentStr.map { qf =>
        if (qf.startsWith("?")) {
          val queryAndFragmentStr = split(qf.substring(1), "#")
          val fragment = queryAndFragmentStr._2.map(Fragment(_))
          (Some(Query(queryAndFragmentStr._1)), fragment)
        } else if (qf.startsWith("#")) {
          (None, Some(Fragment(qf.substring(1))))
        } else {
          (None, None)
        }
      } or (None, None)

      Uri(
        Scheme(schemeAndRest(0)),
        HierarchicalPart(hierarchicalPartAndRest._1),
        queryAndFragment._1,
        queryAndFragment._2
      )
    } catch {
      case e: Throwable =>
        throw new UriParseException(
                    s"[$uriStr] is not a valid URI",
                    e
                  )
    }
  }

  private def split(toSplit: String, separator: String): (String, ?[String]) = {
    val elements: Array[String] = toSplit.split(separator, 2)
    val secondElement = if (elements.size == 2) Some(elements(1)) else None
    (elements(0), secondElement)
  }
}

class UriParseException(
      message: String,
      cause: Throwable
) extends RuntimeException(
      message,
      cause
)
