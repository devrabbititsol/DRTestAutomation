#Ship Rocket eCommerce
# ======================
# Ship Rocket-Quick Shipment
# ================================
@ship_rocket_web @web
Feature: Ship Rocket eCommerce
Scenario: Ship Rocket Add new order
    Given Login as a user
    When Create a quick shipment with invalid data
    Then Create a quick shipment with valid data
    Then Check the Shipping Rates
