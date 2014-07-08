package uk.org.lidalia

import org.scalatest.{Outcome, AbstractSuite, Suite}
import org.joda.time.{DateTimeUtils, Instant}
import java.util.Locale

trait DefaultLocale extends Suite {

  val defaultLocale: Locale

  override abstract def withFixture(test : NoArgTest): Outcome = {
    val oldDefault = Locale.getDefault
    Locale.setDefault(defaultLocale)
    try {
      super.withFixture(test)
    } finally {
      Locale.setDefault(oldDefault)
    }
  }
}
