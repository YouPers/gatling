package com.youpers.load

import com.excilys.ebi.gatling.core.Predef._
import com.excilys.ebi.gatling.http.Predef._
import com.excilys.ebi.gatling.jdbc.Predef._
import com.excilys.ebi.gatling.http.Headers.Names._
import akka.util.duration._
import bootstrap._
import assertions._

class CompleteUserScenario extends Simulation {

  val userFeeder = new Feeder[String] {

    import org.joda.time.DateTime
    import scala.util.Random

    private val RNG = new Random

    // random number in between [a...b]
    private def randInt(a: Int, b: Int) = RNG.nextInt(b - a) + a

    // always return true as this feeder can be polled infinitively
    override def hasNext = true


    override def next: Map[String, String] = {

      val user = "loadtest" + scala.math.abs(java.util.UUID.randomUUID.getMostSignificantBits)
      val password = user
      val email = "ypunittest1+"+user+"@gmail.com"

      Map("username" -> user,
        "password" -> password,
        "email" -> email,
        "fullname" -> "fullname",
        "firstname" -> "firstname",
        "lastname" -> "lastname",
        "campaign" -> "5399862b82428d0000e6e5c9"
      )
    }
  }


  val httpConf = httpConfig
    .baseURL("https://uat.youpers.com/api")
    .acceptHeader("application/json, text/plain, */*")
    .acceptEncodingHeader("gzip, deflate")
    .acceptLanguageHeader("en-gb,en;q=0.5")
    .connection("keep-alive")
    .userAgentHeader("Mozilla/5.0 (Macintosh; Intel Mac OS X 10.9; rv:29.0) Gecko/20100101 Firefox/29.0")


  val headers_1 = Map(
    "Cache-Control" -> """no-cache""",
    "Content-Type" -> """application/json;charset=utf-8""",
    "Origin" -> """http://localhost:9000""",
    "Pragma" -> """no-cache""",
    "yp-language" -> """de"""
  )

  val headers_23 = Map(
    "Origin" -> """http://localhost:9000""",
    "yp-language" -> """de"""
  )

  val headers_33 = Map(
    "Content-Type" -> """application/json;charset=utf-8""",
    "Origin" -> """http://localhost:9000""",
    "yp-language" -> """de"""
  )

  val headers_158 = Map(
    "Content-Type" -> """text/plain; charset=UTF-8""",
    "Origin" -> """http://localhost:9000""",
    "yp-language" -> """de"""
  )


  val chain_signUp =

    exec(http("user validation")
      .post("//users/validate")
      .headers(headers_1)
      .body( """{ "email": "${email}" }""").asJSON
    )

      .pause(1)
      .exec(http("signup")
      .post("/users")
      .headers(headers_1)
      .body(
        """{
          |"fullname": "${fullname}",
          |"firstname": "${firstname}",
          |"lastname": "${lastname}",
          |"email": "${email}",
          |"username": "${username}",
          |"password": "${password}",
          |"campaign": "${campaign}"
          |}""".stripMargin).asJSON
      )
      .pause(63 milliseconds)
      .exec(http("signin")
      .post("/login")
      .headers(headers_1)
      .body( """{ "user": "${username}" }""").asJSON
      .basicAuth( """${username}""", """${password}""")
      .check(jsonPath("$.id").saveAs("userId"))
      .check(jsonPath("$.profile.id").saveAs("profileId"))
      )


  val chain_assessment =

    exec(http("coachmessages signup")
      .get("//coachmessages")
      .headers(headers_23)
      .queryParam( """uistate""", """signup.content""")
      .basicAuth( """${username}""", """${password}""")
    )
      .exec(http("coachmessages home")
      .get("//coachmessages")
      .headers(headers_23)
      .queryParam( """uistate""", """home.content""")
      .basicAuth( """${username}""", """${password}""")
      )
      .exec(http("notifications")
      .get("/notifications")
      .headers(headers_23)
      .queryParam( """sort""", """created:-1""")
      .queryParam( """populate""", """author""")
      .basicAuth( """${username}""", """${password}""")
      )
      .pause(18)
      .exec(http("activityoffers")
      .get("/activityoffers")
      .headers(headers_23)
      .basicAuth( """${username}""", """${password}""")
      )
      .pause(88 milliseconds)
      .exec(http("coachmessages select")
      .get("//coachmessages")
      .headers(headers_23)
      .queryParam( """uistate""", """select.content""")
      .basicAuth( """${username}""", """${password}""")
      )
      .exec(http("notifications")
      .get("/notifications")
      .headers(headers_23)
      .queryParam( """sort""", """created:-1""")
      .queryParam( """populate""", """author""")
      .basicAuth( """${username}""", """${password}""")
      )

