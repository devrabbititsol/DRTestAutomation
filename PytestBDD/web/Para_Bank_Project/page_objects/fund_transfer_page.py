
import os
import pytest
import pytest_bdd
import pytest_html

from utilities.base_class import BaseClass


class FundTransferPage(BaseClass):

    def clk_account_overview_lnk(self, driver):
        driver.find_element_by_xpath("//a[text()='Accounts Overview']").click()
    def get_acnt_details(self, driver):
        account_details = driver.find_element_by_css_selector("table[id='accountTable']").text
        return account_details
    def clk_transfer_funds_lnk(self, driver):
        driver.find_element_by_xpath("//a[text()='Transfer Funds']").click()
    def fill_amount_fld(self, driver):
        driver.find_element_by_css_selector("input[id='amount']").send_keys("20")
    def clk_transfer_btn(self, driver):
        driver.find_element_by_css_selector("input[value='Transfer']").click()

    def get_transfer_details(self, driver):
        success_message = driver.find_element_by_css_selector("div[ng-if='showResult']").text
        return success_message





