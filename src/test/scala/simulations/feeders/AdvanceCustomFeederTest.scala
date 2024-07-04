package simulations.feeders

import io.gatling.core.Predef._
import io.gatling.core.feeder.Feeder
import io.gatling.core.scenario.Simulation
import io.gatling.core.structure.{ChainBuilder, ScenarioBuilder}
import io.gatling.http.Predef._
import io.gatling.http.protocol.HttpProtocolBuilder
import scala.util.Random

class AdvanceCustomFeederTest extends Simulation {

  val token: String = "Bearer b852c5fd133aa4a95e3e5cc40f257dcd2303cd7a8a25211b7e1da9f9d44c684a"

  val httpProtocol: HttpProtocolBuilder = http.baseUrl("https://gorest.co.in/public/v2")

  val random = new Random()

  def randomString(length: Int): String = {
    random.alphanumeric.filter(_.isLetter).take(length).mkString
  }

  //  Use below methods incase your applications needs to feed some number or Date
  //
  //  def randomNumber(length: Int): Int = {
  //    random.nextInt(math.pow(10, length).toInt)
  //  }
  //
  //  def randomDate(): String = {
  //    LocalDate.now().minusDays(random.nextInt(30)).format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
  //  }

  val customFeeder: Iterator[Map[String, _]] = Iterator.continually(Map(
    "name" -> s"${randomString(7)}",
    "email" -> s"${randomString(5)}@gmail.com",
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
        .body(StringBody(session =>
          s"""{
             |    "name": "${session("name").as[String]}",
             |    "email": "${session("email").as[String]}",
             |    "gender": "${session("gender").as[String]}",
             |    "status": "${session("status").as[String]}"
             |  }""".stripMargin)).asJson
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
