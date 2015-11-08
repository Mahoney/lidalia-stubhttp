package uk.org.lidalia.stubhttp

import com.github.tomakehurst.wiremock.common.Slf4jNotifier
import com.github.tomakehurst.wiremock.core.{Options, WireMockConfiguration}
import uk.org.lidalia.lang.ResourceFactory

object StubHttpServerFactory {
  def apply(
    config: Options = WireMockConfiguration.wireMockConfig()
      .dynamicPort()
      .notifier(new Slf4jNotifier(true))
  ): StubHttpServerFactory = new StubHttpServerFactory(config)
}
class StubHttpServerFactory private (
  config: Options = WireMockConfiguration.wireMockConfig()
    .dynamicPort()
    .notifier(new Slf4jNotifier(true))
) extends ResourceFactory[StubHttpServer] {

  override def withA[T](work: (StubHttpServer) => T): T = {

    val stubHttpServer = new StubHttpServer(config)

    try {
      stubHttpServer.start()
      work(stubHttpServer)
    } finally {
      stubHttpServer.stop()
    }
  }
}
