package io.ddm.myfinatra.controllers

import java.time.Instant

import com.twitter.finagle.http.Request
import com.twitter.finatra.http.Controller

import scala.collection.mutable

/**
  * Created by white on 16/09/16.
  */

case class Weight(
                   user: String,
                   weight: Int,
                   status: Option[String],
                   postedAt: Instant = Instant.now()
                 )


case class Stat(
               index: String,
               data: Int
               )

class StatsController extends Controller {

  val db = mutable.ListBuffer[Stat]()
  db += Stat(index = "index1", data = 1)
  db += Stat(index = "index2", data = 2)
  db += Stat(index = "index3", data = 3)
  db += Stat(index = "index4", data = 4)
  db += Stat(index = "index5", data = 5)

  get("/stats") { request: Request =>
    db
  }
}

class WeightResource extends Controller {

  val db = mutable.Map[String, List[Weight]]()

  get("/weights") { request: Request =>
    info("finding all weights for all users...")
    db
  }

  get("/weights/:user") { request: Request =>
    info( s"""finding weight for user ${request.params("user")}""")
    db.getOrElse(request.params("user"), List())
  }

  post("/weights") { weight: Weight =>
    val r = time(s"Total time take to post weight for user '${weight.user}' is %d ms") {
      val weightsForUser = db.get(weight.user) match {
        case Some(weights) => weights :+ weight
        case None => List(weight)
      }
      db.put(weight.user, weightsForUser)
      response.created.location(s"/weights/${weight.user}")
    }
    r
  }
}
