package uk.org.lidalia.stubhttp

import com.github.tomakehurst.wiremock.WireMockServer
import com.github.tomakehurst.wiremock.client.MappingBuilder
import com.github.tomakehurst.wiremock.core.Options
import org.slf4j.LoggerFactory
import uk.org.lidalia.net2.Url

class StubHttpServer private [stubhttp] (config: Options) {

  private val log = LoggerFactory.getLogger(classOf[StubHttpServer])

  private val wiremockServer = new WireMockServer(config)

  lazy val port = wiremockServer.port()

  lazy val httpsPort = wiremockServer.httpsPort()

  lazy val localAddress = Url(s"http://localhost:${wiremockServer.port()}")

  lazy val localHttpsAddress = Url(s"https://localhost:${wiremockServer.httpsPort()}")

  def stub(mappingBuilder: MappingBuilder): Unit = {
    wiremockServer.stubFor(mappingBuilder)
  }

  private [stubhttp] def start(): Unit = {
    wiremockServer.start()
    log.info(s"Stub HTTP Server started on port $port")
  }

  private [stubhttp] def stop(): Unit = {
    wiremockServer.stop()
    log.info("Stub HTTP Server stopped")
  }
}
