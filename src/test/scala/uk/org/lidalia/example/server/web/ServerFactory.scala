package uk.org.lidalia.example.server.web

import uk.org.lidalia.example.server.application.ApplicationFactory
import uk.org.lidalia.example.system.awaitInterruption
import uk.org.lidalia.lang.ResourceFactory

class ServerFactory(
  config: ServerConfig
) extends ResourceFactory[Server] {

  val applicationFactory = new ApplicationFactory(
    config.applicationConfig
  )

  def runUntilInterrupted(): Unit = {
    withA(awaitInterruption)
  }

  override def withA[T](work: (Server) => T): T = {
    applicationFactory.withA { application =>
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
