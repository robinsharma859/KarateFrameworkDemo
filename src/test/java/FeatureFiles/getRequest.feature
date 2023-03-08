Feature: To test the get End point of the application
Background:
  Given  url 'http://localhost:9191'
  * def Token = "98899999"

  Scenario: To test the end point with JSON Data and match complete response

    When  path '/normal/webapi/all'
    When method get
    Then status 200
    Then print  "The response generated is " , response
    And match response ==
    """
    [{
	"jobId": 1,
	"jobTitle": "Software Engg",
	"jobDescription": "To develop andriod application",
	"experience": [
		"Google",
		"Apple",
		"Mobile Iron"
	],
	"project": [{
		"projectName": "Movie App",
		"technology": [
			"Kotlin",
			"SQL Lite",
			"Gradle"
		]
	}]
}]
    """
    @Smoke
    Scenario: Validate the specific property in the Json Array using deep keyword
      Given path '/normal/webapi/all'
      When method get
      And status 200
      Then match  response contains deep {"experience":["Google","Apple","Mobile Iron"]}
      Then match response contains deep{"project":[{"projectName": "Movie App"}]}

  Scenario: Validate the specific property in the Json Array using index
    Given path '/normal/webapi/all'
    When method get
    And status 200
    Then match  response.[0].jobId == 1
    Then match  response.[0].project[0].projectName contains 'Movie App'
    # using wild chard character in expression
    Then match response.[0].experience[*] == ["Google","Apple","Mobile Iron"]

  @Smoke
  Scenario: Validate the specific property in the Json Array using Fuzzy matcher approach
    Given path '/normal/webapi/all'
    When method get
    And status 200
    Then match  response.[0].jobId == '#number'
    Then match  response.[0].project[0].projectName contains 'Movie App'
    # using wild chard character in expression
    Then match response.[0].experience[*] == '#array'