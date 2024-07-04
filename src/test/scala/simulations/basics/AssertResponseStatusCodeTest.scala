package simulations.basics

import io.gatling.core.Predef._
import io.gatling.core.scenario.Simulation
import io.gatling.core.structure.ScenarioBuilder
import io.gatling.http.Predef._
import io.gatling.http.protocol.HttpProtocolBuilder

class AssertResponseStatusCodeTest extends Simulation {

  val httpProtocol: HttpProtocolBuilder = http.baseUrl("https://reqres.in")

  val getScenario: ScenarioBuilder = scenario("Get Single User Scenario")
    .exec(
      http("GET API for fetching Single User")
        .get("/api/users/2")
        .check(status is 200)
    )

  val postScenario: ScenarioBuilder = scenario("Create User Scenario")
    .exec(
      http("Post API to Create user")
        .post("/api/users")
        .body(RawFileBody("data/requestBodyData/createUser.json")).asJson
        .check(status.in(200 to 205))
    )

  val putScenario: ScenarioBuilder = scenario("Update User scenario")
    .exec(
      http("Put Api Update User")
        .put("/api/users/2")
        .body(RawFileBody("data/requestBodyData/updateUser.json")).asJson
        .check(status.not(404))
    )

  val deleteScenario: ScenarioBuilder = scenario("Delete User Scenario")
    .exec(
      http("Delete Api")
        .delete("/api/users/2")
        .check(status is 204)
    )

  setUp(
    getScenario.inject(atOnceUsers(10)),
    postScenario.inject(atOnceUsers(10)),
    putScenario.inject(atOnceUsers(10)),
    deleteScenario.inject(atOnceUsers(10))
  )
    .protocols(httpProtocol)
}
