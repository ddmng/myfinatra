/**
  * Created by white on 16/09/16.
  */

import com.twitter.finatra.http.HttpServer

object MyFinatraApp extends MyFinatra

class MyFinatra extends HttpServer {
  override protected def configureHttp(router: HttpRouter): Unit = {
    router.add(new HelloController)
  }

}

class HelloController extends Controller {

  get("/hello") { request: Request =>
    "Fitman says hello"
  }

}