      .pause(2)
      .exec(http("assessment result newest")
      .get("/assessments/525faf0ac558d40000000005/results/newest")
      .headers(headers_23)
      .basicAuth( """${username}""", """${password}""")
      .check(jsonPath("$").saveAs("assessmentResult"))
      )
      .pause(27 milliseconds)
      .exec(http("assessment")
      .get("/assessments/525faf0ac558d40000000005")
      .headers(headers_23)
      .queryParam( """populate""", """questions""")
      .basicAuth( """${username}""", """${password}""")
      )
      .pause(201 milliseconds)
      .exec(http("notifications")
      .get("/notifications")
      .headers(headers_23)
      .queryParam( """sort""", """created:-1""")
      .queryParam( """populate""", """author""")
      .basicAuth( """${username}""", """${password}""")
      )
      .exec(http("coachmessages check")
      .get("//coachmessages")
      .headers(headers_23)
      .queryParam( """uistate""", """check.content""")
      .basicAuth( """${username}""", """${password}""")
      )
      .pause(1)

      .repeat(26) {
        exec(http("assessment result answer")
          .put("/assessments/525faf0ac558d40000000005/answers/5278c51a6166f2de240000dd")
          .headers(headers_33)
          .fileBody("CompleteUserScenario_assessmentResultAnswer.json")
          .basicAuth( """${username}""", """${password}""")
        )
          .pause(1)
      }

      .exec(http("assessment result newest")
      .get("/assessments/525faf0ac558d40000000005/results/newest")
      .headers(headers_23)
      .basicAuth( """${username}""", """${password}""")
      )
      .pause(34 milliseconds)
      .exec(http("assessment result newest populate")
      .get("/assessments/525faf0ac558d40000000005/results/newest")
      .headers(headers_23)
      .queryParam( """populatedeep""", """answers.question""")
      .basicAuth( """${username}""", """${password}""")
      )


  val chain_focus =

    pause(118 milliseconds)
      .exec(http("notifications")
      .get("/notifications")
      .headers(headers_23)
      .queryParam( """sort""", """created:-1""")
      .queryParam( """populate""", """author""")
      .basicAuth( """${username}""", """${password}""")
      )
      .exec(http("coachmessages focus")
      .get("//coachmessages")
      .headers(headers_23)
      .queryParam( """uistate""", """focus.content""")
      .basicAuth( """${username}""", """${password}""")
      )

      .repeat(3) {

        pause(2)
          .exec(http("assessment result newest")
          .get("/assessments/525faf0ac558d40000000005/results/newest")
          .headers(headers_23)
          .basicAuth( """${username}""", """${password}""")

          .check(jsonPath("$.id").saveAs("assessmentResultId"))
          .check(jsonPath("$.created").saveAs("assessmentResultCreated"))
          )


          .exec(http("focus profile")
          .put("/profiles/${profileId}")
          .headers(headers_33)
          .fileBody("CompleteUserScenario_profile.json", Map(
            "userId" -> "${userId}",
            "profileId" -> "${profileId}"
        )).asJSON
          .basicAuth( """${username}""", """${password}""")
          )
          .pause(260 milliseconds)

      }



