"""Notifii API feature tests."""
import http
import json
import os
import time
import datetime
import doctest
import pytest

import requests
from logger import logger
from pytest_bdd import (
    given,
    scenario,
    then,
    when,
)
directory=(os.path.dirname(os.path.dirname(os.path.abspath(__file__))))
feature_file_path=(directory + '/notifii_api.feature')

class ValidationExecutionError:
    pass

class Notifii:
    login_uri="https://devrabbitdev.com/api/track4/app-login.php"
    get_globals_uri= "https://devrabbitdev.com/api/track4/get-global-constants.php"
    add_receipient_uri= "https://devrabbitdev.com/api/track4/add-recipient.php"
    update_recipient_uri= "https://devrabbitdev.com/api/track4/update-recipient.php"
    get_recipient_uri ="https://devrabbitdev.com/api/track4/get-recipient.php"
    recipient_list_uri="https://staging.notifii.com/api/track4/recipient-list.php"
    logout_uri ="https://devrabbitdev.com/api/track4/app-logout.php"
    login_post ="""{
    "username": "devacc1",
    "password": "devacc1@123",
    "session_timedout": "3 years",
    "version": "4.4"
}"""
    globals_json="""{
    "session_id": "sessionid",
    "authentication_token": "tocken",
    "account_id": "accountid",
    "user_id": "userid",
    "user_type_id": "10001"
}"""
    add_receipient_post= """{
    "session_id": "sessionid",
    "authentication_token": "tocken",
    "account_id": "accountid",
    "first_name": "Devrabbit2",
    "last_name": "Resident12",
    "address1": "D1",
    "email": "",
    "cellphone": "",
    "send_track_nonmarketing_email": "0",
    "send_track_nonmarketing_text": "0",
    "recipient_status": "1",
    "recipient_type": "31",
    "special_track_instructions_flag": "none",
    "special_track_instructions": "",
    "vacation_start_date": "",
    "vacation_end_date": "",
    "send_pkg_login_notification": "a",
    "send_pkg_logout_notification": "a"
}"""
    update_recipient_json="""{
    "session_id": "sessionid",
    "authentication_token": "tocken",
    "account_id": "accountid",
    "recipient_id": "recipientid",
    "first_name": "Devrabbit",
    "last_name": "Resident1",
    "address1": "D1",
    "email": "Devrabbit@yopmail.com",
    "cellphone": "",
    "send_track_nonmarketing_email": "1",
    "send_connect_nonmarketing_email": "1",
    "send_connect_marketing_email": "1",
    "send_track_nonmarketing_text": "1",
    "send_connect_nonmarketing_text": "1",
    "send_connect_marketing_text": "1",
    "send_track_nonmarketing_push": "0",
    "send_connect_nonmarketing_push": "0",
    "send_connect_marketing_push": "0",
    "stop_track_nonmarketing_email": "0",
    "stop_connect_nonmarketing_email": "0",
    "stop_connect_marketing_email": "0",
    "stop_track_nonmarketing_text": "0",
    "stop_connect_nonmarketing_text": "0",
    "stop_connect_marketing_text": "0",
    "stop_track_nonmarketing_push": "0",
    "stop_connect_nonmarketing_push": "0",
    "stop_connect_marketing_push": "0",
    "recipient_status": "1",
    "recipient_type": "31",
    "special_track_instructions_flag": "none",
    "special_track_instructions": "",
    "vacation_status": "1",
    "vacation_start_date": "2019-07-24",
    "vacation_end_date": "2019-07-30",
    "move_in_date": "",
    "move_out_date": "",
    "lease_start_date": "",
    "lease_end_date": "",
    "birth_date": "",
    "send_pkg_login_notification": "a",
    "send_pkg_logout_notification": "a"
}"""
    get_recipient_json = """{
    "session_id": "sessionid",
    "authentication_token": "tocken",
    "account_id": "accountid",
    "recipient_id": "recipientid"
}"""
    recipient_list_json= """{
    "session_id": "sessionid",
    "authentication_token": "tocken",
    "account_id": "accountid"
}"""
    logout_json= """{
    "session_id": "sessionid",
    "authentication_token": "tocken",
    "account_id": "accountid",
    "user_id": "userid"
}"""
    login_response= None
    session_id= None
    tocken =None
    account_id =None
    user_id =None
    globals_response =None
    recipient_id =None

@scenario(feature_file_path, 'POST request for Notifii login API')
def test_post_request_for_notifii_login_api():
    """POST request for Notifii login API."""

@given('URI for Notifii login API POST Request')
def uri_for_notifii_login_api_post_request():
    logger.info("URI for Login in Notifii API %s", Notifii.login_uri)

@when('User performs POST request')
def user_performs_post_request():
    Notifii.login_response= requests.post(Notifii.login_uri, Notifii.login_post)
    Notifii.session_id= Notifii.login_response.json().get("session_id")
    Notifii.tocken = Notifii.login_response.json().get("authentication_token")
    Notifii.user_id= Notifii.login_response.json().get("user_id")
    Notifii.account_id = Notifii.login_response.json().get("account_id")
    logger.info("Session ID is %s", Notifii.session_id)
    logger.info("Authentication tocken is %s", Notifii.tocken)
    logger.info("User ID is %s", Notifii.user_id)
    logger.info("Account id is %s", Notifii.account_id)

