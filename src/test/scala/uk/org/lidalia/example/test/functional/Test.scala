package uk.org.lidalia.example.test.functional

import java.time.Instant

import uk.org.lidalia.example.test.support.EnvironmentFactory
import uk.org.lidalia.slf4jext.Level
import uk.org.lidalia.slf4jtest.TestLoggerFactory

object Test {

  TestLoggerFactory.getInstance().setPrintLevel(Level.INFO)

  def main(args: Array[String]) {

    new EnvironmentFactory().withA { environment =>
      println(s"Running a test against server on port ${environment.server.localPort} $environment")
    }
  }
}
