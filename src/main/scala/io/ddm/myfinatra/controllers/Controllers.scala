package io.ddm.myfinatra.controllers

import java.time.Instant

import com.twitter.finagle.http.Request
import com.twitter.finatra.http.Controller

import scala.collection.mutable

/**
  * Created by white on 16/09/16.
  */

case class Stat(
               index: String,
               data: Int
               )

class StatsController extends Controller {

  val db = mutable.ListBuffer[Stat]()
  db += Stat(index = "index1", data = 1)

  get("/stats") { request: Request =>
    db
  }

  // Serve static files
  get("/") { request: Request =>
    response.ok.file("index.html")
  }
}

