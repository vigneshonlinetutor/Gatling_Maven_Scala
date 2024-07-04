package simulations.advanced

import io.gatling.core.Predef._
import io.gatling.core.scenario.Simulation
import io.gatling.core.structure.{ChainBuilder, ScenarioBuilder}
import io.gatling.http.Predef._
import io.gatling.http.protocol.HttpProtocolBuilder

class ReusableMethodsTest extends Simulation {

  val httpProtocol: HttpProtocolBuilder = http.baseUrl("https://reqres.in")

  def getSingleUser(): ChainBuilder = {
    exec(
      http("GE(T API for fetching Single User")
        .get("/api/users/2")
    )
  }

  def createUser(): ChainBuilder = {
    exec(
      http("Post API to Create user")
        .post("/api/users")
        .body(RawFileBody("data/requestBodyData/createUser.json")).asJson
    )
  }

  def updateUser(): ChainBuilder = {
    exec(
      http("Put Api Update User")
        .put("/api/users/2")
        .body(RawFileBody("data/requestBodyData/updateUser.json")).asJson
    )
  }

  def deleteUser(): ChainBuilder = {
    exec(
      http("Delete Api")
        .delete("/api/users/2")
    )
  }

  val scn1: ScenarioBuilder = scenario("Scenario 1")
    .exec(getSingleUser())
    .exec(getSingleUser())
    .exec(createUser())

  val scn2: ScenarioBuilder = scenario("Scenario 2")
    .exec(updateUser())
    .exec(updateUser())
    .exec(deleteUser())

  setUp(
    scn1.inject(atOnceUsers(1)),
    scn2.inject(atOnceUsers(1))
  )
    .protocols(httpProtocol)
}
