package uk.org.lidalia.net2

import scala.collection.concurrent.TrieMap
import uk.org.lidalia.lang.WrappedValue

object Scheme {

  private val VALID_SCHEME_REGEX = "[a-zA-Z][a-zA-Z0-9+\\-\\.]*".r

  private val knownSchemes = TrieMap[String, SchemeWithDefaultPort]()

  val HTTP = Scheme("http", Port(80))
  val HTTPS = Scheme("https", Port(443))
  val FTP = Scheme("ftp", Port(21))
  val SSH = Scheme("ssh", Port(22))
  val MAILTO = new SimpleScheme("mailto")
  val FILE = new SimpleScheme("file")

  def apply(name: String) =
    knownSchemes.getOrElse(name.toLowerCase, new SimpleScheme(name))

  def apply(name: String, defaultPort: Port) = {
    val scheme = new SchemeWithDefaultPort(name, defaultPort)
    knownSchemes.putIfAbsent(name.toLowerCase, scheme).getOrElse(scheme)
  }
}

sealed abstract class Scheme(val name: String)
    extends WrappedValue(name) with Immutable {

  require(Scheme.VALID_SCHEME_REGEX.findFirstIn(name).isDefined,
    "scheme must match "+Scheme.VALID_SCHEME_REGEX)

  val defaultPort: ?[Port]
}

final class SchemeWithDefaultPort private[net2](name: String, val defaultPort: Some[Port])
    extends Scheme(name)

final class SimpleScheme private[net2](name: String) extends Scheme(name) {
  val defaultPort = None
}
