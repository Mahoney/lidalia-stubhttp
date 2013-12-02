package uk.org.lidalia.http.headerfields

trait HeaderFieldName[+T] {
  def parse(headerFieldValues: List[String]): T
  def name: String
}
