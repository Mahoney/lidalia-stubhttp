package uk.org.lidalia.example.server.application

import uk.org.lidalia.example.system.HasLogger
import uk.org.lidalia.lang.Reusable

case class Application(
  config: ApplicationConfig
) extends Reusable with HasLogger {

  var running = false

  private [application] def start(): Unit = {
    running = true
    log.info(s"Application started: $this")
  }

  private [application] def stop(): Unit = {
    running = false
    log.info(s"Application stopped: $this")
  }
}
