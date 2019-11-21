@parabankweb
Feature: Verify Customer Care Services and logout

  Scenario: Customer Care Services
    Given Login with valid credentials and navigate to customer care services page
    When User fill all the fields and click on send to customer care button
    Then User should see the the success message


  Scenario: Edit Web services and Application settings in Admin Page
    Given Click on Admin Page button
    When User fill Web services and Application setting details and click on Submit button
    Then User should see the success message as settings saved
