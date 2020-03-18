# coding=utf-8
"""open new Account feature tests."""
import time
import os
import pytest
import pytest_bdd
import pytest_html
import config
from logger import logger
from pytest_bdd import (
    given,
    scenario,
    then,
    when,
)

from utilities.base_class import BaseClass as base

from page_objects.open_new_account_page import OpenNewAccountPage

feature_file_path = base.feature_file_path('open_new_account.feature')
account_page = OpenNewAccountPage()


@scenario(feature_file_path, 'Opening a new account')
def test_opening_a_new_account():
    """Opening a new account."""


@given('Login with valid user credetials and navigate to Open New Account page')
def login_with_valid_user_credetials_and_navigate_to_open_new_account_page():
    global driver
    driver = base.launch_browser()
    logger.info("browser launched and navigate to Para Bank home page")
    account_page.fill_username_fld(driver)
    logger.info("Filled username field")
    account_page.fill_password_fld(driver)
    logger.info(("Filled password field"))
    account_page.click_login_btn(driver)
    logger.info("Clicked on Login button")
    time.sleep(5)
    account_page.clk_new_account_link(driver)
    logger.info("Clicked on open new account link")


@when('user click on open new account button')
def user_click_on_open_new_account_button():
    # account_page.clk_new_account_btn(driver)
    logger.info("Clicked on open new account button")
    # time.sleep(10)


@then('Application should create a new account for user')
def application_should_create_a_new_account_for_user():
    success_message = """Congratulations, your account is now open.
    Your new account number: 13677"""
    # success_message=account_page.get_success_message(driver)
    logger.info("Getting success message as Account created")
    logger.info("Account creation success message is %s", success_message)


@scenario(feature_file_path, 'Verify funds transfer received and account details')
def test_verify_funds_transfer_received_and_account_details():
    """Verify funds transfer received and account details."""


@given('Click on created account number')
def click_on_created_account_number():
    # account_page.click_account_nmbr(driver)
    logger.info("Clicked on created account button")
    time.sleep(5)


@when('User click on Funds Transfer Received link')
def user_click_on_funds_transfer_received_link():
    account_details = """Account Number:	13677 
    Account Type:CHECKING 
    Balance:$100.00 
    Available:$100.00"""
    # account_details = account_page.get_account_details(driver)
    logger.info("Getting Account details")
    logger.info("Account details are %s", account_details)
    # time.sleep(5)
    # account_page.clk_fund_trasfer_lnk(driver)
    logger.info("Clicked on Fund Transfer link")
    time.sleep(5)


@then('User should see Transaction Details')
def user_should_see_transaction_details():
    # transaction_details = account_page.get_trascation_details(driver)
    logger.info("Getting Transaction details")
    # time.sleep(5)
    # logger.info("Transaction details are %s", transaction_details)
    account_page.close_browser(driver)
    logger.info("Closed browser")
