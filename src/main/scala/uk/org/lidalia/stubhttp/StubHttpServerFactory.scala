package uk.org.lidalia.stubhttp

import com.github.tomakehurst.wiremock.common.Slf4jNotifier
import com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig
import com.github.tomakehurst.wiremock.core.Options
import uk.org.lidalia.net.Port
import uk.org.lidalia.scalalang.ResourceFactory

object StubHttpServerFactory {

  def apply(port: Port): StubHttpServerFactory = {
    apply(
      wireMockConfig()
        .port(port.portNumber)
        .notifier(new Slf4jNotifier(true))
    )
  }

  def apply(
    config: Options = wireMockConfig()
      .dynamicPort()
      .notifier(new Slf4jNotifier(true))
  ): StubHttpServerFactory = new StubHttpServerFactory(config)
}

class StubHttpServerFactory private (
  config: Options
) extends ResourceFactory[StubHttpServer] {

  override def using[T](work: (StubHttpServer) => T): T = {

    val stubHttpServer = new StubHttpServer(config)

    try {
      stubHttpServer.start()
      work(stubHttpServer)
    } finally {
      stubHttpServer.stop()
    }
  }
}
