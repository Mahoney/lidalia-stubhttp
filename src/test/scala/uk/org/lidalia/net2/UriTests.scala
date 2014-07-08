package uk.org.lidalia.net2

import org.scalatest.PropSpec
import org.scalatest.prop.TableDrivenPropertyChecks
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import uk.org.lidalia.net2.EqualsChecks.{possibleArgsFor, reflexiveTest, equalsTest}
import Scheme.{ HTTP, URN }

@RunWith(classOf[JUnitRunner])
class UriTests extends PropSpec with TableDrivenPropertyChecks {

  property("Uri equals contract") {

    val argsForUri = possibleArgsFor(
      List(
        Scheme("scheme1"),
        Scheme("scheme2")
      ),
      List(
        HierarchicalPart("//host1"),
        HierarchicalPart("//host2"),
        HierarchicalPart("example:animal:ferret:nose")
      ),
      List(
        Some(Query("a=b")),
        Some(Query("c=d")),
        None
      ),
      List(
        Some(Fragment("anchor1")),
        Some(Fragment("anchor2")),
        None
      )
    )

    reflexiveTest(argsForUri) { (args) =>
      Uri(args._1, args._2, args._3, args._4)
    }

    equalsTest(argsForUri) { (args) =>
      Uri(args._1, args._2, args._3, args._4)
    }
  }

  property("Valid URIs maintained") {
    val validUris = Table(
      "URI",
      "foo://example.com:8042/over/there?name=ferret#nose",
      "foo://example.com:8042/over/there?name=ferret",
      "foo://example.com:8042/over/there",
      "foo://example.com:8042/over/there#nose",
      "foo://example.com:8042/over/there#",
      "foo://example.com:8042/over/there?",
      "foo://example.com:8042/over/there?#",
      "foo://example.com:8042/over/there?blah#",
      "foo://example.com:8042/over/there?#blah",
      "urn:example:animal?ferret#nose",
      "urn:example:animal?ferret",
      "urn:example:animal?ferret",
      "urn:example:animal#nose",
      "urn:example:animal"
    )

    forAll(validUris) { uri =>
      assert(
        Uri(uri).toString === uri
      )
    }
  }

  property("Uri parsed correctly") {
    val uris = Table(
      ("URI",                                                 "Scheme", "Hierarchical Part",             "Query",             "Fragment"),
      ("http://example.com:8042/over/there?name=ferret#nose", HTTP,     "//example.com:8042/over/there", Some("name=ferret"), Some("nose")),
      ("http://example.com:8042/over/there?name=ferret",      HTTP,     "//example.com:8042/over/there", Some("name=ferret"), None),
      ("http://example.com:8042/over/there",                  HTTP,     "//example.com:8042/over/there", None,                None),
      ("http://example.com:8042/over/there#nose",             HTTP,     "//example.com:8042/over/there", None,                Some("nose")),
      ("urn:example:animal?ferret#nose",                      URN,      "example:animal",                Some("ferret"),      Some("nose")),
      ("urn:example:animal?ferret",                           URN,      "example:animal",                Some("ferret"),      None),
      ("urn:example:animal#nose",                             URN,      "example:animal",                None,                Some("nose")),
      ("urn:example:animal#",                                 URN,      "example:animal",                None,                Some("")),
      ("urn:example:animal?",                                 URN,      "example:animal",                Some(""),            None),
      ("urn:example:animal?#",                                URN,      "example:animal",                Some(""),            Some("")),
      ("urn:example:animal?blah#",                            URN,      "example:animal",                Some("blah"),        Some("")),
      ("urn:example:animal?#blah",                            URN,      "example:animal",                Some(""),            Some("blah")),
      ("urn:example:animal",                                  URN,      "example:animal",                None,                None)
    )

    forAll(uris) { (uriString,
                    scheme,
                    hierarchicalPartStr,
                    queryStr,
                    fragmentStr) =>
      val uri = Uri(uriString)
      assert(uri.scheme === scheme)
      assert(uri.hierarchicalPart === HierarchicalPart(hierarchicalPartStr))
      assert(uri.query === queryStr.map(Query(_)))
      assert(uri.fragment === fragmentStr.map(Fragment(_)))
    }
  }

  property("Invalid uris rejected") {
    val invalidUrisAndErrorMessages = Table(
      ("URI", "Error message"),
      ("http", "[http] is not a valid URI"),
      (".http", "[.http] is not a valid URI")
    )

    forAll(invalidUrisAndErrorMessages) { (invalidUri,
                                           expectedErrorMessage) =>
      val exception = intercept[UriParseException] {
        Uri(invalidUri)
      }

      assert(
        exception.getMessage === expectedErrorMessage
      )
    }
  }
}
