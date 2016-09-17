package io.ddm.myfinatra

/**
  * Created by white on 16/09/16.
  */


import com.twitter.finagle.http.{Request, Response}
import com.twitter.finatra.http.HttpServer
import com.twitter.finatra.http.filters.CommonFilters
import com.twitter.finatra.http.routing.HttpRouter
import com.twitter.finatra.logging.filter.{LoggingMDCFilter, TraceIdMDCFilter}
import io.ddm.myfinatra.controllers.StatsController

object MyFinatraMain extends MyFinatraServer


class MyFinatraServer extends HttpServer {
  override protected def defaultFinatraHttpPort: String = ":8888"
  override protected def defaultTracingEnabled: Boolean = false
  override protected def defaultHttpServerName: String = "MyFitMan"

  override protected def configureHttp(router: HttpRouter): Unit = {
    router
      .filter[LoggingMDCFilter[Request, Response]]
      .filter[TraceIdMDCFilter[Request, Response]]
      .filter[CommonFilters]
      .add[StatsController]

  }

}

