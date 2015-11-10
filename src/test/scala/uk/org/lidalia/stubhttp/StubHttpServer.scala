package uk.org.lidalia.stubhttp

import com.github.tomakehurst.wiremock.WireMockServer
import com.github.tomakehurst.wiremock.client.MappingBuilder
import com.github.tomakehurst.wiremock.core.Options
import org.slf4j.LoggerFactory
import uk.org.lidalia.net2.Url

class StubHttpServer private [stubhttp] (config: Options) {

  private val log = LoggerFactory.getLogger(classOf[StubHttpServer])

  private val wireMockServer = new WireMockServer(config)

  lazy val port = wireMockServer.port()

  lazy val httpsPort = wireMockServer.httpsPort()

  lazy val localAddress = Url(s"http://localhost:${wireMockServer.port()}")

  lazy val localHttpsAddress = Url(s"https://localhost:${wireMockServer.httpsPort()}")

  def stub(mappingBuilder: MappingBuilder): Unit = {
    wireMockServer.stubFor(mappingBuilder)
  }

  private [stubhttp] def start(): Unit = {
    wireMockServer.start()
    log.info(s"Stub HTTP Server started on port $port")
  }

  private [stubhttp] def stop(): Unit = {
    wireMockServer.stop()
    log.info("Stub HTTP Server stopped")
  }

  override def toString = s"${getClass.getSimpleName}:$port"
}
