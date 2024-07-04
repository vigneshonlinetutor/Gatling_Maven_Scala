package simulations.apiTest

import io.gatling.core.scenario.Simulation
import io.gatling.core.Predef._
import io.gatling.core.structure.ScenarioBuilder
import io.gatling.http.Predef._
import io.gatling.http.protocol.HttpProtocolBuilder

class DeleteApiTest extends Simulation {

  val httpProtocol: HttpProtocolBuilder = http.baseUrl("https://reqres.in")

  val scn: ScenarioBuilder = scenario("Delete User Scenario")
    .exec(
      http("Delete Api")
        .delete("/api/users/2")
    )

  setUp(scn.inject(atOnceUsers(10)))
    .protocols(httpProtocol)

}
