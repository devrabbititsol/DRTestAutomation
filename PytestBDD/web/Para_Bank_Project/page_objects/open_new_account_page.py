import time

import os
import pytest
import pytest_bdd
import pytest_html

from utilities.base_class import BaseClass


class OpenNewAccountPage(BaseClass):

    def fill_username_fld(self, driver):
        driver.find_element_by_css_selector("input[name='username']").send_keys("johnsmith")
    def fill_password_fld(self, driver):
        driver.find_element_by_css_selector("input[name='password']").send_keys("testuser")
    def click_login_btn(self, driver):
        driver.find_element_by_css_selector("input[value='Log In']").click()
    def clk_new_account_link(self, driver):
        driver.find_element_by_xpath("//a[text()='Open New Account']").click()
    def clk_new_account_btn(self, driver):
        driver.find_element_by_xpath("//*[@id='rightPanel']/div/div/form/div/input").click()
        # driver.find_element_by_xpath("//*[@id='rightPanel']/div/div/form/div/input").click()
    #  input[value='Open New Account']
    def get_success_message(self, driver):
        message=driver.find_element_by_css_selector("div[ng-if='showResult']").text
        return message

    def click_account_nmbr(self, driver):
        driver.find_element_by_css_selector("a[id='newAccountId']").click()
    def get_account_details(self, driver):
        account_details = driver.find_element_by_css_selector("div[ng-controller='AccountDetailsCtrl']").text
        return account_details
    def clk_fund_trasfer_lnk(self, driver):
        driver.find_element_by_xpath("//a[text()='Funds Transfer Received']").click()

    def get_trascation_details(self, driver):
        transaction_details=driver.find_element_by_css_selector("div[id='rightPanel']").text
        return transaction_details
    def close_browser(self, driver):
        driver.quit()

