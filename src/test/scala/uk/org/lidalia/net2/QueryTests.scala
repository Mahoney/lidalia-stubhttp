package uk.org.lidalia.net2

import org.scalatest.PropSpec
import org.scalatest.prop.PropertyChecks
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalacheck.Gen
import java.lang.IllegalArgumentException

@RunWith(classOf[JUnitRunner])
class QueryTests
    extends PropSpec
    with PropertyChecks {

  val unreserved =
    ('a' to 'z').toSet ++ ('A' to 'Z') ++ ('0' to '9') ++ Set('-', '.', '_', '~')

  val subDelims = Set(
    '!',
    '$',
    '&',
    '\'',
    '(',
    ')',
    '*',
    '+',
    ',',
    ';',
    '=')

  val pchar = unreserved ++ subDelims ++ Set(':', '@')

  val additionalQueryChars = Set('/',
    '?',
    '/')

  val queryChars: Set[Char] = pchar ++ additionalQueryChars

  val pctEncoded = for { num <- Gen.choose(0, 255) } yield "%"+String.format("%02X", num.asInstanceOf[Object])

  val otherThanPctEncoded = for (n <- Gen.someOf(queryChars)) yield n.mkString

  val genQueryStringElement: Gen[String] = Gen.oneOf(pctEncoded, otherThanPctEncoded)

  def genNode(level: Int): Gen[String] = for {
    start <- Gen.lzy(genQueryStrings(level))
    end <- Gen.lzy(genQueryStrings(level))
  } yield start + end

  def genQueryStrings(level: Int = 0): Gen[String] = {
    if (level >= 100) genQueryStringElement
    else Gen.oneOf(genQueryStringElement, genNode(level + 1))
  }

  property("Valid query strings accepted") {
    forAll((genQueryStrings(), "query string")) { (queryString) =>
      assert(Query(queryString).toString === queryString)
    }
  }

  property("Query string can be empty") {
    assert(Query("").toString === "")
  }

  property("Query string cannot be null") {
    val exception = intercept[IllegalArgumentException] {
      Query(null)
    }
  }

//  property("Valid query strings rejected") {
//
//    forAll("query string") { queryString: String =>
//      val exception = intercept[IllegalArgumentException] {
//        Query(queryString)
//      }
//    }
//  }
}
