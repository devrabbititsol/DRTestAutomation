import logging
import os
from os.path import dirname
from pytest_bdd import scenario, given, when, then
from pageObjects.create_quick_shipment_page import CreateQuickShipmentPage
from pageObjects.login_page import LoginPage
from pageObjects.shipping_rates_calculator_page import ShippingRatesCalculatorPage
from utilities.base_ui import BaseUI
from utilities.global_variables import GlobalVariables
logging.basicConfig(level=logging.INFO)

class ShipRocket:
    driver = None
    login_page = None
    create_quick_shipment_page = None
    shipping_rates_calculator_page = None
    new_order_page = None

base_ui = BaseUI(ShipRocket.driver)
ShipRocket.driver = base_ui.launch_browser_instance(browser_type='chrome',
                                                    application_url='https://app.shiprocket.in/quick-ship')
GlobalVariables.project_path = dirname(dirname(__file__))
GlobalVariables.feature_file_path = os.path.join(GlobalVariables.project_path, 'shiprocket_ecommerce.feature')
create_quick_shipment_page = CreateQuickShipmentPage(ShipRocket.driver)
shipping_rates_calculator_page = ShippingRatesCalculatorPage(ShipRocket.driver)

@scenario(GlobalVariables.feature_file_path, 'Ship Rocket Add new order')
def test_ship_rocket_add_new_order():
    """Ship Rocket Add new order."""

@given('Login as a user')
def login_as_a_user():
    try:

        login_page = LoginPage(ShipRocket.driver)

        home_page_title = login_page.get_page_title()
        logging.info('Application is Launched in Browser')
        if home_page_title is 'eCommerce Logistics & Shipping Solutions: Multiple Courier Aggregator India':
            logging.info('Application Home Page Title is : ' + home_page_title)
        else:
            logging.info('Application Home Page Title is : ' + home_page_title)
        login_page.fill_email_address_field('chandrachinthapatla@gmail.com')
        logging.info('Enter email address in email field')
        login_page.fill_password_field('Sekhar@123')
        logging.info('Enter password in password field')
        login_page.click_login_button()
        logging.info('Click on login button')
        logging.info('User is Successfully Logged into Application')
    except Exception as e:
        raise AssertionError("Exception is ", e)

@when('Create a quick shipment with invalid data')
def create_a_quick_shipment_with_invalid_data():
    try:
        # create_quick_shipment_page.visit()
        create_quick_shipment_page.fill_deliver_address_field()
        logging.info('Enter deliver address in deliver field')
        create_quick_shipment_page.fill_deliver_to_field('517644')
        logging.info('Enter deliver to address')
        create_quick_shipment_page.fill_product_name_field('Washing Machine')
        logging.info('Enter product name in product field')
        create_quick_shipment_page.fill_quantity_field('2')
        logging.info('Enter qunatity in quantity field')
        create_quick_shipment_page.fill_unit_price_field('48000')
        logging.info('Enter price in unit price field')
        create_quick_shipment_page.fill_weight_field('42')
        logging.info('Enter weight in weight field')
        create_quick_shipment_page.fill_length_field('42')
        logging.info('Enter length in length field')
        create_quick_shipment_page.fill_breadth_field('27')
        logging.info('Enter breadth in breadth field')
        create_quick_shipment_page.fill_height_field('26')
        logging.info('Enter height in height field')
        create_quick_shipment_page.click_search_courier_button()
        logging.info('Click on search courier button')
        alert_message_no_couriers = create_quick_shipment_page.get_alert_message_no_couriers()
        assert alert_message_no_couriers == 'No courier service available between 517501 and 517644'
        logging.info('Alert Message When No Couriers Available : ' + alert_message_no_couriers)
        create_quick_shipment_page.click_alert_cancel_no_couriers()
        logging.info('Click on alert cancel no courier button')
    except Exception as e:
        raise AssertionError("Exception is ", e)

