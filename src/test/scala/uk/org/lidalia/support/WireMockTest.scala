package uk.org.lidalia.support

import com.github.tomakehurst.wiremock.WireMockServer
import com.github.tomakehurst.wiremock.common.Slf4jNotifier
import com.github.tomakehurst.wiremock.core.{WireMockConfiguration, Options}
import org.scalatest._

trait WireMockTest extends FunSuiteLike{

  def testWithDeps(testName: String, testTags: Tag*)(testFun: (WireMockServer) => Unit) {
    test(testName, testTags:_*) {
      val wireMock = new WireMockServer(WireMockConfiguration.wireMockConfig().port(0).notifier(new Slf4jNotifier(true)))
      wireMock.start()
      try {
        testFun(wireMock)
      } finally {
        wireMock.stop()
      }
    }
  }
}
