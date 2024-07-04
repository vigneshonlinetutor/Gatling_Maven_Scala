package demo

import scala.concurrent.duration._

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._

class RecordedHARSimulation extends Simulation {

  private val httpProtocol = http
    .baseUrl("https://computer-database.gatling.io")
    .inferHtmlResources()
    .acceptHeader("text/css,*/*;q=0.1")
    .acceptEncodingHeader("gzip, deflate, br")
    .acceptLanguageHeader("en-US,en;q=0.9,te;q=0.8,ta;q=0.7")
    .userAgentHeader("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36")
  
  private val headers_0 = Map(
  		"accept" -> "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.7",
  		"sec-ch-ua" -> """Not_A Brand";v="8", "Chromium";v="120", "Google Chrome";v="120""",
  		"sec-ch-ua-mobile" -> "?0",
  		"sec-ch-ua-platform" -> "Windows",
  		"sec-fetch-dest" -> "document",
  		"sec-fetch-mode" -> "navigate",
  		"sec-fetch-site" -> "same-origin",
  		"sec-fetch-user" -> "?1",
  		"upgrade-insecure-requests" -> "1"
  )
  
  private val headers_1 = Map(
  		"sec-ch-ua" -> """Not_A Brand";v="8", "Chromium";v="120", "Google Chrome";v="120""",
  		"sec-ch-ua-mobile" -> "?0",
  		"sec-ch-ua-platform" -> "Windows",
  		"sec-fetch-dest" -> "style",
  		"sec-fetch-mode" -> "no-cors",
  		"sec-fetch-site" -> "same-origin"
  )
  
  private val headers_6 = Map(
  		"accept" -> "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.7",
  		"origin" -> "https://computer-database.gatling.io",
  		"sec-ch-ua" -> """Not_A Brand";v="8", "Chromium";v="120", "Google Chrome";v="120""",
  		"sec-ch-ua-mobile" -> "?0",
  		"sec-ch-ua-platform" -> "Windows",
  		"sec-fetch-dest" -> "document",
  		"sec-fetch-mode" -> "navigate",
  		"sec-fetch-site" -> "same-origin",
  		"sec-fetch-user" -> "?1",
  		"upgrade-insecure-requests" -> "1"
  )


  private val scn = scenario("RecordedHARSimulation")
    .exec(
      http("request_0")
        .get("/computers?f=hp")
        .headers(headers_0)
        .resources(
          http("request_1")
            .get("/assets/css/bootstrap.min.css")
            .headers(headers_1),
          http("request_2")
            .get("/assets/css/main.css")
            .headers(headers_1)
        ),
      pause(3),
      http("request_3")
        .get("/computers/289")
        .headers(headers_0)
        .resources(
          http("request_4")
            .get("/assets/css/bootstrap.min.css")
            .headers(headers_1),
          http("request_5")
            .get("/assets/css/main.css")
            .headers(headers_1)
        ),
      pause(4),
      http("request_6")
        .post("/computers/289")
        .headers(headers_6)
        .formParam("name", "HP 9000")
        .formParam("introduced", "")
        .formParam("discontinued", "")
        .formParam("company", "1")
    )

	setUp(scn.inject(atOnceUsers(1))).protocols(httpProtocol)
}
