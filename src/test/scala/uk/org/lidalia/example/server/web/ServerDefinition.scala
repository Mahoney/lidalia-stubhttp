package uk.org.lidalia.example.server.web

import uk.org.lidalia.example.server.application.ApplicationDefinition
import uk.org.lidalia.example.system.awaitInterruption
import uk.org.lidalia.lang.ResourceFactory

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
