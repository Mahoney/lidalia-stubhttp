package uk.org.lidalia.stubhttp

import com.github.tomakehurst.wiremock
import uk.org.lidalia.http.core.Response
import wiremock.client.{ WireMock, UrlMatchingStrategy, ValueMatchingStrategy, RemoteMappingBuilder, ScenarioMappingBuilder }
import wiremock.http.RequestMethod
import wiremock.http.RequestMethod.GET

object DSL {

  def get(
    pathAndQuery: String,
    headers: (String, String)*
  ): ReqPatternBuilder = {
    new ReqPatternBuilder(
      GET,
      WireMock.urlEqualTo(pathAndQuery),
      Map(headers.map(entry => (entry._1, WireMock.equalTo(entry._2))):_*)
    )
  }
}

class ReqPatternBuilder(
  method: RequestMethod,
  urlMatchingStrategy: UrlMatchingStrategy,
  headers: Map[String, ValueMatchingStrategy]
) {

  def returns[T <: RemoteMappingBuilder[T, ScenarioMappingBuilder]](response: Response[_]): T = {

    val result: T = WireMock.request(method.toString, urlMatchingStrategy).asInstanceOf[T]

    headers.foreach { header =>
      result.withHeader(header._1, header._2)
    }

    val responseDef = WireMock.aResponse()

    responseDef.withStatus(response.code.code)

    response.headerFields.foreach { header =>
      header.values.foreach { value =>
        responseDef.withHeader(header.name, value)
      }
    }

    responseDef.withBody(response.entityBytes.toSignedArray)

    result.willReturn(responseDef)
  }
}
