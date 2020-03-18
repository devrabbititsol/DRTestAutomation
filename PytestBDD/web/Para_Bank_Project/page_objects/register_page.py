import os
import pytest
import pytest_bdd
import pytest_html

from utilities.base_class import BaseClass


class RegistrationPage(BaseClass):

    def clk_register_btn(self, driver):
        driver.find_element_by_xpath("//a[text()='Register']").click()
    def fill_first_name_fld(self, driver, first_name):
        driver.find_element_by_css_selector("input[id='customer.firstName']").send_keys(first_name)
    def fill_last_name_fld(self, driver, last_name):
        driver.find_element_by_css_selector("input[id='customer.lastName']").send_keys(last_name)
    def fill_address_fld(self, driver, address):
        driver.find_element_by_css_selector("input[id='customer.address.street']").send_keys(address)
    def fill_city_fld(self, driver, city):
        driver.find_element_by_css_selector("input[id='customer.address.city']").send_keys(city)
    def fill_state_fld(self, driver, state):
        driver.find_element_by_css_selector("input[id='customer.address.state']").send_keys(state)
    def fill_zip_code_fld(self, driver, zip_code):
        driver.find_element_by_css_selector("input[id='customer.address.zipCode']").send_keys(zip_code)

    def fill_phone_fld(self, driver, phone):
        driver.find_element_by_css_selector("input[id='customer.phoneNumber']").send_keys(phone)

    def fill_ssn_number_fld(self, driver, ssn_number):
        driver.find_element_by_css_selector("input[id='customer.ssn']").send_keys(ssn_number)

    def fill_username_fld(self, driver, user_name):
        driver.find_element_by_css_selector("input[id='customer.username']").send_keys(user_name)
    def fill_password_fld(self, driver, password):
        driver.find_element_by_css_selector("input[id='customer.password']").send_keys(password)

    def fill_confirm_paswrd_fld(self, driver, confirm_pswrd):
        driver.find_element_by_css_selector("input[id='repeatedPassword']").send_keys(confirm_pswrd)

    def clk_submit_btn(self, driver):
        driver.find_element_by_css_selector("input[value='Register']").click()

    def get_success_message(self, driver):
        message=driver.find_element_by_css_selector("div[id='rightPanel']").text
        return message
    def close_browser(self, driver):
        driver.quit()