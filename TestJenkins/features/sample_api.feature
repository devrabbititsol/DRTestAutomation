@api
Feature: Sample User API

Scenario: GET request for Users API
    Given URI for Users API for GET Request
    When User performs GET request
    Then I should see the response code as 200


  Scenario: POST request for Users API
   Given URI for Users API for POST Request
   When User do POST request
   Then I should see response code as 201

Scenario: PUT request for Users API
    Given URI for Users API for PUT Request
    When User performs PUT request
    Then I should see the response code as 200


Scenario: DELETE request for Users API
    Given URI for Users API for DELETE Request
    When User performs DELETE request
    Then I should see the response code as 200
