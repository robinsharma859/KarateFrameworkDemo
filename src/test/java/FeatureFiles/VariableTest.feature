Feature: Use of Variable in karateFramework
  Background: global variables
    * def urlName = "http://www.google.com"
    * def userName = "rahul111"
    # reading variable from differnt feature file


    Scenario: Print variable and reading file
      Given  def file =  read("../Data/Test.json")
      Then  url 'http://localhost:9191/normal/webapi/all'
      And  method get
      # Passing value to feature file
      * def data = call read("Common.feature") {_url: "www.google.com",_password: "rahul123"}
      And print  "Token value =>  ", data.Token
      Then match response ==  file


      Scenario: Execute java script function
        * def getIntData  = function(){return 10;}
        Then print "Funtiona value = > " ,getIntData()
        * def randomNumber =  function(){ return Math.floor((100) * Math.random());}
        Then print "Random Funtion value = > " ,randomNumber()

  Scenario: Embedded Expression in Karate replacing with variable
    * def jobId = 100
    * def jobTitle = "Testing"
    * def rquestParam  =
    """
     {
 	"jobId": '#(jobId)',
 	"jobTitle": '#(jobTitle)',
 	"jobDescription": "To Automate andriod application",
 	"experience": [
 		"Nagaro1o"
 	],
 	"project": [{
 		"projectName": "Movie App",
 		"technology": [
 			"Kotlin",
 			"SQL Lite",
 			"Gradle1"
 		]
 	}]
 }
    """
    And print rquestParam

    Scenario Outline: Verify data with multiple data set
      Given print  '<UserName>'  + "  Passreord ->" +  '<Password>'  + " Age = >"  + '<Age>'

      Examples:
     |UserName | Password | Age|
     |rsharma  | abcd123  |11  |
     |rahul28  |sucessfully@123|44|

  Scenario: Reading config file karate.config

    * def configURL = AppURl
    When print AppURl
