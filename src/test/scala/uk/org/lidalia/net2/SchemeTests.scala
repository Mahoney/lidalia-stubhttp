package uk.org.lidalia.net2

import org.scalatest.PropSpec
import org.scalatest.prop.TableDrivenPropertyChecks
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import java.lang.IllegalArgumentException

@RunWith(classOf[JUnitRunner])
class SchemeTests extends PropSpec with TableDrivenPropertyChecks {

  property("Only valid schemes work") {
    val exception = intercept[IllegalArgumentException] {
      Scheme("1 3")
    }
    assert(exception.getMessage === "requirement failed: scheme must match [a-zA-Z][a-zA-Z0-9+\\-\\.]*")
  }

  property("Scheme with same name equal") {
    val scheme1 = Scheme("myscheme")
    val scheme2 = Scheme("myscheme")
    assert(scheme1 === scheme2)
    assert(scheme2 === scheme1)
  }

  property("Scheme with different name not equal") {
    val scheme1 = Scheme("myschemea")
    val scheme2 = Scheme("myschemeb")
    assert(scheme1 != scheme2)
    assert(scheme2 != scheme1)
  }

  property("Default ports") {
    assert(Scheme.HTTP.defaultPort === Some(Port(80)))
    assert(Scheme("http").defaultPort === Some(Port(80)))
  }

  property("Register creates new scheme") {
    val newScheme = Scheme("newscheme", Port(8090))
    assert(newScheme.name === "newscheme")
    assert(newScheme.defaultPort === Some(Port(8090)))
    val retrievedNewScheme = Scheme("newscheme")
    assert(retrievedNewScheme === newScheme)
    assert(retrievedNewScheme.defaultPort === newScheme.defaultPort)
  }

  property("Register returns existing scheme") {
    val existingScheme = Scheme("http", Port(8090))
    assert(existingScheme.name === "http")
    assert(existingScheme.defaultPort === Some(Port(80)))

    val retrievedNewScheme = Scheme("http")
    assert(retrievedNewScheme === existingScheme)
    assert(retrievedNewScheme.defaultPort === existingScheme.defaultPort)
  }
}
