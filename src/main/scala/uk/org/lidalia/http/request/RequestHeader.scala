package uk.org.lidalia.http.request

import uk.org.lidalia.http._
import uk.org.lidalia.http.headerfields.HeaderField

object RequestHeader {
    def apply(headerFields: List[HeaderField]): RequestHeader = new RequestHeader(headerFields)
    def apply(headerFields: HeaderField*): MessageHeader = apply(headerFields.to[List])
}

class RequestHeader private(@Identity private val headerFieldsList: List[HeaderField]) extends MessageHeader(headerFieldsList) {

}

//class Decorated {
//
//
//  def addAll(xs: Iterable[String]): Unit = xs.foreach(add)
//
//  def add(x: String): Unit = {}
//
//  def something = "Something"
//}
//
//class Decorator(decorated: Decorated) extends Decorated {
//
//  var added = 0
//
//  override def addAll(xs: Iterable[String]): Unit = {
//    added += xs.size
//    super.addAll(xs)
//  }
//
//  override def add(x: String): Unit = {
//    added += 1
//    super.add(x)
//  }
//
//  def somethingElse = "Something else"
//}
//
//object App {
//  def main(args: Array[String]): Unit = {
//    val value  = new Decorated with Decorator
//    value.addAll(List("hi", "world"))
//    assert(value.something == "Something")
//    assert(value.somethingElse == "Something else")
//    assert(value.added == 2)
//  }
//}
