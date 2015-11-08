package uk.org.lidalia.http.core.headerfields

abstract class StringHeaderFieldName extends SingleValueHeaderFieldName[String] {

  override def parse(headerFieldValue: String) = headerFieldValue

  override def :=(etag: String) = apply(etag)
}
