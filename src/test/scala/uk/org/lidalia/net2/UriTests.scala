package uk.org.lidalia.net2

import org.scalatest.PropSpec
import org.scalatest.prop.TableDrivenPropertyChecks
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import uk.org.lidalia.net2.EqualsChecks.{possibleArgsFor, reflexiveTest, equalsTest}

@RunWith(classOf[JUnitRunner])
class UriTests extends PropSpec with TableDrivenPropertyChecks {

  property("Uri equals contract") {

    val argsForUri = possibleArgsFor(
      List(Scheme("scheme1"), Scheme("scheme2")),
      List(HierarchicalPart("//host1"), HierarchicalPart("//host2")),
      List(Some(Query("a=b")), Some(Query("c=d")), None),
      List(Some(Fragment("anchor1")), Some(Fragment("anchor2")), None)
    )

    reflexiveTest(argsForUri) { (args) =>
      Uri(args._1, args._2, args._3, args._4)
    }

    equalsTest(argsForUri) { (args) =>
      Uri(args._1, args._2, args._3, args._4)
    }
  }
}
