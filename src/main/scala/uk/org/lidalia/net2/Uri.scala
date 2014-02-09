package uk.org.lidalia.net2

import uk.org.lidalia.lang.RichObject

object Uri {

  def apply(uri: String): Uri = {
    val uriSegments = parse(uri, List(":", "\\?", "#"))
    new Uri(
      Scheme(uriSegments(0).get),
      HierarchicalPart(uriSegments(1).get),
      uriSegments(2).map(Query(_)),
      uriSegments(3).map(Fragment(_))
    )
  }
  
  def parse(toParse: ?[String], separators: List[String]): List[?[String]] = {
    separators match {
      case Nil => List(toParse)
      case _ =>
        val headAndRest = toParse.map(split(_, separators.head)) or (None, None)
        headAndRest._1 :: parse(headAndRest._2, separators.tail)
    }
  }

  def split(toSplit: String, separator: String): (Some[String], ?[String]) = {
    val elements: Array[String] = toSplit.split(separator, 2)
    val secondElement = if (elements.size == 2) Some(elements(1)) else None
    (elements(0), secondElement)
  }

  def apply(
         scheme: Scheme,
         hierarchicalPart: HierarchicalPart,
         query: ?[Query] = None,
         fragment: ?[Fragment] = None): Uri =
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
