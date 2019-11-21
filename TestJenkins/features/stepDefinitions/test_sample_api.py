import json
import os
from os.path import dirname
import request
import logging

import requests
from pytest_bdd import (
    given,
    scenario,
    then,
    when,
)



class API_Test():
    project_path = dirname(dirname(__file__))
    feature_file_path = os.path.join(project_path, 'sample_api.feature')
    uri = "https://reqres.in/api/users"
    get_response = None
    put_response = None
    post_response = None
    id = None
    test = None

    post_json = """{
            "name": "morpheus",
            "job": "leader",
            "id": "396",
            "createdAt": "2019-06-27T11:06:30.193Z"
        }"""
    put_json = """{
            "name": "john",
            "job": "manager",
            "id": "693",
            "createdAt": "2019-06-27T11:09:30.193Z"
        }"""


@scenario(API_Test.feature_file_path, 'GET request for Users API')
def test_get_request_for_users_api():
    """GET request for Users API."""


@given('URI for Users API for GET Request')
def uri_for_users_api_for_get_request():
    print("URI for Sample API ", API_Test.uri)


@when('User performs GET request')
def user_performs_get_request():
    API_Test.get_response = requests.get(API_Test.uri)


@then('I should see the response code as 200')
def i_should_see_the_response_code_as_200():
    print("Response Code for GET request : ",API_Test.get_response)
    print(
        "JSON Response for GET request ",json.dumps(API_Test.get_response.json(), indent=4)
    )


@scenario(API_Test.feature_file_path, 'POST request for Users API')
def test_post_request_for_users_api():
    """POST request for Users API."""


@given('URI for Users API for POST Request')
def uri_for_users_api_for_post_request():
    print("URI for Sample API ", API_Test.uri)

@when('User do POST request')
def user_do_post_request():
    API_Test.post_response = requests.post(API_Test.uri,API_Test.post_json)

@then('I should see response code as 201')
def i_should_see_response_code_as_201():
    print("Response Code for POST request ",API_Test.post_response)
    print("JSON Response for POST request ",json.dumps(API_Test.post_response.json(), indent=4))
    API_Test.id = API_Test.post_response.json().get("id")
    print("Generated ID with post request is ", API_Test.id)


@scenario(API_Test.feature_file_path, 'PUT request for Users API')
def test_put_request_for_users_api():
    """PUT request for Users API."""


@given('URI for Users API for PUT Request')
def uri_for_users_api_for_put_request():
    print("URI for Sample API ", API_Test.uri)


@when('User performs PUT request')
def user_performs_put_request():
    API_Test.put_request = requests.put(API_Test.uri + "/" + API_Test.id, API_Test.put_json)


@scenario(API_Test.feature_file_path, 'DELETE request for Users API')
def test_delete_request_for_users_api():
    """DELETE request for Users API."""


@given('URI for Users API for DELETE Request')
def uri_for_users_api_for_delete_request():
    print("URI for Sample API ", API_Test.uri)


@when('User performs DELETE request')
def user_performs_delete_request():
    API_Test.delete_request = requests.put(API_Test.uri + "/" + API_Test.id)


