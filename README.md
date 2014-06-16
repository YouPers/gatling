# Gatling Load Tests

We use [Gatling](http://gatling-tool.org/) for our load tests, use the version in this repository or download a current version from [https://github.com/excilys/gatling](https://github.com/excilys/gatling)

## Recorder

Gatling provides a convinient recorder, which can be used to record all requests while using the server and write them to a simulation file. This already does much of the work, but the test of course has to be optimized, in order to get a realistic simulation with many distinct users.

-  start bin/recorder.sh
-  choose a HTTP/HTTPS port that does not conflict with your local application server
-  enter a package and a class name
-  setup a proxy in your browser with the same port numbers as above
-  exclude google-analytics.com or any other undesired domains from your proxy configuration
-  start the recorder and use your browser to generate requests
-  the recorded simulation is located in /user-files/simulations 

## Complete User Scenario

A first version of a simulation that covers the most important user flows from our yp ewl app can be found in

	/user-files/simulations/com/youpers/load/CompleteUserScenario
	
The request bodys and templates are located in 

	/request-bodies
	
At the end of the simulation file you will find the parameters like the **number of concurrent users**, and the **ramp up time** it will take until all users start their duty.

Requests/Executions are combined to **chains**, which enables us to easily enable or disable parts of the user workflow.

The simulation includes a custom **user feeder**, that provides users with random usernames and email adresses.

A very important concept in a gatling simulation are **variables**, which enable us to create a dynamic user scenario. The user variables from the user feeder are availabe in the session and can be accessed in any String parameter with **${ variable }**.

Unfortunately Gatling does not support a convinient way to process JSON responses. Values from a server response have to be stored as a variable using a check with a jsonPath notation.

	.check(jsonPath("$.id").saveAs("userId"))
	
These variables can be used to create a new request with a SSP template.

	.fileBody("CompleteUserScenario_profile.json", Map(
            "userId" -> "${userId}",
            "profileId" -> "${profileId}"
        )).asJSON
        
In the SSP file we have to use the **${ variable }** notation as well.

	"owner": "${userId}",
    "id": "${profileId}"
    
A session is immutable, if you want to reuse a chain with alternating parameters you have to create a new session with the modified parameter before executing the chain:

	val chain_planMultipleActivityOffers = exec(

    exec(
      (session: Session) => {
        session.setAttribute("activityId", "5278c6adcdeab69a2500001e")
      }
    ),
    chain_planActivityOffer