  	val chain_planActivityOffer =
  		pause(262 milliseconds)
  		.exec(http("activityOffer list")
  					.get("/activityoffers")
  					.headers(headers_23)
  					.basicAuth("""${username}""","""${password}""")
  			)
  		.pause(219 milliseconds)
  		.exec(http("coachmessages select")
  					.get("//coachmessages")
  					.headers(headers_23)
  					.queryParam("""uistate""", """select.content""")
  					.basicAuth("""${username}""","""${password}""")
  			)
  		.exec(http("notifications")
  					.get("/notifications")
  					.headers(headers_23)
  					.queryParam("""sort""", """created:-1""")
  					.queryParam("""populate""", """author""")
  					.basicAuth("""${username}""","""${password}""")
  			)
  		.pause(2)
  		.exec(http("activityOffer view")
  					.get("/activityoffers")
  					.headers(headers_23)
  					.queryParam("""activity""", """${activityId}""")
  					.basicAuth("""${username}""","""${password}""")
  			)
  		.pause(148 milliseconds)
  		.exec(http("notifications")
  					.get("/notifications")
  					.headers(headers_23)
  					.queryParam("""sort""", """created:-1""")
  					.queryParam("""populate""", """author""")
  					.basicAuth("""${username}""","""${password}""")
  			)
  		.exec(http("coachmessages schedule")
  					.get("//coachmessages")
  					.headers(headers_23)
  					.queryParam("""uistate""", """schedule.offer""")
  					.basicAuth("""${username}""","""${password}""")
  			)
      .repeat(4) {
        pause(1000 milliseconds)
          .exec(http("activityPlan conflicts")
          .post("/activityplans/conflicts")
          .headers(headers_1)
          .fileBody("CompleteUserScenario_activityOfferConflict.json")
          .basicAuth("""${username}""","""${password}""")
          )
      }

  		.pause(500 milliseconds)
  		.exec(http("activityPlan post")
  					.post("/activityplans")
  					.headers(headers_1)
  						.fileBody("CompleteUserScenario_activityPlanPost.json")
  					.basicAuth("""${username}""","""${password}""")
            .check(jsonPath("$.id").saveAs("activityPlanId"))
  			)
  		.pause(129 milliseconds)
  		.exec(http("activityPlan get")
  					.get("/activityplans/${activityPlanId}")
  					.headers(headers_23)
  					.queryParam("""populate""", """owner""")
  					.queryParam("""populate""", """invitedBy""")
  					.queryParam("""populate""", """joiningUsers""")
  					.queryParam("""populate""", """activity""")
  					.basicAuth("""${username}""","""${password}""")
  			)

  		.pause(9)
  		.exec(http("activityPlan conflicts")
  					.post("/activityplans/conflicts")
  					.headers(headers_1)
  						.fileBody("CompleteUserScenario_activityOfferConflict.json")
  					.basicAuth("""${username}""","""${password}""")
  			)
  		.pause(542 milliseconds)
  		.exec(http("activityPlan put")
  					.put("/activityplans/${activityPlanId}")
  					.headers(headers_33)
  						.fileBody("CompleteUserScenario_activityPlanPut.json", Map(
                "activityPlanId" -> "${activityPlanId}",
                "userId" -> "${userId}"
              ))
  					.basicAuth("""${username}""","""${password}""")
  			)
  		.pause(1)

  val chain_planMultipleActivityOffers = exec(

    exec(
      (session: Session) => {
        session.setAttribute("activityId", "5278c6adcdeab69a2500001e")
      }
    ),
    chain_planActivityOffer,
    exec(
      (session: Session) => {
        session.setAttribute("activityId", "5278c6adcdeab69a25000019")
      }
    ),
    chain_planActivityOffer,
    exec(
      (session: Session) => {
        session.setAttribute("activityId", "5278c6adcdeab69a25000027")
      }
    ),
    chain_planActivityOffer
    exec(
      (session: Session) => {
        session.setAttribute("activityId", "5278c6accdeab69a2500000d")
      }
    ),
    chain_planActivityOffer

  )

