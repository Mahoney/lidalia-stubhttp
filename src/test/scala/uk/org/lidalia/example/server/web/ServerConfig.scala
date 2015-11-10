package uk.org.lidalia.example.server.web

import uk.org.lidalia.example.server.application.ApplicationConfig

case class ServerConfig(
  applicationConfig: ApplicationConfig,
  webConfig: WebConfig
)
