package uk.org.lidalia.net2

import uk.org.lidalia.lang.RichObject
import uk.org.lidalia.net2.UriConstants._

object PathAndQuery {
  def apply(pathAndQueryStr: String): PathAndQuery = {
    if (pathAndQueryStr.startsWith("?")) {
      PathAndQuery(PathAfterAuthority(), Query(pathAndQueryStr.substring(1)))
    } else {
      val pathAndQuery: (String, ?[String]) = split(pathAndQueryStr, "\\?")
      PathAndQuery(PathAfterAuthority(pathAndQuery._1), pathAndQuery._2.map(Query(_)))
    }
  }
  def apply(path: Path, query: ?[Query] = None): PathAndQuery = new PathAndQuery(path, query)
}

class PathAndQuery private (
  @Identity val path: Path,
  @Identity val query: ?[Query]
) extends RichObject
{
  override def toString = query.map(q => s"$path?$q") or path.toString
}
