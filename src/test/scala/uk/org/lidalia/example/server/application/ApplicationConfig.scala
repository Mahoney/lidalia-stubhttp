package uk.org.lidalia.example.server.application

import uk.org.lidalia.net2.Url

case class ApplicationConfig (
  sendGridUrl: Url,
  contentfulUrl: Url
)
