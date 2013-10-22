package uk.org.lidalia.net2

import uk.org.lidalia.lang.WrappedValue

object Query {
  def apply(queryStr: String) = new Query(queryStr)
}
class Query private(queryStr: String) extends WrappedValue(queryStr)
