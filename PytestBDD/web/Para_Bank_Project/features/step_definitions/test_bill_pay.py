from logger import logger
import time
import os
import pytest
import pytest_bdd
import pytest_html
from pytest_bdd import (
    given,
    scenario,
    then,
    when,
)
import page_objects.bill_pay_page as _page
from utilities.base_class import BaseClass as base
import page_objects.open_new_account_page as acnt_page

feature_file_path=base.feature_file_path('bill_pay.feature')
page = _page.BillPayPage()
account_page=acnt_page.OpenNewAccountPage()

@scenario(feature_file_path, 'Bill Payment')
def test_bill_payment():
    """Bill Payment."""

@given('Login with valid user credetials and navigate to Bill Payment service page')
def login_with_valid_user_credetials_and_navigate_to_bill_payment_service_page():
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

@when('User fill all fields and click on send payment button')
def user_fill_all_fields_and_click_on_send_payment_button():
    page.clk_bill_pay_lnk(driver)
    logger.info("Clicked on Bill pay link")
    page.fill_payee_name_fld(driver)
    logger.info("Filled Payee Name field")
    page.fill_address_fld(driver)
    logger.info("Filled Address field")
    page.fill_city_fld(driver)
    logger.info("Filled City field")
    page.fill_state_fld(driver)
    logger.info("Filled State field")
    page.fill_zip_fld(driver)
    logger.info("Filled Zip Code field")
    page.fill_phone_fld(driver)
    logger.info("Filled Phone field")
    page.fill_acnt_number_fld(driver)
    logger.info("Filled Account number field")
    page.fill_verify_acnt_fld(driver)
    logger.info("Filled Verify Account number field")
    page.fill_amount_fld(driver)
    logger.info("Filled Amount field")
    page.clk_send_pay_btn(driver)
    logger.info("Clicked on Send Payment button")



@then('User should see the the success message')
def user_should_see_the_the_success_message():
    success_message=page.get_success_message(driver)
    logger.info("Getting success message as bill paid")
    logger.info("Bill payment success message is %s", success_message)


@scenario(feature_file_path, 'Update Contact Info')
def test_update_contact_info():
    """Update Contact Info."""


@given('Click on Contact Info link')
def click_on_contact_info_link():
    page.clk_update_contact_info_lnk(driver)
    logger.info("Clicked on Update Contact Info link")


@when('User fill all the fields and click on Update Profile button')
def user_fill_all_the_fields_and_click_on_update_profile_button():
    page.fill_first_name(driver)
    logger.info("Filled First Name field")
    page.fill_last_name(driver)
    logger.info("Filled Last Name field")
    page.fill_adres_fld(driver)
    logger.info("Filled Address field")
    page.fill_city_field(driver)
    logger.info("Filled City field")
    page.fill_state_field(driver)
    logger.info("Filled State field")
    page.fill_zip_code_field(driver)
    logger.info("Filled Zip Code field")
    page.fill_phn_field(driver)
    logger.info("Filled Phone Number field")
    page.clk_update_profile_btn(driver)
    logger.info("Clicked on Update Profile button")
    time.sleep(5)

@then('User should see the success message as Profile Updated')
def user_should_see_the_success_message_as_profile_updated():
    success_message=page.get_updated_message(driver)
    logger.info("Success message for updating contact info is %s", success_message)

@scenario(feature_file_path, 'Request Loan')
def test_request_loan():
    """Request Loan."""

@given('Click on Request Loan link')
def click_on_request_loan_link():
    page.clk_request_loan_lnk(driver)
    logger.info("Clicked on Request loan button")

@when('User fill all the filds and click on Apply now button in Apply load page')
def user_fill_all_the_filds_and_click_on_apply_now_button_in_apply_load_page():
    page.fill_amnt_fld(driver)
    logger.info("Filled Amount field")
    page.fill_down_payment_fld(driver)
    logger.info("Filled Down Payment field")
    page.clk_apply_now_btn(driver)
    logger.info("Clicked on Apply Now button")
    time.sleep(5)
@then('User should see the success message as load applied')
def user_should_see_the_success_message_as_load_applied():
    success_message=page.get_apply_loan_message(driver)
    logger.info("Success message for Applied loan %s", success_message)
    account_page.close_browser(driver)
    logger.info("Closed browser")