@then('Create a quick shipment with valid data')
def create_a_quick_shipment_with_valid_data():
    try:
        create_quick_shipment_page.click_back_create_shipment_button()
        logging.info('Click on create shipment button')
        create_quick_shipment_page.fill_deliver_to_field('517619')
        logging.info('Enter deliver to in deliver field')
        create_quick_shipment_page.fill_product_name_field('LED TV')
        logging.info('Enter product name in product field')
        create_quick_shipment_page.fill_quantity_field('2')
        logging.info('Enter qunatity in quantity field')
        create_quick_shipment_page.fill_unit_price_field('19000')
        logging.info('Enter price in unit price field')
        create_quick_shipment_page.fill_weight_field('24')
        logging.info('Enter weight in weight field')
        create_quick_shipment_page.fill_length_field('28')
        logging.info('Enter length in length field')
        create_quick_shipment_page.fill_breadth_field('25')
        logging.info('Enter breadth in breadth field')
        create_quick_shipment_page.fill_height_field('29')
        logging.info('Enter height in height field')
        create_quick_shipment_page.click_search_courier_button()
        logging.info('Click on search courier button')
        # create_quick_shipment_page.select_option('CHEAPEST')
        # pickup_description = create_quick_shipment_page.get_pickup_details()
        # logging.info('Courier Pickup Description for the above selected : ' + pickup_description)
        # deliver_to_description = create_quick_shipment_page.get_deliver_to_details()
        # logging.info('Courier Deliver Description for the above selected : ' + deliver_to_description)
        # payment_mode_type = create_quick_shipment_page.get_payment_type()
        # logging.info('Payment Type for Courier is : ' + payment_mode_type)
        # create_quick_shipment_page.select_option('FASTEST')
        # logging.info('Selected the Courier Type as : FASTEST')
        # pickup_description = create_quick_shipment_page.get_pickup_details()
        # logging.info('Courier Pickup Description for the above selected : ' + pickup_description)
        # deliver_to_description = create_quick_shipment_page.get_deliver_to_details()
        # logging.info('Courier Deliver Description for the above selected : ' + deliver_to_description)
        # payment_mode_type = create_quick_shipment_page.get_payment_type()
        # logging.info('Payment Type for Courier is : ' + payment_mode_type)
        # create_quick_shipment_page.select_option('BEST RATED')
        # logging.info('Selected the Courier Type as : BEST RATED')
        # pickup_description = create_quick_shipment_page.get_pickup_details()
        # logging.info('Courier Pickup Description for the above selected : ' + pickup_description)
        # deliver_to_description = create_quick_shipment_page.get_deliver_to_details()
        # logging.info('Courier Deliver Description for the above selected : ' + deliver_to_description)
        # payment_mode_type = create_quick_shipment_page.get_payment_type()
        # logging.info('Payment Type for Courier is : ' + payment_mode_type)
        # create_quick_shipment_page.select_option('CUSTOM')
        # logging.info('Selected the Courier Type as : CUSTOM')
        # pickup_description = create_quick_shipment_page.get_pickup_details()
        # logging.info('Courier Pickup Description for the above selected : ' + pickup_description)
        # deliver_to_description = create_quick_shipment_page.get_deliver_to_details()
        # logging.info('Courier Deliver Description for the above selected : ' + deliver_to_description)
        # payment_mode_type = create_quick_shipment_page.get_payment_type()
        # logging.info('Payment Type for Courier is : ' + payment_mode_type)
    except Exception as e:
        raise AssertionError("Exception is ", e)


@then('Check the Shipping Rates')
def check_the_shipping_rates():
    try:
        shipping_rates_calculator_page.visit()
        logging.info("Navigate to url")
        shipping_rates_calculator_page.fill_pickup_pincode_field('517501')
        logging.info('Enter pickup pin code')
        shipping_rates_calculator_page.fill_delivery_pincode_field('517644')
        logging.info('Enter deliver pin code')
        shipping_rates_calculator_page.fill_weight_field('20')
        logging.info('Enter weight in weight field')
        shipping_rates_calculator_page.fill_dimensions_length_field('19')
        logging.info('Enter dimensions length in length field')
        shipping_rates_calculator_page.fill_dimensions_breadth_field('16 ')
        logging.info('Enter dimensions breadth in breadth field')
        shipping_rates_calculator_page.fill_dimensions_height_field('18')
        logging.info('Enter dimensions height in height field')
        shipping_rates_calculator_page.fill_declared_value_field('25600')
        logging.info('Enter declared value')
        shipping_rates_calculator_page.click_calculate_button()
        logging.info('Click on calculate button')
        alert_message_no_couriers = create_quick_shipment_page.get_alert_message_no_couriers()
        logging.info('Alert Message When No Couriers : ' + alert_message_no_couriers)
        ShipRocket.driver.quit()
        # courier_type_air = shipping_rates_calculator_page.get_courier_type_air()
        # logging.info('Courier Type : ' + courier_type_air)
        # courier_air_list = shipping_rates_calculator_page.get_air_courier_provider_list()
        # for courier_list in courier_air_list:
        #     logging.info('Couriers List  : ' + courier_list)
        # courier_surface = shipping_rates_calculator_page.get_air_courier_rate_list()
        # courier_type_surface = shipping_rates_calculator_page.get_courier_type_surface()
        # logging.info('Courier Type : ' + courier_type_surface)
        # for courier_list in courier_surface:
        #     logging.info('Couriers List  : ' + courier_list)

    except Exception as e:
        raise AssertionError("Exception is ", e)
