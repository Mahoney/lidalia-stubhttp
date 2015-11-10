package uk.org.lidalia.example.server.application

import uk.org.lidalia.example.system.HasLogger
import uk.org.lidalia.lang.ResourceFactory

class ApplicationDefinition(
  config: ApplicationConfig
) extends ResourceFactory[Application] with HasLogger {

  override def withA[T](work: (Application) => T): T = {

    val application = new Application(config)

    try {
      log.info(s"Application started: $this")
      work(application)
    } finally {
      log.info(s"Application stopped: $this")
    }
  }
}
