package simulations.advanced

import io.gatling.core.Predef._
import io.gatling.core.scenario.Simulation
import io.gatling.core.structure.{ChainBuilder, ScenarioBuilder}
import io.gatling.http.Predef._
import io.gatling.http.protocol.HttpProtocolBuilder

class AuthenticationTest extends Simulation {

  val token: String = "Bearer b852c5fd133aa4a95e3e5cc40f257dcd2303cd7a8a25211b7e1da9f9d44c684a"

  val httpProtocol: HttpProtocolBuilder = http.baseUrl("https://gorest.co.in/public/v2")

  def getAllStudentDetails(): ChainBuilder = {
    exec(
      http("get all student details")
        .get("/users")
        .header("Authorization", token)
        .check(
          status.is(200),
          jsonPath("$[0].id").saveAs("studentId"),
          jsonPath("$[0].name").saveAs("studentName")
        )
    )
  }

  def getSingleStudentDetail(): ChainBuilder = {
    exec(
      http("get single student details")
        .get("/users/#{studentId}")
        .header("Authorization", token)
        .check(
          status.is(200),
          jsonPath("$.id").is("#{studentId}"),
          jsonPath("$.name").is("#{studentName}")
        )
    )
  }

  val scn: ScenarioBuilder = scenario("Get All Students Details")
    .exec(getAllStudentDetails())
    .pause(2)
    .exec(getSingleStudentDetail())

  setUp(scn.inject(atOnceUsers(1)))
    .protocols(httpProtocol)

}
