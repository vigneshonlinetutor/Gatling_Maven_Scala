package simulations.parameters

import io.gatling.core.Predef._
import io.gatling.core.scenario.Simulation
import io.gatling.core.structure.ScenarioBuilder
import io.gatling.http.Predef._
import io.gatling.http.protocol.HttpProtocolBuilder

class RunTimeParametersTest extends Simulation {

  val httpProtocol: HttpProtocolBuilder = http.baseUrl("https://reqres.in")

  def userMin: Int = System.getProperty("userMin", "10").toInt

  def userMax: Int = System.getProperty("userMax", "20").toInt

  def duration: Int = System.getProperty("duration", "20").toInt

  before {
    println(s"User count Minimum = ${userMin}")
    println(s"User count Maximum = ${userMax}")
    println(s"Duration = ${duration}")
  }


  val scn: ScenarioBuilder = scenario("Get Single User Scenario")
    .exec(
      http("GET API for fetching Single User")
        .get("/api/users/2")
    )

  setUp(scn.inject(
    rampConcurrentUsers(userMin).to(userMax).during(duration)
  ))
    .protocols(httpProtocol)
}
