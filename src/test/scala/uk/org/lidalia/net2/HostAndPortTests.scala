package uk.org.lidalia.net2

import org.scalatest
import scalatest.PropSpec
import scalatest.prop.TableDrivenPropertyChecks
import scalatest.junit.JUnitRunner
import org.junit.runner.RunWith

@RunWith(classOf[JUnitRunner])
class HostAndPortTests extends PropSpec with TableDrivenPropertyChecks {

  property("HostAndPort with Some port equal to ResolvedHostAndPort") {
    val hostAndPort = HostAndPort(Host("name.me"), Some(Port(80)))
    val resolvedHostAndPort = ResolvedHostAndPort(Host("name.me"), Port(80))
    assert(hostAndPort === resolvedHostAndPort)
  }

  property("HostAndPort with None port equal to HostWithoutPort") {
    val hostAndPort = HostAndPort(Host("name.me"), None)
    val hostWithoutPort = HostWithoutPort(Host("name.me"))
    assert(hostAndPort === hostWithoutPort)
  }
}
