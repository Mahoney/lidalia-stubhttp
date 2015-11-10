package uk.org.lidalia.example.server.web

import uk.org.lidalia.example.server.application.{ApplicationConfig, ApplicationDefinition}
import uk.org.lidalia.example.system.awaitInterruption
import uk.org.lidalia.lang.ResourceFactory
import uk.org.lidalia.net2.{Port, Url}

import scala.collection.immutable

object ServerDefinition {

  def main(args: Array[String]) {

    val config = configFor(
      args.toVector,
      System.getProperties.toMap,
      System.getenv().toMap
    )

    new ServerDefinition(config).runUntilInterrupted()
  }

  def configFor(
    args: immutable.Seq[String],
    sysProps: Map[String, String],
    env: Map[String, String]
  ): ServerConfig = {

    new ServerConfig (

      new ApplicationConfig(
        sendGridUrl = Url("http://www.example.com"),
        contentfulUrl = Url("http://www.disney.com")
      ),

      new WebConfig(
        localPort = Port(80)
      )
    )
  }
}

class ServerDefinition(
  config: ServerConfig
) extends ResourceFactory[Server] {

  val applicationDefinition = new ApplicationDefinition(
    config.applicationConfig
  )

  def runUntilInterrupted(): Unit = {
    withA(awaitInterruption)
  }

  override def withA[T](work: (Server) => T): T = {

    applicationDefinition.withA { application =>

      val server = new Server(application, config.webConfig)

      try {
        server.start()
        work(server)
      } finally {
        server.stop()
      }
    }
  }
}
