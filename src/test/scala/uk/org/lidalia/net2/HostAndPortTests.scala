package uk.org.lidalia.net2

import org.scalatest
import scalatest.PropSpec
import org.scalatest.prop.TableDrivenPropertyChecks
import scalatest.junit.JUnitRunner
import org.junit.runner.RunWith
import EqualsChecks.{possibleArgsFor, reflexiveTest, equalsTest }

@RunWith(classOf[JUnitRunner])
class HostAndPortTests extends PropSpec with TableDrivenPropertyChecks {

  property("ResolvedHostAndPort equals") {
    val argsForResolvedHostAndPort = possibleArgsFor(
      List(Host("host1"), Host("host2")),
      List(Port(1), Port(2))
    )

    reflexiveTest(argsForResolvedHostAndPort) { (args) =>
      HostWithPort(args._1, args._2)
    }

    equalsTest(argsForResolvedHostAndPort) { (args) =>
      HostWithPort(args._1, args._2)
    }
  }

  property("HostWithoutPort equals") {
    val argsForHostWithoutPort = possibleArgsFor(List(Host("host1"), Host("host2")))

    reflexiveTest(argsForHostWithoutPort) { arg =>
      HostWithoutPort(arg)
    }

    equalsTest(argsForHostWithoutPort) { arg =>
      HostWithoutPort(arg)
    }
  }
}
