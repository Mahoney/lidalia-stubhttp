package uk.org.lidalia.lang

import org.scalatest.FunSuite



class PercentEncodedStringTest extends FunSuite {

  test("can encode all characters") {
    
    val toEncode = "\n\t"+"""aA0%-\/[]{}()^$!@£#$&*!~`|?<>,."""

    assert(AllCharsPercentEncodedStringFactory.encode(toEncode).toString === "%0a%09%61%41%30%25%2d%5c%2f%5b%5d%7b%7d%28%29%5e%24%21%40%a3%23%24%26%2a%21%7e%60%7c%3f%3c%3e%2c%2e")

  }
  
  test("standard encoded string checks") {
    EncodedStringChecks.checks(AllCharsPercentEncodedStringFactory)
  }

}

object AllCharsPercentEncodedStringFactory extends PercentEncodedStringFactoryAid[AllCharsPercentEncodedString](Set()) {
  override def apply(encoded: String) = new AllCharsPercentEncodedString(encoded)
}

class AllCharsPercentEncodedString(encoded: String) extends PercentEncodedStringAid[AllCharsPercentEncodedString](AllCharsPercentEncodedStringFactory, encoded)
