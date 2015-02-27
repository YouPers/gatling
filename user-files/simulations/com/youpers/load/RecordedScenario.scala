package com.youpers.load 
import com.excilys.ebi.gatling.core.Predef._
import com.excilys.ebi.gatling.http.Predef._
import com.excilys.ebi.gatling.jdbc.Predef._
import com.excilys.ebi.gatling.http.Headers.Names._
import akka.util.duration._
import bootstrap._
import assertions._

class RecordedScenario extends Simulation {

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
        "campaign" -> "527916a82079aa8704000006"
      )
    }
  }

  val overridePause = true

	val httpConf = httpConfig
			.baseURL("http://localhost:8000")
			.acceptHeader("application/json, text/plain, */*")
			.acceptEncodingHeader("gzip, deflate")
			.acceptLanguageHeader("en-GB,en;q=0.5")
//			.authorizationHeader("Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiI1NGVmNTAyZGRkMWY0MzRkYjc3MjUwMTIiLCJleHAiOjE0MjU1NzQ1NzM4MTl9.0_BqhuOQ7PZfUjMJWjaRb4rTYHdUyi25UsXo3xnl4fY")
      .connection("keep-alive")
			.userAgentHeader("Mozilla/5.0 (Macintosh; Intel Mac OS X 10.9; rv:35.0) Gecko/20100101 Firefox/35.0")


	val headers_1 = Map(
			"Origin" -> """http://localhost:9000""",
			"yp-language" -> """de"""
	)

	val headers_2 = Map(
			"Cache-Control" -> """no-cache""",
			"Content-Type" -> """application/json;charset=utf-8""",
			"Origin" -> """http://localhost:9000""",
			"Pragma" -> """no-cache""",
			"yp-language" -> """de"""
	)

	val headers_11 = Map(
			"Authorization" -> """Basic dGVzdEB0ZXN0OnBhc3N3ZA==""",
			"Cache-Control" -> """no-cache""",
			"Content-Type" -> """application/json;charset=utf-8""",
			"Origin" -> """http://localhost:9000""",
			"Pragma" -> """no-cache""",
			"yp-language" -> """de"""
	)

	val headers_23 = Map(
			"Content-Type" -> """application/json;charset=utf-8""",
			"Origin" -> """http://localhost:9000""",
			"yp-language" -> """de"""
	)


	val chain_signup =
		exec(http("campaign GET")
					.get("/campaigns/527916a82079aa8704000006")
					.headers(headers_1)
					.queryParam("""populate""", """organization""")
					.queryParam("""populate""", """campaignLeads""")
					.queryParam("""populate""", """topic""")
			)
		.pause(if(overridePause) 0 else 10)
    .repeat(10) {
      exec(http("validate user")
        .post("//users/validate")
        .headers(headers_2)
        .body( """{ "email": "${email}" }""").asJSON
      )
      .pause(100 milliseconds)
    }

		.pause(if(overridePause) 0 else 6)
		.exec(http("user POST")
					.post("/users")
					.headers(headers_2)
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
		.pause(300 milliseconds)
		.exec(http("login POST")
					.post("/login")
					.headers(headers_11)
          .body( """{ "user": "${username}" }""").asJSON
          .basicAuth( """${username}""", """${password}""")
          .check(jsonPath("$.token").saveAs("token"))
          .check(jsonPath("$.user.id").saveAs("userId"))
          .check(jsonPath("$.user.profile.id").saveAs("profileId"))
			)
		.pause(200 milliseconds)

  val chain_homeResolves =
		exec(http("activities GET")
					.get("/activities")
					.headers(headers_1)
          .basicAuth( """${username}""", """${password}""")
			)
		.pause(50 milliseconds)
		.exec(http("activityevents GET")
					.get("/activityevents")
					.headers(headers_1)
      .basicAuth( """${username}""", """${password}""")
			)
    .pause(50 milliseconds)
		.exec(http("offers GET")
					.get("/offers")
					.headers(headers_1)
					.queryParam("""populate""", """author""")
					.queryParam("""populate""", """activity""")
      .basicAuth( """${username}""", """${password}""")
          .check(jsonPath("$[0].id").saveAs("socialInteractionId"))
			)
		.pause(50 milliseconds)
		.exec(http("campaign GET")
					.get("/campaigns/527916a82079aa8704000006")
					.headers(headers_1)
					.queryParam("""populate""", """organization""")
					.queryParam("""populate""", """campaignLeads""")
					.queryParam("""populate""", """topic""")
      .basicAuth( """${username}""", """${password}""")
			)
		.pause(50 milliseconds)
		.exec(http("ideas GET")
					.get("/ideas")
					.headers(headers_1)
					.queryParam("""campaign""", """527916a82079aa8704000006""")
					.queryParam("""filter[id]""", """5278c6accdeab69a25000008""")
      .basicAuth( """${username}""", """${password}""")
			)
		.pause(20 milliseconds)
		.exec(http("ideas GET")
					.get("/ideas")
					.headers(headers_1)
					.queryParam("""campaign""", """527916a82079aa8704000006""")
					.queryParam("""filter[id]""", """5278c6adcdeab69a2500007c""")
      .basicAuth( """${username}""", """${password}""")
			)
		.pause(20 milliseconds)
		.exec(http("ideas GET")
					.get("/ideas")
					.headers(headers_1)
					.queryParam("""campaign""", """527916a82079aa8704000006""")
					.queryParam("""filter[id]""", """5278c6adcdeab69a25000024""")
      .basicAuth( """${username}""", """${password}""")
			)
		.pause(150 milliseconds)
		.exec(http("socialInteractions GET")
					.get("/socialInteractions")
					.headers(headers_1)
					.queryParam("""limit""", """10""")
					.queryParam("""sort""", """publishFrom:-1""")
					.queryParam("""populate""", """author activity""")
					.queryParam("""filter[authorType]""", """!coach""")
      .basicAuth( """${username}""", """${password}""")
			)
		.pause(300 milliseconds)
		.exec(http("lookAheadCounters GET")
					.get("/activities/54ef37f0dd1f434db7724fcf/lookAheadCounters")
					.headers(headers_1)
      .basicAuth( """${username}""", """${password}""")
			)



		//start assessment
    val chain_assessment =

    pause(if(overridePause) 0 else 15)
		.exec(http("assessments GET")
					.get("/assessments")
					.headers(headers_1)
					.queryParam("""filter[topic]""", """53b416cfa43aac62a2debda1""")
					.queryParam("""populate""", """questions""")
      .basicAuth( """${username}""", """${password}""")
			)
		.pause(30 milliseconds)
		.exec(http("assessmentResults GET")
					.get("/assessments/525faf0ac558d40000000005/results/newest")
					.headers(headers_1)
      .basicAuth( """${username}""", """${password}""")
			)
		.pause(20 milliseconds)
		.exec(http("assessment activityevents GET")
					.get("/activityevents")
					.headers(headers_1)
					.queryParam("""filter[idea]""", """5278c6accdeab69a25000008""")
      .basicAuth( """${username}""", """${password}""")
			)

    .repeat(26) {
      pause(if(overridePause) 0 else 10)
      .exec(http("assessment answer PUT")
            .put("/assessments/525faf0ac558d40000000005/answers/5278c51a6166f2de240000dd")
            .headers(headers_23)
        .basicAuth( """${username}""", """${password}""")
            .fileBody("RecordedScenario_PUTassessmentAnswer.json")
        )
    }
		.pause(if(overridePause) 0 else 3)
		.exec(http("assessment activityevents GET")
					.get("/activityevents")
					.headers(headers_1)
					.queryParam("""filter[idea]""", """5278c6accdeab69a25000008""")
      .basicAuth( """${username}""", """${password}""")
          .check(jsonPath("$[0].id").saveAs("assessmentActivityEventId"))
          .check(jsonPath("$[0].activity").saveAs("assessmentActivityId"))
			)
		.pause(50 milliseconds)
		.exec(http("assessment activityevents PUT")
					.put("/activityevents/${assessmentActivityEventId}")
					.headers(headers_23)
          .basicAuth( """${username}""", """${password}""")
          .fileBody("RecordedScenario_PUTassessmentActivityEvent.json", Map(
            "userId" -> "${userId}",
            "assessmentActivityId" -> "${assessmentActivityId}",
            "assessmentActivityEventId" -> "${assessmentActivityEventId}"
          )).asJSON
			)
		.pause(100 milliseconds)
		.exec(http("coachRecommendations GET")
					.get("/coachRecommendations")
					.headers(headers_1)
      .basicAuth( """${username}""", """${password}""")
			)
		.pause(50 milliseconds)
		.exec(http("assessment result GET")
					.get("/assessments/525faf0ac558d40000000005/results/newest")
					.headers(headers_1)
      .basicAuth( """${username}""", """${password}""")
			)



		//start recommendation1
    val chain_recommendation1 =
		pause(if(overridePause) 0 else 9)
		.exec(http("socialInteractions GET")
					.get("/socialInteractions/${socialInteractionId}")
					.headers(headers_1)
					.queryParam("""populate""", """author""")
					.queryParam("""populate""", """activity""")
      .basicAuth( """${username}""", """${password}""")
			)
		.pause(50 milliseconds)
		.exec(http("defaultActivity GET")
					.get("/ideas/5278c6adcdeab69a25000024/defaultActivity")
					.headers(headers_1)
					.queryParam("""campaignId""", """527916a82079aa8704000006""")
      .basicAuth( """${username}""", """${password}""")
			)
    .repeat(5) {
      pause(if(overridePause) 0 else 1)
        .exec(http("activity validate POST")
          .post("/activities/validate")
          .headers(headers_2)
        .basicAuth( """${username}""", """${password}""")
          .fileBody("RecordedScenario_POSTactivitiesValidate.json", Map(
            "userId" -> "${userId}"
          )).asJSON
        )
    }

		.pause(if(overridePause) 0 else 3)
		.exec(http("activity POST")
					.post("/activities")
					.headers(headers_2)
      .basicAuth( """${username}""", """${password}""")
          .fileBody("RecordedScenario_POSTactivity.json", Map(
            "userId" -> "${userId}"
          )).asJSON
          .check(jsonPath("$.id").saveAs("recommendation1ActivityId"))
			)
		.pause(200 milliseconds)
		.exec(http("campaign GET")
					.get("/campaigns/527916a82079aa8704000006")
					.headers(headers_1)
      .basicAuth( """${username}""", """${password}""")
					.queryParam("""populate""", """organization""")
					.queryParam("""populate""", """campaignLeads""")
					.queryParam("""populate""", """topic""")
			)
		.exec(http("activity GET")
					.get("/activities/${recommendation1ActivityId}")
					.headers(headers_1)
      .basicAuth( """${username}""", """${password}""")
					.queryParam("""populate""", """idea""")
					.queryParam("""populate""", """owner""")
					.queryParam("""populate""", """joiningUsers""")
			)
		.pause(100 milliseconds)
		.exec(http("activity events GET")
					.get("/activityevents")
					.headers(headers_1)
      .basicAuth( """${username}""", """${password}""")
					.queryParam("""sort""", """start""")
					.queryParam("""filter[activity]""", """${recommendation1ActivityId}""")
			)
		.pause(500 milliseconds)
		.exec(http("activities validate")
					.post("/activities/validate")
					.headers(headers_2)
      .basicAuth( """${username}""", """${password}""")
          .fileBody("RecordedScenario_POSTactivitiesValidate2.json", Map(
            "userId" -> "${userId}",
            "recommendation1ActivityId" -> "${recommendation1ActivityId}"
          )).asJSON
			)
		.pause(100 milliseconds)
		.exec(http("messages GET")
					.get("/messages")
					.headers(headers_1)
      .basicAuth( """${username}""", """${password}""")
					.queryParam("""authored""", """true""")
					.queryParam("""populate""", """author""")
					.queryParam("""targetId""", """${recommendation1ActivityId}""")
					.queryParam("""dismissed""", """true""")
			)


//		//start recommendation 2
//		.pause(13)
//		.exec(http("request_53")
//					.get("/socialInteractions/54ef505fdd1f434db7725023")
//					.headers(headers_1)
//					.queryParam("""populate""", """author""")
//					.queryParam("""populate""", """activity""")
//			)
//		.pause(25 milliseconds)
//		.exec(http("request_54")
//					.get("/ideas/5278c6adcdeab69a25000028/defaultActivity")
//					.headers(headers_1)
//					.queryParam("""campaignId""", """527916a82079aa8704000006""")
//			)
//		.pause(686 milliseconds)
//		.exec(http("request_55")
//					.post("/activities/validate")
//					.headers(headers_2)
//						.fileBody("RecordedScenario_request_55.txt")
//			)
//		.pause(2)
//		.exec(http("request_56")
//					.post("/activities/validate")
//					.headers(headers_2)
//						.fileBody("RecordedScenario_request_56.txt")
//			)
//		.pause(810 milliseconds)
//		.exec(http("request_57")
//					.post("/activities/validate")
//					.headers(headers_2)
//						.fileBody("RecordedScenario_request_57.txt")
//			)
//		.pause(2)
//		.exec(http("request_58")
//					.post("/activities/validate")
//					.headers(headers_2)
//						.fileBody("RecordedScenario_request_58.txt")
//			)
//		.pause(3)
//		.exec(http("request_59")
//					.post("/activities")
//					.headers(headers_2)
//						.fileBody("RecordedScenario_request_59.txt")
//			)
//		.pause(86 milliseconds)
//		.exec(http("request_60")
//					.get("/campaigns/527916a82079aa8704000006")
//					.headers(headers_1)
//					.queryParam("""populate""", """organization""")
//					.queryParam("""populate""", """campaignLeads""")
//					.queryParam("""populate""", """topic""")
//			)
//		.exec(http("request_61")
//					.get("/activities/54ef5083dd1f434db7725026")
//					.headers(headers_1)
//					.queryParam("""populate""", """idea""")
//					.queryParam("""populate""", """owner""")
//					.queryParam("""populate""", """joiningUsers""")
//			)
//		.pause(54 milliseconds)
//		.exec(http("request_62")
//					.get("/activityevents")
//					.headers(headers_1)
//					.queryParam("""sort""", """start""")
//					.queryParam("""filter[activity]""", """54ef5083dd1f434db7725026""")
//			)
//		.pause(365 milliseconds)
//		.exec(http("request_63")
//					.post("/activities/validate")
//					.headers(headers_2)
//						.fileBody("RecordedScenario_request_63.txt")
//			)
//		.pause(15 milliseconds)
//		.exec(http("request_64")
//					.get("/messages")
//					.headers(headers_1)
//					.queryParam("""authored""", """true""")
//					.queryParam("""populate""", """author""")
//					.queryParam("""targetId""", """54ef5083dd1f434db7725026""")
//					.queryParam("""dismissed""", """true""")
//			)
//		//back to home
//		.pause(8)
//		.exec(http("request_65")
//					.get("/activities")
//					.headers(headers_1)
//			)
//		.exec(http("request_66")
//					.get("/activityevents")
//					.headers(headers_1)
//			)
//		.pause(15 milliseconds)
//		.exec(http("request_67")
//					.get("/offers")
//					.headers(headers_1)
//					.queryParam("""populate""", """author""")
//					.queryParam("""populate""", """activity""")
//			)
//		.pause(35 milliseconds)
//		.exec(http("request_68")
//					.get("/ideas")
//					.headers(headers_1)
//					.queryParam("""campaign""", """527916a82079aa8704000006""")
//					.queryParam("""filter[id]""", """5278c6adcdeab69a25000037""")
//			)
//		.pause(661 milliseconds)
//		.exec(http("request_69")
//					.get("/socialInteractions")
//					.headers(headers_1)
//					.queryParam("""limit""", """10""")
//					.queryParam("""sort""", """publishFrom:-1""")
//					.queryParam("""populate""", """author activity""")
//					.queryParam("""filter[authorType]""", """!coach""")
//			)
//		.exec(http("request_70")
//					.get("/activities/54ef37f0dd1f434db7724fcf/lookAheadCounters")
//					.headers(headers_1)
//			)



		//start invitation
    val chain_invitation =
		pause(if(overridePause) 0 else 5)

    .exec(http("offers GET")
    .get("/offers")
    .headers(headers_1)
    .queryParam("""populate""", """author""")
    .queryParam("""populate""", """activity""")
      .basicAuth( """${username}""", """${password}""")
    .check(jsonPath("$[1].id").saveAs("socialInteractionId"))
    )
    .pause(if(overridePause) 0 else 1)
		.exec(http("socialInteraction GET")
					.get("/socialInteractions/${socialInteractionId}")
					.headers(headers_1)
					.queryParam("""populate""", """author""")
					.queryParam("""populate""", """activity""")
      .basicAuth( """${username}""", """${password}""")
			)
		.pause(50 milliseconds)
		.exec(http("activityevents GET")
					.get("/activityevents")
					.headers(headers_1)
					.queryParam("""sort""", """start""")
					.queryParam("""filter[activity]""", """54ef37f0dd1f434db7724fcf""")
      .basicAuth( """${username}""", """${password}""")
			)
		.pause(40 milliseconds)
		.exec(http("invitations GET")
					.get("/invitations")
					.headers(headers_1)
					.queryParam("""authored""", """true""")
					.queryParam("""populate""", """author""")
					.queryParam("""publishFrom""", """false""")
					.queryParam("""publishTo""", """false""")
					.queryParam("""targetId""", """527916a82079aa8704000006""")
					.queryParam("""filter[activity]""", """54ef37f0dd1f434db7724fcf""")
      .basicAuth( """${username}""", """${password}""")
			)
		.exec(http("request_74")
					.get("/activities/54ef37f0dd1f434db7724fcf/invitationStatus")
					.headers(headers_1)
      .basicAuth( """${username}""", """${password}""")
			)
		.pause(300 milliseconds)
		.exec(http("messages GET")
					.get("/messages")
					.headers(headers_1)
					.queryParam("""authored""", """true""")
					.queryParam("""populate""", """author""")
					.queryParam("""targetId""", """54ef37f0dd1f434db7724fcf""")
					.queryParam("""dismissed""", """true""")
      .basicAuth( """${username}""", """${password}""")
			)
		.pause(92 milliseconds)
		.exec(http("lookAheadCounters GET")
					.get("/activities/54ef37f0dd1f434db7724fcf/lookAheadCounters")
					.headers(headers_1)
					.queryParam("""since""", """2015-02-26T16:58:00.145Z""")
      .basicAuth( """${username}""", """${password}""")
			)
		.repeat(4) {
      pause(300 milliseconds)
        .exec(http("activities validate POST")
        .post("/activities/validate")
        .headers(headers_2)
        .basicAuth( """${username}""", """${password}""")
        .fileBody("RecordedScenario_POSTactivitiesValidate3.json", Map(
          "userId" -> "${userId}"
        )).asJSON
        )
    }
		.pause(if(overridePause) 0 else 5)
		.exec(http("activities join POST")
					.post("/activities/54ef37f0dd1f434db7724fcf//join")
					.headers(headers_2)
          .header("Authorization", "${token}")
          .basicAuth( """${username}""", """${password}""")
          .fileBody("RecordedScenario_POSTactivityJoin.json").asJSON
			)
		.pause(42 milliseconds)
		.exec(http("activity GET")
					.get("/activities/54ef37f0dd1f434db7724fcf")
					.headers(headers_1)
					.queryParam("""populate""", """idea""")
					.queryParam("""populate""", """owner""")
					.queryParam("""populate""", """joiningUsers""")
      .basicAuth( """${username}""", """${password}""")
			)
		.pause(62 milliseconds)
		.exec(http("invitationStatus GET")
					.get("/activities/54ef37f0dd1f434db7724fcf/invitationStatus")
					.headers(headers_1)
			)
		.exec(http("activityevents GET")
					.get("/activityevents")
					.headers(headers_1)
					.queryParam("""sort""", """start""")
					.queryParam("""filter[activity]""", """54ef37f0dd1f434db7724fcf""")
      .basicAuth( """${username}""", """${password}""")
			)
		.exec(http("invitations GET")
					.get("/invitations")
					.headers(headers_1)
					.queryParam("""authored""", """true""")
					.queryParam("""populate""", """author""")
					.queryParam("""publishFrom""", """false""")
					.queryParam("""publishTo""", """false""")
					.queryParam("""targetId""", """527916a82079aa8704000006""")
					.queryParam("""filter[activity]""", """54ef37f0dd1f434db7724fcf""")
      .basicAuth( """${username}""", """${password}""")
			)
		.pause(316 milliseconds)
		.exec(http("messages")
					.get("/messages")
					.headers(headers_1)
					.queryParam("""authored""", """true""")
					.queryParam("""populate""", """author""")
					.queryParam("""targetId""", """54ef37f0dd1f434db7724fcf""")
					.queryParam("""dismissed""", """true""")
      .basicAuth( """${username}""", """${password}""")
			)

		//comments
    val chain_comments =
		pause(if(overridePause) 0 else 11)
		.exec(http("messages POST")
					.post("/messages")
					.headers(headers_2)
      .basicAuth( """${username}""", """${password}""")
          .fileBody("RecordedScenario_POSTmessage.json", Map(
            "userId" -> "${userId}"
          )).asJSON
			)
		.pause(if(overridePause) 0 else 1)
      .exec(http("messages POST")
      .post("/messages")
      .headers(headers_2)
      .basicAuth( """${username}""", """${password}""")
      .fileBody("RecordedScenario_POSTmessage.json", Map(
        "userId" -> "${userId}"
      )).asJSON
      )


	val scn = scenario("dhc")
    .feed(userFeeder)
		.exec(
			chain_signup,
      chain_homeResolves,
      chain_assessment,
      chain_homeResolves,
      chain_recommendation1,
      chain_homeResolves,
//      chain_recommendation1,
      chain_homeResolves,
      chain_invitation,
      chain_comments,
      chain_homeResolves
    )

	setUp(scn.users(1).protocolConfig(httpConf))
}