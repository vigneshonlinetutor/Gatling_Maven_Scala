package simulations.feeders

import com.github.javafaker.Faker
import io.gatling.core.Predef._
import io.gatling.core.scenario.Simulation
import io.gatling.core.structure.{ChainBuilder, ScenarioBuilder}
import io.gatling.http.Predef._
import io.gatling.http.protocol.HttpProtocolBuilder


class AdvanceCustomFeederTestWithTemplateJson extends Simulation {

  val token: String = "Bearer b852c5fd133aa4a95e3e5cc40f257dcd2303cd7a8a25211b7e1da9f9d44c684a"

  val httpProtocol: HttpProtocolBuilder = http.baseUrl("https://gorest.co.in/public/v2")

  val faker = new Faker()

  val customFeeder: Iterator[Map[String, _]] = Iterator.continually(Map(
    "name" -> s"${faker.name().fullName()}",
    "email" -> s"${faker.internet().emailAddress()}",
    "gender" -> "male",
    "status" -> "active"
  ))

  def createSingleStudent(): ChainBuilder = {
    exec(
      http("Post API to create Student")
        .post("/users")
        .header("Authorization", token)
        .header("accept", "application/json")
        .header("content-type", "application/json")
        .body(ElFileBody("data/requestTemplate/newStudentRequestBodyTemplate.json")).asJson
        .check(
          status.is(201),
          bodyString.saveAs("responseBody")
        )
    )
      .exec { session => println(session("responseBody").as[String]); session }

  }

  val scn: ScenarioBuilder = scenario("Advanced Custom Feeder Test")
    .feed(customFeeder)
    .exec(createSingleStudent())

  setUp(scn.inject(atOnceUsers(5)))
    .protocols(httpProtocol)
}
