package simulations.apiTest

import io.gatling.core.scenario.Simulation
import io.gatling.core.Predef._
import io.gatling.core.structure.ScenarioBuilder
import io.gatling.http.Predef._
import io.gatling.http.protocol.HttpProtocolBuilder

class PostApiTest extends Simulation {

  val httpProtocol: HttpProtocolBuilder = http.baseUrl("https://reqres.in")

  val scn: ScenarioBuilder = scenario("Create User Scenario")
    .exec(
      http("Post API to Create user")
        .post("/api/users")
        .body(RawFileBody("data/requestBodyData/createUser.json")).asJson
    )

  setUp(scn.inject(atOnceUsers(10)))
    .protocols(httpProtocol)
}
