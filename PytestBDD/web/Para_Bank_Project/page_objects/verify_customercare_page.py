import os
import pytest
import pytest_bdd
import pytest_html

class CustomerCareServices:

    def clk_customer_care_icon(self, driver):
        driver.find_element_by_xpath("//a[text()='contact']").click()
    def fill_name_fld(self, driver):
        driver.find_element_by_css_selector("input[id='name']").send_keys("sam")
    def fill_email_fld(self, driver):
        driver.find_element_by_css_selector("input[id='email']").send_keys("test@gmail.com")
    def fill_phone_fld(self, driver):
        driver.find_element_by_css_selector("input[id='phone']").send_keys("123456")
    def fill_message_fld(self, driver):
        driver.find_element_by_css_selector("textarea[id='message']").send_keys("good")
    def clk_send_to_customer_btn(self, driver):
        driver.find_element_by_css_selector("input[value='Send to Customer Care']").click()
    def get_success_message(self, driver):
        message=driver.find_element_by_css_selector("div[id='rightPanel']").text
        return message
    def clk_admin_page_lnk(self, driver):
        driver.find_element_by_xpath("//a[text()='Admin Page']").click()
    def clk_json_radio_btn(self, driver):
        driver.find_element_by_css_selector("input[id='accessMode3']").click()
    def fill_soap_endpoint(self, driver):
        driver.find_element_by_css_selector("input[id='soapEndpoint']").send_keys("https://test.com")
    def fill_rest_endpoint(self, driver):
        driver.find_element_by_css_selector("input[id='restEndpoint']").send_keys("https://testing.com")
    def fill_endpoint_fld(self, driver):
        driver.find_element_by_css_selector("input[id='endpoint']").send_keys("https://test123.com")
    def clk_submit_btn(self, driver):
        driver.find_element_by_css_selector("input[value='Submit']").click()

    def get_settings_success_msg(self, driver):
        message = driver.find_element_by_xpath("//*[@id='rightPanel']/p/b").text
        return message


    def clk_home_icon(self, driver):
        driver.find_element_by_xpath("//a[text()='home']").click()
    def clk_logout_link(self, driver):
        driver.find_element_by_xpath("//a[text()='Log Out']").click()

    def verify_logout(self, driver):
        message = driver.find_element_by_xpath("//*[@id='leftPanel']/h2").text
        return message


