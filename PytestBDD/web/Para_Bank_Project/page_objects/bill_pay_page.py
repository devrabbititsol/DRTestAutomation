import os
import pytest
import pytest_bdd
import pytest_html
class BillPayPage:
    def clk_bill_pay_lnk(self, driver):
        driver.find_element_by_xpath("//a[text()='Bill Pay']").click()
    def fill_payee_name_fld(self, driver):
        driver.find_element_by_css_selector("input[name='payee.name']").send_keys("sam")
    def fill_address_fld(self, driver):
        driver.find_element_by_css_selector("input[name='payee.address.street']").send_keys("tirupati")
    def fill_city_fld(self, driver):
        driver.find_element_by_css_selector("input[name='payee.address.city']").send_keys("tpt")
    def fill_state_fld(self, driver):
        driver.find_element_by_css_selector("input[name='payee.address.state']").send_keys("AP")
    def fill_zip_fld(self, driver):
        driver.find_element_by_css_selector("input[name='payee.address.zipCode']").send_keys("517501")
    def fill_phone_fld(self, driver):
        driver.find_element_by_css_selector("input[name='payee.phoneNumber']").send_keys("9456123456")
    def fill_acnt_number_fld(self, driver):
        driver.find_element_by_css_selector("input[name='payee.accountNumber']").send_keys("123456")
    def fill_verify_acnt_fld(self, driver):
        driver.find_element_by_css_selector("input[name='verifyAccount']").send_keys("123456")
    def fill_amount_fld(self, driver):
        driver.find_element_by_css_selector("input[name='amount']").send_keys("10")
    def clk_send_pay_btn(self, driver):
        driver.find_element_by_css_selector("input[value='Send Payment']").click()
    def get_success_message(self, driver):
        message = driver.find_element_by_css_selector("div[ng-show='showResult']").text
        return message

    def clk_update_contact_info_lnk(self, driver):
        driver.find_element_by_xpath("//a[text()='Update Contact Info']").click()

    def fill_first_name(self, driver):
        driver.find_element_by_css_selector("input[id='customer.firstName']").send_keys("test")

    def fill_last_name(self, driver):
        driver.find_element_by_css_selector("input[id='customer.lastName']").send_keys("te")

    def fill_adres_fld(self, driver):
        driver.find_element_by_css_selector("input[id='customer.address.street']").send_keys("tpt")

    def fill_city_field(self, driver):
        driver.find_element_by_css_selector("input[id='customer.address.city']").send_keys("tpt")

    def fill_state_field(self, driver):
        driver.find_element_by_css_selector("input[id='customer.address.state']").send_keys("AP")

    def fill_zip_code_field(self, driver):
        driver.find_element_by_css_selector("input[id='customer.address.zipCode']").send_keys("517501")

    def fill_phn_field(self, driver):
        driver.find_element_by_css_selector("input[id='customer.phoneNumber']").send_keys("123456")

    def clk_update_profile_btn(self, driver):
        driver.find_element_by_css_selector("input[value='Update Profile']").click()

    def get_updated_message(self, driver):
        message = driver.find_element_by_css_selector("div[ng-app='UpdateProfileApp']").text
        return message

    def clk_request_loan_lnk(self, driver):
        driver.find_element_by_xpath("//a[text()='Request Loan']").click()

    def fill_amnt_fld(self, driver):
        driver.find_element_by_css_selector("input[id='amount']").send_keys("300")

    def fill_down_payment_fld(self, driver):
        driver.find_element_by_css_selector("input[id='downPayment']").send_keys("10")

    def clk_apply_now_btn(self, driver):
        driver.find_element_by_css_selector("input[value='Apply Now']").click()

    def get_apply_loan_message(self, driver):
        message = driver.find_element_by_css_selector("div[ng-if='showResult']").text
        return message

