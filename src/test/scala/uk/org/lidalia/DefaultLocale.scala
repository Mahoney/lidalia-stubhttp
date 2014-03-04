package uk.org.lidalia

import org.scalatest.{AbstractSuite, Suite}
import org.joda.time.{DateTimeUtils, Instant}
import java.util.Locale

trait DefaultLocale extends AbstractSuite {
  self : Suite =>

  val defaultLocale: Locale
  override abstract def withFixture(test : NoArgTest) {
    val oldDefault = Locale.getDefault
    Locale.setDefault(defaultLocale)
    try {
      DefaultLocale.super.withFixture(test)
    } finally {
      Locale.setDefault(oldDefault)
    }
  }
}
