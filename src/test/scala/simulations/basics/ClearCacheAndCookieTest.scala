package simulations.basics

import io.gatling.core.Predef._
import io.gatling.core.scenario.Simulation
import io.gatling.core.structure.ScenarioBuilder
import io.gatling.http.Predef._
import io.gatling.http.protocol.HttpProtocolBuilder

class ClearCacheAndCookieTest extends Simulation {

  val httpProtocol: HttpProtocolBuilder = http.baseUrl("https://reqres.in")

  val getAllUserScenario: ScenarioBuilder = scenario("Get All User Scenario")
    .exec(flushHttpCache)
    .exec(flushCookieJar)
    .exec(
      http("GET API for All User")
        .get("/api/users?page=2")
        .check(
          status is 200,
          jsonPath("$.data[1].id").saveAs("userId"),
          jsonPath("$.data[1].first_name").saveAs("firstName"),
          jsonPath("$.data[1].last_name").saveAs("lastName")
        )
    )
    .pause(2)
    .exec(flushHttpCache)
    .exec(flushCookieJar)
    .exec(
      http("GET API for fetching Single User")
        .get("/api/users/#{userId}")
        .check(
          status is 200,
          jsonPath("$.data.id").is("#{userId}"),
          jsonPath("$.data.first_name").is("#{firstName}"),
          jsonPath("$.data.last_name").is("#{lastName}")
        )
    )

  setUp(
    getAllUserScenario.inject(atOnceUsers(10)))
    .protocols(httpProtocol)
}
