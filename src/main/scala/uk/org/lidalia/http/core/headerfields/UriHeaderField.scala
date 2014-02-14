package uk.org.lidalia.http.core.headerfields

import uk.org.lidalia
import lidalia.net2.Uri
import lidalia.http.core.{HeaderFieldName, HeaderField}

abstract class UriHeaderField protected(name: String, val uri: Uri) extends HeaderField(name, List(uri.toString))

abstract class UriHeaderFieldName extends HeaderFieldName[?[Uri]] {

  def parse(headerFieldValues: List[String]) = headerFieldValues.headOption.map(Uri(_))

}
