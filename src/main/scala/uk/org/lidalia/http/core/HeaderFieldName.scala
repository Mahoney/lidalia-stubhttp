package uk.org.lidalia.http.core

trait HeaderFieldName[+T] {
  def parse(headerFieldValues: List[String]): T
  def name: String
  override val toString = name
}
