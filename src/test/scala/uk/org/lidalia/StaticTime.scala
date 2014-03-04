package uk.org.lidalia

import org.scalatest.{AbstractSuite, Suite}
import org.joda.time.{DateTimeUtils, Instant}

trait StaticTime extends AbstractSuite {
  self : Suite =>

  val staticTime: Instant
  override abstract def withFixture(test : NoArgTest) {
    DateTimeUtils.setCurrentMillisFixed(staticTime.getMillis)
    try {
      StaticTime.super.withFixture(test)
    } finally {
      DateTimeUtils.setCurrentMillisSystem()
    }
  }
}
