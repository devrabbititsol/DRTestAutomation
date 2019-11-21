@parabankweb
Feature: Registration in Para bank

  Scenario Outline: New user registration
    Given Navigate to Registration page
    When User fill "<first_name>","<last_name>","<address>","<city>","<state>","<zip_code>","<phone>","<ssn_number>","<username>","<password>","<confirm_password>" fields and click on Register button
    Then New user should be created

    Examples:
    |first_name|last_name|address|city|state|zip_code|phone|ssn_number|username|password|confirm_password|
    |John      |Mike     |TK street|Tirupati|AP|517501|965236987|123456|Johnmike|testuser|testuser|

