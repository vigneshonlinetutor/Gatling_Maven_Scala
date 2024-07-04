package simulations.utils

import io.gatling.core.Predef._
import io.gatling.core.structure.ChainBuilder
import io.gatling.http.Predef._
import io.gatling.http.request.builder.HttpRequestBuilder

trait BaseTest {

  def getSingleUser(userId: Int, statusCode: Int, needRepeating: Boolean = false, repeaterCount: Int = 0): ChainBuilder = {
    val request = http("GET API for fetching Single User")
      .get(s"/api/users/$userId")
      .check(status.is(statusCode))
    buildRequest(request, needRepeating, repeaterCount)
  }

  def createUser(requestBodyFileName: String, statusCode: Int, needRepeating: Boolean = false, repeaterCount: Int = 0): ChainBuilder = {
    val request = http("Post API to Create user")
      .post("/api/users")
      .body(RawFileBody(requestBodyFileName)).asJson
      .check(status.is(statusCode))
    buildRequest(request, needRepeating, repeaterCount)
  }

  def updateUser(userId: Int, requestBodyFileName: String, statusCode: Int, needRepeating: Boolean = false, repeaterCount: Int = 0): ChainBuilder = {
    val request = http("Put Api Update User")
      .put(s"/api/users/$userId")
      .body(RawFileBody(requestBodyFileName)).asJson
      .check(status.is(statusCode))
    buildRequest(request, needRepeating, repeaterCount)
  }

  def deleteUser(userId: Int, statusCode: Int, needRepeating: Boolean = false, repeaterCount: Int = 0): ChainBuilder = {
    val request = http("Delete Api")
      .delete(s"/api/users/$userId")
      .check(status.is(statusCode))
    buildRequest(request, needRepeating, repeaterCount)
  }

  private def buildRequest(request: HttpRequestBuilder, needRepeating: Boolean = false, repeaterCount: Int = 0): ChainBuilder = {
    if (needRepeating) {
      repeat(repeaterCount, "repeaterIndex") {
        exec(request)
      }
    }
    else {
      exec(request)
    }
  }
}
