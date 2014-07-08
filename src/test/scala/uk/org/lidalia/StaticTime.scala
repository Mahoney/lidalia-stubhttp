package uk.org.lidalia

import org.scalatest.{Outcome, Suite}
import org.joda.time.{DateTimeUtils, Instant}

trait StaticTime extends Suite {

  val staticTime: Instant

  override abstract def withFixture(test : NoArgTest): Outcome = {
    DateTimeUtils.setCurrentMillisFixed(staticTime.getMillis)
    try {
      super.withFixture(test)
    } finally {
      DateTimeUtils.setCurrentMillisSystem()
    }
  }
}
