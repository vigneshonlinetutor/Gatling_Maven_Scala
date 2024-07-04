package simulations.apiTest

import io.gatling.core.scenario.Simulation
import io.gatling.core.Predef._
import io.gatling.core.structure.ScenarioBuilder
import io.gatling.http.Predef._
import io.gatling.http.protocol.HttpProtocolBuilder

class PutApiTest extends Simulation {

  val httpProtocol: HttpProtocolBuilder = http.baseUrl("https://reqres.in")

  val scn: ScenarioBuilder = scenario("Update User scenario")
    .exec(
      http("Put Api Update User")
        .put("/api/users/2")
        .body(RawFileBody("data/requestBodyData/updateUser.json")).asJson
    )

  setUp(scn.inject(atOnceUsers(10)))
    .protocols(httpProtocol)
}
