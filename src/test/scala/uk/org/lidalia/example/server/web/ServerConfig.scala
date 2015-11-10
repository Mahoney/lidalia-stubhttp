package uk.org.lidalia.example.server.web

import uk.org.lidalia.example.server.application.ApplicationConfig
import uk.org.lidalia.net2.Port

case class ServerConfig(
  applicationConfig: ApplicationConfig,
  localPort: Option[Port]
)
