package uk.org.lidalia.example.test.functional

import uk.org.lidalia.example.local.EnvironmentDefinition

object PooledTest {

  def main(args: Array[String]) {

    EnvironmentDefinition.withA { environment =>
      // do whatever here
    }
  }
}
