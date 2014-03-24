package uk.org.lidalia.net2

import uk.org.lidalia.lang.RichObject

object PathAndQuery {
  def apply(pathAndQuery: String): PathAndQuery = apply(Path(pathAndQuery))
  def apply(path: Path, query: ?[Query] = None): PathAndQuery = new PathAndQuery(path, query)
}
class PathAndQuery(@Identity val path: Path, @Identity val query: ?[Query]) extends RichObject {
  override def toString = query.map(q=>s"$path?$q") or path.toString
}
