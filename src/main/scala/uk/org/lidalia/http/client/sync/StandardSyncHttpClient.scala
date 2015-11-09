package uk.org.lidalia.http.client

import org.joda.time.Duration

object StandardSyncHttpClient extends ConvenientMultiTargetHttpClient(
  MultiTargetHttpClient((url) => new SyncHttpClient(
    new ExpectedEntityHttpClient(
      new Apache4Client(url.baseUrl)
    ),
    Duration.standardSeconds(5)
  ))
)
