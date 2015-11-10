package uk.org.lidalia.example.server

import application.ApplicationConfig
import uk.org.lidalia.net2.{Port, Url}
import web.{ServerFactory, ServerConfig, WebConfig}

import scala.collection.JavaConversions.mapAsScalaMap
import scala.collection.JavaConversions.propertiesAsScalaMap

import scala.collection.immutable

object Main {

  def main(args: Array[String]) {

    val config = configFor(
      args.toVector,
      System.getProperties.toMap,
      System.getenv().toMap
    )

    new ServerFactory(config).runUntilInterrupted()
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
