import time

from logger import logger
from pytest_bdd import (
    given,
    scenario,
    then,
    when,
)

import page_objects.fund_transfer_page as funds
import page_objects.open_new_account_page as acnt_page
from utilities.base_class import BaseClass as base
import os
import pytest
import pytest_bdd
import pytest_html

feature_file_path = base.feature_file_path('transfer_funds.feature')
page = funds.FundTransferPage()
accounts_page = acnt_page.OpenNewAccountPage()

@scenario(feature_file_path, 'Account Overview')
def test_account_overview():
    """Account Overview."""

@given('Login with valid user credetials')
def login_with_valid_user_credetials():
    global driver
    driver=base.launch_browser()
    logger.info("browser launched and navigate to Para Bank home page")
    accounts_page.fill_username_fld(driver)
    logger.info("Filled username field")
    accounts_page.fill_password_fld(driver)
    logger.info(("Filled password field"))
    accounts_page.click_login_btn(driver)
    logger.info("Clicked on Login button")
    time.sleep(2)

@when('User click on Account Overview link')
def user_click_on_account_overview_link():
    page.clk_account_overview_lnk(driver)
    logger.info("Clicked on Account Overview link")
    time.sleep(2)

@then('User should see the account details')
def user_should_see_the_account_details():
    account_details = page.get_acnt_details(driver)
    logger.info("Getting account details")
    logger.info("Current Account details are %s", account_details)
    time.sleep(2)

@scenario(feature_file_path, 'Fund Transfer')
def test_fund_transfer():
    """Fund Transfer."""

@given('Navigate to Transfer Funds page')
def navigate_to_transfer_funds_page():
    page.clk_transfer_funds_lnk(driver)
    logger.info("Clicked on transfer funds link")
    time.sleep(2)

@when('User transfer funds to another account')
def user_transfer_funds_to_another_account():
    page.fill_amount_fld(driver)
    logger.info("Filled amount field")
    page.clk_transfer_btn(driver)
    logger.info("Clicked on Transfer button")
    time.sleep(2)

@then('User should success message of funds transfer')
def user_should_success_message_of_funds_transfer():
    success_message= page.get_transfer_details(driver)
    logger.info("Success message for funds transfer is %s", success_message)
    accounts_page.close_browser(driver)
