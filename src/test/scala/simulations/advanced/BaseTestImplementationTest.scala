package simulations.advanced

import io.gatling.core.Predef._
import io.gatling.core.scenario.Simulation
import io.gatling.core.structure.{ChainBuilder, ScenarioBuilder}
import io.gatling.http.Predef._
import io.gatling.http.protocol.HttpProtocolBuilder
import simulations.utils.BaseTest

class BaseTestImplementationTest extends Simulation with BaseTest {

  val httpProtocol: HttpProtocolBuilder = http.baseUrl("https://reqres.in")

  val scn1: ScenarioBuilder = scenario("Scenario 1")
    .exec(getSingleUser(2, 200))
    .pause(1)
    .exec(getSingleUser(3, 200, true, 2))
    .pause(1)
    .exec(createUser("data/requestBodyData/createUser.json", 201))
    .pause(1)

  val scn2: ScenarioBuilder = scenario("Scenario 2")
    .exec(updateUser(2, "data/requestBodyData/updateUser.json", 200))
    .pause(1)
    .exec(updateUser(3, "data/requestBodyData/updateUser.json", 200, true, 4))
    .pause(1)
    .exec(deleteUser(2, 204, true, 2))
    .pause(1)

  setUp(
    scn1.inject(atOnceUsers(1)),
    scn2.inject(atOnceUsers(1))
  )
    .protocols(httpProtocol)
}
