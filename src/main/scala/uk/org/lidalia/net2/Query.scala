package uk.org.lidalia.net2

object Query {
  def apply(queryStr: String) = {
    require(queryStr != null, "Query cannot be null")
    new Query(queryStr)
  }
}

class Query private(override val toString: String) extends AnyVal
