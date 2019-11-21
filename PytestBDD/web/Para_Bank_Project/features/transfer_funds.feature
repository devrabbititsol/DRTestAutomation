@parabankweb
Feature: Account Overview and Fund Transfer

  Scenario: Account Overview
    Given Login with valid user credetials
    When User click on Account Overview link
    Then User should see the account details

  Scenario: Fund Transfer
    Given Navigate to Transfer Funds page
    When User transfer funds to another account
    Then User should success message of funds transfer
