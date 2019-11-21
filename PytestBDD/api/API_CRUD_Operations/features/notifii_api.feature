@api
Feature: Notifii API

Scenario: POST request for Notifii login API
    Given URI for Notifii login API POST Request
    When User performs POST request
    Then User should see the response code as 200

Scenario: POST request for get GlobalConstants in Notifii API
  Given URI for get GlobalConstants in Notifii API
  When User do POST request for GlobalConstants
  Then User should see the response code as 200


Scenario: POST request for Add Receipient
  Given URI for Add Receipient POST request
  When User do POST request for Add Receipient
  Then User should see the response code as 200


Scenario: POST request for Update Receipient
  Given URI for Update Receipient POST request
  When User do Post request for Update Receipient
  Then User should see the response code as 200


Scenario: POST request for Get Receipient
  Given URI for Get Receipient POST request
  When User do Post request for Get Receipient
  Then User should see the response code as 200

Scenario: POST request for Receipient list
  Given URI for Receipient list POST request
  When User do Post request for Receipient list
  Then User should see the response code as 200


Scenario: POST request for Logout in Notifii API
  Given URI for Logout in Notifii API POST request
  When User do Post request for Logout
  Then User should see the response code as 200
