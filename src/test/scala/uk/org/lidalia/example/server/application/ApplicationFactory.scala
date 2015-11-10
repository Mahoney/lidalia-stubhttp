package uk.org.lidalia.example.server.application

import uk.org.lidalia.lang.ResourceFactory

class ApplicationFactory(
  config: ApplicationConfig
) extends ResourceFactory[Application] {

  override def withA[T](work: (Application) => T): T = {
    val application = new Application(config)
    try {
      application.start()
      work(application)
    } finally {
      application.stop()
    }
  }
}