  //		.pause(10)
  //		.exec(http("request_165")
  //					.post("/activityplans/539854da499b2ec3fb0018c3//inviteEmail")
  //					.headers(headers_1)
  //						.fileBody("CompleteUserScenario_request_165.txt")
  //					.basicAuth("""${username}""","""${password}""")
  //			)
  //		.pause(4)
  //		.exec(http("request_166")
  //					.get("/activityplans/539854c2499b2ec3fb0018a6")
  //					.headers(headers_23)
  //					.queryParam("""populate""", """owner""")
  //					.queryParam("""populate""", """invitedBy""")
  //					.queryParam("""populate""", """joiningUsers""")
  //					.queryParam("""populate""", """activity""")
  //					.basicAuth("""${username}""","""${password}""")
  //			)
  //		.pause(316 milliseconds)
  //		.exec(http("request_167")
  //					.get("/notifications")
  //					.headers(headers_23)
  //					.queryParam("""sort""", """created:-1""")
  //					.queryParam("""populate""", """author""")
  //					.basicAuth("""${username}""","""${password}""")
  //			)
  //		.pause(31 milliseconds)
  //		.exec(http("request_168")
  //					.get("//coachmessages")
  //					.headers(headers_23)
  //					.queryParam("""uistate""", """schedule.plan""")
  //					.basicAuth("""${username}""","""${password}""")
  //			)
  //		.pause(765 milliseconds)
  //		.exec(http("request_169")
  //					.post("/activityplans/conflicts")
  //					.headers(headers_1)
  //						.fileBody("CompleteUserScenario_request_169.txt")
  //					.basicAuth("""${username}""","""${password}""")
  //			)
  //		.pause(5)
  //		.exec(http("request_170")
  //					.put("/activityplans/539854c2499b2ec3fb0018a6")
  //					.headers(headers_33)
  //						.fileBody("CompleteUserScenario_request_170.txt")
  //					.basicAuth("""${username}""","""${password}""")
  //			)
  //		.exec(http("request_171")
  //					.post("/activityplans/conflicts")
  //					.headers(headers_1)
  //						.fileBody("CompleteUserScenario_request_171.txt")
  //					.basicAuth("""${username}""","""${password}""")
  //			)
  //		.pause(1)
  //		.exec(http("request_172")
  //					.get("/activityplans")
  //					.headers(headers_23)
  //					.queryParam("""filter[status]""", """active""")
  //					.queryParam("""populate""", """owner""")
  //					.queryParam("""populate""", """invitedBy""")
  //					.queryParam("""populate""", """joiningUsers""")
  //					.queryParam("""populate""", """activity""")
  //					.basicAuth("""${username}""","""${password}""")
  //			)
  //		.pause(201 milliseconds)
  //		.exec(http("request_173")
  //					.get("//coachmessages")
  //					.headers(headers_23)
  //					.queryParam("""uistate""", """plan.offer""")
  //					.basicAuth("""${username}""","""${password}""")
  //			)
  //		.exec(http("request_174")
  //					.get("/notifications")
  //					.headers(headers_23)
  //					.queryParam("""sort""", """created:-1""")
  //					.queryParam("""populate""", """author""")
  //					.basicAuth("""${username}""","""${password}""")
  //			)
  //		.pause(1)
  //		.exec(http("request_175")
  //					.get("/diaryentries")
  //					.headers(headers_23)
  //					.queryParam("""populate""", """activityPlan""")
  //					.basicAuth("""${username}""","""${password}""")
  //			)
  //		.pause(93 milliseconds)
  //		.exec(http("request_176")
  //					.get("//coachmessages")
  //					.headers(headers_23)
  //					.queryParam("""uistate""", """diary.content""")
  //					.basicAuth("""${username}""","""${password}""")
  //			)
  //		.exec(http("request_177")
  //					.get("/notifications")
  //					.headers(headers_23)
  //					.queryParam("""sort""", """created:-1""")
  //					.queryParam("""populate""", """author""")
  //					.basicAuth("""${username}""","""${password}""")
  //			)
  //		.pause(4)
  //		.exec(http("request_178")
  //					.post("/diaryentries")
  //					.headers(headers_1)
  //						.fileBody("CompleteUserScenario_request_178.txt")
  //					.basicAuth("""${username}""","""${password}""")
  //			)
  //		.pause(30 milliseconds)
  //		.exec(http("request_179")
  //					.put("/profiles/539853d7499b2ec3fb001809")
  //					.headers(headers_33)
  //						.fileBody("CompleteUserScenario_request_179.txt")
  //					.basicAuth("""${username}""","""${password}""")
  //			)

  val scn = scenario("Scenario Name")
    .feed(userFeeder)
    .exec(
      chain_signUp,
      chain_assessment,
      chain_focus,
      chain_planMultipleActivityOffers
    )

  setUp(scn.users(200).ramp(300).protocolConfig(httpConf))
}