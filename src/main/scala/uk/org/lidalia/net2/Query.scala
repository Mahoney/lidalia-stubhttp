package uk.org.lidalia.net2

object Query {
  def apply(queryStr: String) = new Query(queryStr)
}

class Query private(override val toString: String) extends AnyVal
