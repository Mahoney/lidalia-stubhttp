package uk.org.lidalia.example.server.application

import uk.org.lidalia.lang.Reusable

case class Application(
  config: ApplicationConfig
) extends Reusable {

  var running = false

  private [application] def start(): Unit = {
    running = true
    println(s"Application started: $this")
  }

  private [application] def stop(): Unit = {
    running = false
    println(s"Application stopped: $this")
  }
}
