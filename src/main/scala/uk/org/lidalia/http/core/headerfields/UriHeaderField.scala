package uk.org.lidalia.http.core.headerfields

import uk.org.lidalia
import lidalia.net2.Uri
import lidalia.http.core.{HeaderFieldName, HeaderField}
import scala.util.Try

abstract class UriHeaderFieldName extends SingleValueHeaderFieldName[Uri] {

  def parse(headerFieldValue: String) = Try(Uri(headerFieldValue)).toOption

}

abstract class UriHeaderField protected(name: String, val uri: Uri) extends HeaderField(name, List(uri.toString))