@then('User should see the response code as 200')
def user_should_see_the_response_code_as_200():
    if Notifii.login_response.status_code != http.HTTPStatus.OK:
        raise ValidationExecutionError
    logger.info("Response Code for POST request : %s",Notifii.login_response)
    logger.info(
        "JSON Response for POST request %s",json.dumps(Notifii.login_response.json(), indent=4)
    )

@scenario(feature_file_path, 'POST request for get GlobalConstants in Notifii API')
def test_post_request_for_get_globalconstants_in_notifii_api():
    """POST request for get GlobalConstants in Notifii API."""

@given('URI for get GlobalConstants in Notifii API')
def uri_for_get_globalconstants_in_notifii_api():
    logger.info("URI for get GlobalConstants Constants API %s", Notifii.get_globals_uri)

@when('User do POST request for GlobalConstants')
def user_do_post_request_for_globalconstants():
    Notifii.add_receipient_post = Notifii.add_receipient_post.replace("sessionid",Notifii.session_id).replace(
        "tocken",Notifii.tocken).replace("accountid",Notifii.account_id).replace("userid",Notifii.user_id)
    Notifii.login_response = requests.post(Notifii.get_globals_uri, Notifii.add_receipient_post)

@scenario(feature_file_path, 'POST request for Add Receipient')
def test_post_request_for_add_receipient():
    """POST request for Add Receipient."""

@given('URI for Add Receipient POST request')
def uri_for_add_receipient_post_request():
    logger.info("URI for Add Recipient API %s", Notifii.add_receipient_uri)

@when('User do POST request for Add Receipient')
def user_do_post_request_for_add_receipient():
    Notifii.add_receipient_post = Notifii.add_receipient_post.replace("sessionid",Notifii.session_id).replace(
        "tocken",Notifii.tocken).replace("accountid",Notifii.account_id)
    Notifii.login_response = requests.post(Notifii.add_receipient_uri, Notifii.add_receipient_post)
    Notifii.recipient_id = Notifii.login_response.json().get("recipient_id")
    logger.info("Receipient ID is %s",Notifii.recipient_id)
    logger.info("Response code for Add Receipient %s", Notifii.login_response)

@scenario(feature_file_path, 'POST request for Update Receipient')
def test_post_request_for_update_receipient():
    """POST request for Update Receipient."""

@given('URI for Update Receipient POST request')
def uri_for_update_receipient_post_request():
    logger.info("URI for Login in Notifii API %s", Notifii.add_receipient_uri)

@when('User do Post request for Update Receipient')
def user_do_post_request_for_update_receipient():
    Notifii.update_recipient_json = Notifii.update_recipient_json.replace("sessionid",Notifii.session_id).replace(
        "tocken",Notifii.tocken).replace("accountid",Notifii.account_id).replace("recipientid",Notifii.recipient_id)
    Notifii.login_response = requests.post(Notifii.update_recipient_uri, Notifii.update_recipient_json)

@scenario(feature_file_path, 'POST request for Get Receipient')
def test_post_request_for_get_receipient():
    """POST request for Get Receipient."""

@given('URI for Get Receipient POST request')
def uri_for_get_receipient_post_request():
    logger.info("URI for Get Recipient API %s", Notifii.get_recipient_uri)

@when('User do Post request for Get Receipient')
def user_do_post_request_for_get_receipient():
    Notifii.get_recipient_json = Notifii.get_recipient_json.replace("sessionid",Notifii.session_id).replace(
        "tocken",Notifii.tocken).replace("accountid",Notifii.account_id).replace("recipientid",Notifii.recipient_id)
    logger.info("Json data for get recipient is %s", Notifii.get_recipient_json)
    Notifii.login_response = requests.post(Notifii.get_recipient_uri, Notifii.get_recipient_json)

@scenario(feature_file_path, 'POST request for Receipient list')
def test_post_request_for_receipient_list():
    """POST request for Receipient list."""

@given('URI for Receipient list POST request')
def uri_for_receipient_list_post_request():
    logger.info("URI for Get Recipient list %s", Notifii.recipient_list_uri)

@when('User do Post request for Receipient list')
def user_do_post_request_for_receipient_list():
    Notifii.recipient_list_json = Notifii.recipient_list_json.replace("sessionid",Notifii.session_id).replace(
        "tocken",Notifii.tocken).replace("accountid",Notifii.account_id)
    Notifii.login_response = requests.post(Notifii.recipient_list_uri, Notifii.recipient_list_json)
    logger.info("Json data for get recipient list is %s", Notifii.recipient_list_json)


@scenario(feature_file_path, 'POST request for Logout in Notifii API')
def test_post_request_for_logout_in_notifii_api():
    """POST request for Logout in Notifii API."""


@given('URI for Logout in Notifii API POST request')
def uri_for_logout_in_notifii_api_post_request():
    logger.info("URI for Logout in Notifii API %s", Notifii.logout_uri)


@when('User do Post request for Logout')
def user_do_post_request_for_logout():
    Notifii.logout_json = Notifii.logout_json.replace("sessionid",Notifii.session_id).replace(
        "tocken",Notifii.tocken).replace("accountid",Notifii.account_id).replace("userid",Notifii.user_id)
    Notifii.login_response = requests.post(Notifii.logout_uri, Notifii.logout_json)
