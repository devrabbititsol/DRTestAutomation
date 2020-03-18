# coding=utf-8
"""Registration in Para bank feature tests."""
import datetime
import os
import pytest
import pytest_bdd
import pytest_html
from logger import logger
from pytest_bdd import (
    given,
    scenario,
    then,
    when,
)

from page_objects.register_page import RegistrationPage
from utilities.base_class import BaseClass as base

feature_file_path = base.feature_file_path('registration.feature')
register = RegistrationPage()

@scenario(feature_file_path, 'New user registration')
def test_registering_a_new_user():
    """Registering a new user."""

@given('Navigate to Registration page')
def navigate_to_registration_page():
    global driver
    driver =base.launch_browser()
    logger.info("browser launched and navigate to Para Bank home page")
    register.clk_register_btn(driver)
    logger.info("Clicked on Register button")

@when(
    'User fill "<first_name>","<last_name>","<address>","<city>",'
    '"<state>","<zip_code>","<phone>","<ssn_number>","<username>",'
    '"<password>","<confirm_password>" fields and click on Register button'
)
def user_registration(
        first_name, last_name, address, city, state, zip_code,
        phone, ssn_number, username, password, confirm_password
):
    time_stamp = datetime.datetime.now().strftime('%M%S%f')
    register.fill_first_name_fld(driver, first_name)
    logger.info("Filled first name field in registration page")
    register.fill_last_name_fld(driver, last_name)
    logger.info("Filled last name field")
    register.fill_address_fld(driver, address)
    logger.info("Filled address field")
    register.fill_city_fld(driver, city)
    logger.info("Filled city field")
    register.fill_state_fld(driver, state)
    logger.info("Filled state field")
    register.fill_zip_code_fld(driver, zip_code)
    logger.info("Filled zip code field")
    register.fill_phone_fld(driver, phone)
    logger.info("Filled phone number field")
    register.fill_ssn_number_fld(driver, ssn_number)
    logger.info("Filled ssn number field")
    register.fill_username_fld(driver, username+time_stamp[:-7])
    logger.info("Filled username field")
    register.fill_password_fld(driver, password)
    logger.info("Filled password field")
    register.fill_confirm_paswrd_fld(driver, confirm_password)
    logger.info("Filled confirm password field")
    register.clk_submit_btn(driver)
    logger.info("Clicked on submit button")

@then('New user should be created')
def new_user_should_be_created():
    success_message=register.get_success_message(driver)
    logger.info("Getting success message as user is created")
    logger.info("Success message for User creation is %s", success_message)
    register.close_browser(driver)
    logger.info("Closed browser")
