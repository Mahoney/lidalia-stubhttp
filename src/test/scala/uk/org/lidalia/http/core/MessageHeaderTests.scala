package uk.org.lidalia.http.core

import org.scalatest
import scalatest.PropSpec
import scalatest.prop.TableDrivenPropertyChecks

class MessageHeaderTests extends PropSpec with TableDrivenPropertyChecks {

  property("correctly parses multiple headerfields with the same name") {
    val headerField1 = HeaderField("Name1", "value1")
    val headerField2 = HeaderField("Name2", "value4")
    val headerField3 = HeaderField("Name1", "value2", "value3")
    val message = new MessageHeader(List(headerField1, headerField2, headerField3)) {}

    assert(message.headerField("Name1") === Some(HeaderField("Name1", "value1", "value2", "value3")))
    assert(message.headerFieldValues("Name1") === List("value1", "value2", "value3"))
  }

  property("maintains list of headerfields") {
    val headerField1 = HeaderField("Name1", "value1")
    val headerField2 = HeaderField("Name2", "value4")
    val headerField3 = HeaderField("Name1", "value2", "value3")
    val message = new MessageHeader(List(headerField1, headerField2, headerField3)) {}

    assert(message.headerFields === List(headerField1, headerField2, headerField3))
  }
}
