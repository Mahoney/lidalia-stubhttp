package uk.org.lidalia.stubhttp

import com.github.tomakehurst.wiremock
import uk.org.lidalia.http.core.Response
import wiremock.client.{MappingBuilder, ResponseDefinitionBuilder, UrlMatchingStrategy, ValueMatchingStrategy, WireMock}
import wiremock.http.RequestMethod
import wiremock.http.RequestMethod.GET

object DSL {

  def get(
    pathAndQuery: String,
    headers: (String, String)*
  ): RequestPatternBuilder = {
    new RequestPatternBuilder(
      GET,
      WireMock.urlEqualTo(pathAndQuery),
      Map(headers.map(entry => (entry._1, WireMock.equalTo(entry._2))):_*)
    )
  }
}

class RequestPatternBuilder(
  method: RequestMethod,
  urlMatchingStrategy: UrlMatchingStrategy,
  headers: Map[String, ValueMatchingStrategy]
) {

  def returns(response: Response[_]): MappingBuilder = {

    val result = new MappingBuilder(method, urlMatchingStrategy)

    headers.foreach { header =>
      result.withHeader(header._1, header._2)
    }

    val responseDef: ResponseDefinitionBuilder = WireMock.aResponse()

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
