package uk.org.lidalia.net2

import org.scalatest.PropSpec
import org.scalatest.prop.TableDrivenPropertyChecks
import uk.org.lidalia.net2.EqualsChecks._

class HierarchicalPartTest extends PropSpec with TableDrivenPropertyChecks {

  property("HierarchicalPart equals contract") {
    val argsForHierarchicalPart = possibleArgsFor(
      List(
        "/path",
        "/",
        "",
        "/path1/path2",
        "//name/path",
        "//name/",
        "//name",
        "//name/path1/path2"
      )
    )

    reflexiveTest(argsForHierarchicalPart) { args =>
      HierarchicalPart(args)
    }

    equalsTest(argsForHierarchicalPart) { args =>
      HierarchicalPart(args)
    }
  }

  property("HierarchicalPartWithAuthority when part has authority") {

    val cases = Table(
      ("HierarchicalPart String", "Authority",       "Path"                            ),
      ("//name/path",             Authority("name"), PathAfterAuthority("/path")       ),
      ("//name/",                 Authority("name"), PathAfterAuthority("/")           ),
      ("//name",                  Authority("name"), PathAfterAuthority("")            ),
      ("//name/path1/path2",      Authority("name"), PathAfterAuthority("/path1/path2"))
    )

    forAll(cases) { (hierPartStr, expectedAuthority, expectedPath) =>
      val hierPart = HierarchicalPart(hierPartStr)
      assert(hierPart.getClass === classOf[HierarchicalPartWithAuthority])
      assert(hierPart.authority === Some(expectedAuthority))
      assert(hierPart.path === expectedPath)
    }
  }

  property("HierarchicalPartPathOnly when part has no authority") {

    val cases = Table(
      ("HierarchicalPart String", "Path"                         ),
      ("/path",                   PathNoAuthority("/path")       ),
      ("/",                       PathNoAuthority("/")           ),
      ("",                        PathNoAuthority("")            ),
      ("/path1/path2",            PathNoAuthority("/path1/path2"))
    )

    forAll(cases) { (hierPartStr, expectedPath) =>
      val hierPart = HierarchicalPart(hierPartStr)
      assert(hierPart.getClass === classOf[HierarchicalPartPathOnly])
      assert(hierPart.authority === None)
      assert(hierPart.path === expectedPath)
    }
  }
}
