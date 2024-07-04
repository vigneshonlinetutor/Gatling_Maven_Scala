package simulations.advanced

import io.gatling.core.Predef._
import io.gatling.core.scenario.Simulation
import io.gatling.core.structure.ScenarioBuilder
import io.gatling.http.Predef._
import io.gatling.http.protocol.HttpProtocolBuilder

class LoggingMechanismTest extends Simulation {

  val httpProtocol: HttpProtocolBuilder = http.baseUrl("https://reqres.in")

  val getAllUserScenario: ScenarioBuilder = scenario("Get All User Scenario")
    .exec(
      http("GET API for All User")
        .get("/api/users?page=2")
        .check(
          status is 200,
          jsonPath("$.data[1].id").saveAs("userId"),
          jsonPath("$.data[1].first_name").saveAs("firstName"),
          jsonPath("$.data[1].last_name").saveAs("lastName"),
          // SAVE THE ENTIRE RESPONSE BODY
          bodyString.saveAs("responseBody")
        )
    )
  //    .exec {
  //      session => println(session); session
  //    }

  //    .exec {
  //      session => println(session("responseBody").as[String]); session
  //    }

  setUp(
    getAllUserScenario.inject(atOnceUsers(1)))
    .protocols(httpProtocol)
}
