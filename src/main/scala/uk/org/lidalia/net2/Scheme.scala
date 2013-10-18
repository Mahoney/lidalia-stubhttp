package uk.org.lidalia.net2

import scala.collection.concurrent
import scala.collection.concurrent.TrieMap

object Scheme {

  val HTTP = Scheme("http", Port(80))

  val knownSchemes = TrieMap(
    "http" -> new SchemeWithDefaultPort("http", Port(80)),
    "https" -> new SchemeWithDefaultPort("https", Port(443)),
    "ftp" -> new SchemeWithDefaultPort("ftp", Port(21)),
    "ssh" -> new SchemeWithDefaultPort("ssh", Port(22)),

  )

  def apply(name: String, defaultPort: ?[Port]) = defaultPort.map(SchemeWithDefaultPort(name, _)).getOrElse(SimpleScheme(name))

}

sealed abstract class Scheme {

  def name: String
  def defaultPort: ?[Port]

  override def equals(other: Any) = {
    other match {
      case scheme: Scheme => scheme.name == name
      case _ => false
    }
  }

  override def hashCode = name.hashCode
}

object SchemeWithDefaultPort {
  def apply(name: String, port: Port) = new SchemeWithDefaultPort(name, port)

}

final class SchemeWithDefaultPort private(val name: String, val port: Some[Port])

object SimpleScheme {
  def apply(name: String) = new SimpleScheme(name)
}

final class SimpleScheme private(val name: String) {
  def defaultPort = None
}
