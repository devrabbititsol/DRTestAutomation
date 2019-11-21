@api
Feature: New Headlines API

Scenario: GET request for News Headlines API
    Given URI for New Headlines API for GET Request
    When User performs GET request
    Then I should see the response code as 200

  Scenario: GET request for sources API
    Given URI for sources API for GET Request
    When User performs GET request for sources
    Then I should see the response code as 200

Scenario: GET request for Everything API
    Given URI for Everything API for GET Request
    When User performs GET request for Everything
    Then I should see the response code as 200

Scenario: GET request for News Headlines with Country API
    Given URI for Country API for GET Request
    When User performs GET request for Country
    Then I should see the response code as 200
