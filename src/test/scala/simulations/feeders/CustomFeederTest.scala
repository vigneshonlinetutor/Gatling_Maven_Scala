package simulations.feeders

import io.gatling.core.Predef._
import io.gatling.core.feeder.Feeder
import io.gatling.core.scenario.Simulation
import io.gatling.core.structure.{ChainBuilder, ScenarioBuilder}
import io.gatling.http.Predef._
import io.gatling.http.protocol.HttpProtocolBuilder

class CustomFeederTest extends Simulation {

  val token: String = "Bearer b852c5fd133aa4a95e3e5cc40f257dcd2303cd7a8a25211b7e1da9f9d44c684a"

  val httpProtocol: HttpProtocolBuilder = http.baseUrl("https://gorest.co.in/public/v2")

  val idNumbers: Iterator[Int] = (6392566 to 6392569).iterator

  val customFeeder: Iterator[Map[String, Int]] = Iterator.continually(Map("id" -> idNumbers.next()))

  def getSingleStudentDetail(): ChainBuilder = {
    repeat(3) {
      feed(customFeeder)
        .exec(
          http("get single student details of #{id}")
            .get("/users/#{id}")
            .header("Authorization", token)
            .check(
              status.is(200),
              jsonPath("$.id").is("#{id}"),
            )
        )
    }
  }

  val scn: ScenarioBuilder = scenario("Custom Feeder Test")
    .exec(getSingleStudentDetail())

  setUp(scn.inject(atOnceUsers(1)))
    .protocols(httpProtocol)

}
