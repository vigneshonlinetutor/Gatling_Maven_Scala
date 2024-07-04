package simulations.injection

import io.gatling.core.Predef._
import io.gatling.core.scenario.Simulation
import io.gatling.core.structure.{ChainBuilder, ScenarioBuilder}
import io.gatling.http.Predef._
import io.gatling.http.protocol.HttpProtocolBuilder

class OpenInjectionWithStressPeakUser extends Simulation {

  val httpProtocol: HttpProtocolBuilder = http.baseUrl("https://reqres.in")

  val scn: ScenarioBuilder = scenario("Get Single User Scenario")
    .exec(
      http("GET API for fetching Single User")
        .get("/api/users/2")
    )

  setUp(scn.inject(stressPeakUsers(50).during(20)))
    .protocols(httpProtocol)
}
