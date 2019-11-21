@parabankweb
Feature: open new Account

  Scenario: Opening a new account
    Given Login with valid user credetials and navigate to Open New Account page
    When user click on open new account button
    Then Application should create a new account for user


  Scenario: Verify funds transfer received and account details
    Given Click on created account number
    When User click on Funds Transfer Received link
    Then User should see Transaction Details