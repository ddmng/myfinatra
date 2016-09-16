package io.ddm.myfinatra

/**
  * Created by white on 16/09/16.
  */
import com.twitter.finagle.http.Status
import com.twitter.finatra.http.test.EmbeddedHttpServer
import com.twitter.inject.server.FeatureTest
import io.ddm.myfinatra.controllers.Weight

class StatsControllerFeatureTest extends FeatureTest {
  override val server: EmbeddedHttpServer = new EmbeddedHttpServer(
    twitterServer = new MyFinatraServer)

  "Say Hello" in {
    server.httpGet(
      path = "/stats",
      andExpect = Status.Ok,
      withBody = "Fitman says hello"
    )
  }
}

class WeightResourceFeatureTest extends FeatureTest {
  override val server = new EmbeddedHttpServer(
    twitterServer = new MyFinatraServer
  )

  "WeightResource" should {
    "Save user weight when POST request is made" in {
      server.httpPost(
        path = "/weights",
        postBody =
          """
            |{
            |"user":"test_user_1",
            |"weight":80,
            |"status":"Feeling great!!!"
            |}
          """.stripMargin,
        andExpect = Status.Created,
        withLocation = "/weights/test_user_1"
      )
    }
  }

  "List all weights for a user when GET request is made" in {
    val response = server.httpPost(
      path = "/weights",
      postBody =
        """
          |{
          |"user":"test_user_2",
          |"weight":80,
          |"posted_at" : "2016-01-03T14:34:06.871Z"
          |}
        """.stripMargin,
      andExpect = Status.Created
    )

    server.httpGetJson[List[Weight]](
      path = response.location.get,
      andExpect = Status.Ok,
      withJsonBody =
        """
          |[
          |  {
          |    "user" : "test_user_2",
          |    "weight" : 80,
          |    "posted_at" : "2016-01-03T14:34:06.871Z"
          |  }
          |]
        """.stripMargin
    )
  }

  "Bad request when user is not present in request" in {
    server.httpPost(
      path = "/weights",
      postBody =
        """
          |{
          |"weight":85
          |}
        """.stripMargin,
      andExpect = Status.BadRequest
    )
  }
}