package simulations.feeders

import io.gatling.core.Predef._
import io.gatling.core.feeder.Feeder
import io.gatling.core.scenario.Simulation
import io.gatling.core.structure.{ChainBuilder, ScenarioBuilder}
import io.gatling.http.Predef._
import io.gatling.http.protocol.HttpProtocolBuilder

class IndexSeqFeederTest extends Simulation {

  val token: String = "Bearer b852c5fd133aa4a95e3e5cc40f257dcd2303cd7a8a25211b7e1da9f9d44c684a"

  val httpProtocol: HttpProtocolBuilder = http.baseUrl("https://gorest.co.in/public/v2")

  val indexSeqFeeder: Feeder[Any] = IndexedSeq(
    Map("id" -> 6392586, "name" -> "Leela Shukla"),
    Map("id" -> 6392579, "name" -> "Hiranmay Malik"),
    Map("id" -> 6392583, "name" -> "Nanda Pillai")
  ).circular()

  def getSingleStudentDetail(): ChainBuilder = {
    repeat(3) {
      feed(indexSeqFeeder)
        .exec(
          http("get single student details of #{id} of the user name #{name}")
            .get("/users/#{id}")
            .header("Authorization", token)
            .check(
              status.is(200),
              jsonPath("$.id").is("#{id}"),
              jsonPath("$.name").is("#{name}")
            )
        )
    }
  }

  val scn: ScenarioBuilder = scenario("Index Sequence Feeder Test")
    .exec(getSingleStudentDetail())

  setUp(scn.inject(atOnceUsers(1)))
    .protocols(httpProtocol)

}
