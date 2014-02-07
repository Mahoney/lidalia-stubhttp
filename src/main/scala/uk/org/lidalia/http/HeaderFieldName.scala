package uk.org.lidalia.http

trait HeaderFieldName[+T] {
  def parse(headerFieldValues: List[String]): T
  def name: String
}
