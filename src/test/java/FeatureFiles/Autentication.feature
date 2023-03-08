Feature: Validate Authentication
  
  Background: User user authentication token
    Given url 'http://localhost:9191'
    * def tokenValue = 'Basic YWRtaW46d2VsY29tZQ=='

    Scenario: User to get the request using Basic aAuthentication and replace variable
      Given path 'secure/webapi/all'
      * def data  = {content-type:'application/json',Authorization: '#(tokenValue)'}
      And headers {content-type:'application/json',Authorization:'#(tokenValue)'}
      Then method get
      And status 200
      Then match response.[0].jobId == 1

  Scenario: User to get the request using Basic aAuthentication using Java script code
    Given path 'secure/webapi/all'
    * def auth = call read('C:\Users\hp\IdeaProjects\Karate02\src\test\java\FeatureFiles\Auth.js')
    And print auth
    And headers {content-type:'application/json',Authorization:'#(tokenValue)'}
    Then method get
    And status 200
    Then match response.[0].jobId == 1