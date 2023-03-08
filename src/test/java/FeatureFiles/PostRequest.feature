Feature: Create Post request

  Background: er is trying to post data on the server
    Given url 'http://localhost:9191'

    Scenario: Create Post request using Json
      Given path '/normal/webapi/add'
      And headers { Accept:'application/json',Content-type:'application/json'}
      And request
      """
    {
    "jobId": 7,
    "jobTitle": "Autp",
    "jobDescription": "To Automate andriod application",
    "experience": [
    "Nagaro1o"
    ],
    "project": [
    {
    "projectName": "Movie App",
    "technology": [
    "Kotlin",
    "SQL Lite",
    "Gradle1"
    ]
    }
    ]
    }
"""
      Then method post
      And status 201
      And print response
      Then match response.jobId == 7

Scenario: post request from external file and  validate the schema returned in response
  Given path '/normal/webapi/add'
  And headers { Accept:'application/json',Content-type:'application/json'}
  And def requestDetails =  read("../Data/Test.json")
  And def validateSchema =  read("../Data/ValidateSchema.json")
  Then request requestDetails
  And method post
  Then status 201
  And match  response == validateSchema