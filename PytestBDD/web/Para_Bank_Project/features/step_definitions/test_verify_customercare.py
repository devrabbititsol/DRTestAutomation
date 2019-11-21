import time
from logger import logger
from pytest_bdd import (
    given,
    scenario,
    then,
    when,
)
import os
import pytest
pytest.config
import pytest_bdd
import pytest_html
import page_objects.verify_customercare_page as _page
from utilities.base_class import BaseClass as base
import page_objects.open_new_account_page as acnt_page

feature_file_path=base.feature_file_path('verify_customercare_services.feature')
page=_page.CustomerCareServices()
account_page=acnt_page.OpenNewAccountPage()

@scenario(feature_file_path, 'Customer Care Services')
def test_customer_care_services():
    """Customer Care Services."""

@given('Login with valid credentials and navigate to customer care services page')
def login_with_valid_credentials_and_navigate_to_customer_care_services_page():
    global driver
    driver=base.launch_browser()
    logger.info("browser launched and navigate to Para Bank home page")
    account_page.fill_username_fld(driver)
    logger.info("Filled username field")
    account_page.fill_password_fld(driver)
    logger.info(("Filled password field"))
    account_page.click_login_btn(driver)
    logger.info("Clicked on Login button")
    time.sleep(5)
    page.clk_customer_care_icon(driver)
    logger.info("Clicked on Customer Care Services Icon")

@when('User fill all the fields and click on send to customer care button')
def user_fill_all_the_fields_and_click_on_send_to_customer_care_button():
    page.fill_name_fld(driver)
    logger.info("Filled Name field")
    page.fill_email_fld(driver)
    logger.info("Filled Email field")
    page.fill_phone_fld(driver)
    logger.info("Filled Phone number field")
    page.fill_message_fld(driver)
    logger.info("Filled Message field")
    page.clk_send_to_customer_btn(driver)
    logger.info("Clicked on Send to Customer button")

@then('User should see the the success message')
def user_should_see_the_the_success_message():
    success_message=page.get_success_message(driver)
    logger.info("Success message is %s",success_message)

@scenario(feature_file_path, 'Edit Web services and Application settings in Admin Page')
def test_edit_web_services_and_application_settings_in_admin_page():
    """Edit Web services and Application settings in Admin Page."""

@given('Click on Admin Page button')
def click_on_admin_page_button():
    page.clk_admin_page_lnk(driver)
    logger.info("Clicked on Admin Page link")

@when('User fill Web services and Application setting details and click on Submit button')
def user_fill_web_services_and_application_setting_details_and_click_on_submit_button():
    page.clk_json_radio_btn(driver)
    logger.info("clicked on JSON radio button")
    page.fill_rest_endpoint(driver)
    logger.info("Filled Rest endpoint field")
    page.fill_soap_endpoint(driver)
    logger.info("Filled SOAP endpoint field")
    page.fill_endpoint_fld(driver)
    logger.info("Filled Endpoint field")
    page.clk_submit_btn(driver)
    logger.info("Clicked on Submit button")
    time.sleep(2)

@then('User should see the success message as settings saved')
def user_should_see_the_success_message_as_settings_saved():
    success_message=page.get_settings_success_msg(driver)
    logger.info("Success message for updating settings is %s",success_message)
    time.sleep(2)
    page.clk_home_icon(driver)
    logger.info("Clicked on Home Page link")
    page.clk_logout_link(driver)
    logger.info("Clicked on Logout link")
    logout_message=page.verify_logout(driver)
    if logout_message=="Customer Login":
        logger.info("User logout successfully")
    else:
        logger.info("User logout is not successfull")

    account_page.close_browser(driver)
    logger.info("Closed browser")
