package uk.org.lidalia.example.test.functional

import java.time.Instant

import uk.org.lidalia.example.test.support.EnvironmentFactory

object PooledTest {
  def main(args: Array[String]) {
    println(Instant.now())
    new EnvironmentFactory().withA { environment =>
      // do whatever here
    }
    println(Instant.now())
  }
}
