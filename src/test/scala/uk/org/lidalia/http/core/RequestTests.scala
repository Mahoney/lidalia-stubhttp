package uk.org.lidalia.http.core

import java.io.{ByteArrayInputStream, InputStream}

import org.apache.commons.io.IOUtils
import org.scalatest.FunSuite
import uk.org.lidalia.http.client.{Accept}
import uk.org.lidalia.http.core.Method.{PUT, GET, HEAD}
import uk.org.lidalia.lang.UnsignedByte
import scala.collection.immutable.Seq

class RequestTests extends FunSuite {
//
//  val contentTypeString = new ContentType[String](MediaType.text_plain) {
//    override def marshal(message: Message[String]) = {
//      new ByteArrayInputStream(message.entity.getBytes)
//    }
//  }
//
//  val acceptString = new Accept[String](Seq(new MediaRangePref(MediaType.text_plain))) {
//    override def unmarshal(
//      request: Request,
//      response: ResponseHeader,
//      entityBytes: InputStream
//    ): String = {
//      IOUtils.toString(inputStream)
//    }
//  }
//
//  test("construct PUT request with no accept and an entity") {
//
//    val putRequest: Request[Seq[UnsignedByte], String] = Request(
//      PUT, RequestUri("/path"),
//      contentTypeString,
//      Nil,
//      "entity"
//    )
//
//    assert(putRequest.entity == "entity")
//    assert(putRequest.toString == "")
//  }
//
//  test("construct PUT request with accept and an entity") {
//
//    val putRequest: Request[String, String] = Request(
//      PUT, RequestUri("/path"),
//      acceptString,
//      contentTypeString,
//      Nil,
//      "entity"
//    )
//
//    assert(putRequest.entity == "entity")
//    assert(putRequest.toString == "")
//  }
//
//  test("construct GET request with no accept and no entity") {
//
//    val request: Request[Seq[UnsignedByte], None.type] = Request(
//      GET, RequestUri("/path")
//    )
//
//    assert(request.entity == None)
//    assert(request.toString == "")
//  }
//
//  test("construct GET request with accept") {
//
//    val request: Request[String, None.type] = Request(
//      GET, RequestUri("/path"),
//      acceptString
//    )
//
//    assert(request.entity == None)
//    assert(request.toString == "")
//  }
//
//  test("construct HEAD request") {
//
//    val request: Request[None.type, None.type] = Request(
//      HEAD, RequestUri("/path")
//    )
//
//    assert(request.entity == None)
//    assert(request.toString == "")
//  }
//
//  test("construct HEAD request with just accept") {
//
//    val request: Request[None.type, None.type] = Request(
//      HEAD, RequestUri("/path"),
//      acceptString
//    )
//
//    assert(request.entity == None)
//    assert(request.toString == "")
//  }
//
//  test("construct HEAD request with just accept") {
//
//    val request: Request[None.type, None.type] = Request(
//      HEAD, RequestUri("/path"),
//      acceptString
//    )
//
//    assert(request.entity == None)
//    assert(request.toString == "")
//  }
//
//  test("construct HEAD request with just contentType") {
//
//    val request: Request[None.type, None.type] = Request(
//      HEAD, RequestUri("/path"),
//      contentTypeString
//    )
//
//    assert(request.entity == None)
//    assert(request.toString == "")
//  }
//
//  test("construct HEAD request with accept and contentType") {
//
//    val request: Request[None.type, None.type] = Request(
//      HEAD, RequestUri("/path"),
//      acceptString,
//      contentTypeString
//    )
//
//    assert(request.entity == None)
//    assert(request.toString == "")
//  }
//
//  test("construct unknown request with no accept or contentType") {
//
//    val request: Request[Seq[UnsignedByte], Seq[UnsignedByte]] = Request(
//      Method("GET"), RequestUri("/path")
//    )
//
//    assert(request.entity == Seq())
//    assert(request.toString == "")
//  }
//
//  test("construct unknown request with just accept") {
//
//    val request: Request[String, Seq[UnsignedByte]] = Request(
//      Method("GET"), RequestUri("/path"),
//      acceptString
//    )
//
//    assert(request.entity == Seq())
//    assert(request.toString == "")
//  }
//
//  test("construct unknown request with just contentType") {
//
//    val request: Request[Seq[UnsignedByte], String] = Request(
//      Method("GET"), RequestUri("/path"),
//      contentTypeString
//    )
//
//    assert(request.entity == Seq())
//    assert(request.toString == "")
//  }
//
//  test("construct unknown request with accept and contentType") {
//
//    val request: Request[String, String] = Request(
//      Method("GET"), RequestUri("/path"),
//      acceptString,
//      contentTypeString
//    )
//
//    assert(request.entity == Seq())
//    assert(request.toString == "")
//  }
//
//  test("construct request from string") {
//
//    val request: Request[Seq[UnsignedByte], String] = Request(
//      """PUT /path HTTP/1.1
//        |Accept: text/plain
//        |Content-Type: text/plain
//        |
//        |entity
//      """.stripMargin
//    )
//
//    assert(request.entity == "entity")
//    assert(request.toString == """PUT /path HTTP/1.1
//                                 |Accept: text/plain
//                                 |Content-Type: text/plain
//                                 |
//                                 |entity
//                               """.stripMargin)
//  }
}
