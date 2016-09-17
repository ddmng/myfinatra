package io.ddm.myfinatra

/**
  * Created by white on 16/09/16.
  */
import com.twitter.finagle.http.Status
import com.twitter.finatra.http.test.EmbeddedHttpServer
import com.twitter.inject.server.FeatureTest
import io.ddm.myfinatra.controllers.Stat

class StatsControllerFeatureTest extends FeatureTest {
  override val server: EmbeddedHttpServer = new EmbeddedHttpServer(
    twitterServer = new MyFinatraServer)

  "Get all statistics" in {
    server.httpGet(
      path = "/stats",
      andExpect = Status.Ok,
      withBody = "[{\"index\":\"index1\",\"data\":1}]"
    )
  }
}

