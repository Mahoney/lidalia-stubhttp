package uk.org.lidalia.net2

import uk.org.lidalia.lang.RichObject

object Uri {

  def apply(
         scheme: Scheme,
         hierarchicalPart: HierarchicalPart,
         query: ?[Query] = None,
         fragment: ?[Fragment] = None) =
    new Uri(scheme, hierarchicalPart, query, fragment)
}

class Uri private(@Identity val scheme: Scheme,
                  @Identity val hierarchicalPart: HierarchicalPart,
                  @Identity val query: ?[Query],
                  @Identity val fragment: ?[Fragment])
    extends RichObject with Immutable {

  override final def toString = {
    val baseUri = s"$scheme:$hierarchicalPart"
    val uriWithQuery = query map (q=>s"$baseUri?$q") or baseUri
    val uriWithFragment = fragment.map(f=>s"$uriWithQuery#$f") or uriWithQuery
    uriWithFragment
  }
}
