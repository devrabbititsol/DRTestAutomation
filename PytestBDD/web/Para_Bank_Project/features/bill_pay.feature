@parabankweb
Feature: Bill Payment in Parabank

  Scenario: Bill Payment
    Given Login with valid user credetials and navigate to Bill Payment service page
    When User fill all fields and click on send payment button
    Then User should see the the success message


  Scenario: Update Contact Info
    Given Click on Contact Info link
    When User fill all the fields and click on Update Profile button
    Then User should see the success message as Profile Updated


  Scenario: Request Loan
    Given Click on Request Loan link
    When User fill all the filds and click on Apply now button in Apply load page
    Then User should see the success message as load applied