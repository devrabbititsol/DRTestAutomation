import http
import json
import os

import requests
from logger import logger
from pytest_bdd import (
    given,
    scenario,
    then,
    when,
)
import time
import datetime
import doctest
import pytest
directory=(os.path.dirname(os.path.dirname(os.path.abspath(__file__))))
feature_file_path=(directory + '/news_api_topheadlines.feature')

class ValidationExecutionError(object):
    pass

class NewsAPI:
    news_headlines_api= "https://newsapi.org/v2/top-headlines?sources=google-news&apiKey=03bebe1127dd427f881b36aa9be1f93c"
    source_api= "https://newsapi.org/v2/top-headlines?sources=google-news&apiKey=03bebe1127dd427f881b36aa9be1f93c"
    everything_api= "https://newsapi.org/v2/everything?q=bitcoin&apiKey=03bebe1127dd427f881b36aa9be1f93c"
    country_api= "https://newsapi.org/v2/top-headlines?country=us&apiKey=03bebe1127dd427f881b36aa9be1f93c"
    get_response= None

@scenario(feature_file_path, 'GET request for News Headlines API')
def test_get_request_for_news_headlines_api():
    """GET request for News Headlines API."""

@given('URI for New Headlines API for GET Request')
def uri_for_new_headlines_api_for_get_request():
    logger.info("URI for New Headlines API is %s", NewsAPI.news_headlines_api)

@when('User performs GET request')
def user_performs_get_request():
    NewsAPI.get_response= requests.get(NewsAPI.news_headlines_api)

@then('I should see the response code as 200')
def i_should_see_the_response_code_as_200():
    if NewsAPI.get_response.status_code !=http.HTTPStatus.OK:
        raise ValidationExecutionError
    logger.info("Response Code for GET request : %s",NewsAPI.get_response)
    logger.info(
        "JSON Response for GET request %s",json.dumps(NewsAPI.get_response.json(), indent=4)
    )

@scenario(feature_file_path, 'GET request for sources API')
def test_get_request_for_sources_api():
    """GET request for sources API."""

@given('URI for sources API for GET Request')
def uri_for_sources_api_for_get_request():
    logger.info("URI for Source API is %s",NewsAPI.source_api)


@when('User performs GET request for sources')
def user_performs_get_request_for_sources():
    NewsAPI.get_response=requests.get(NewsAPI.source_api)

@scenario(feature_file_path, 'GET request for Everything API')
def test_get_request_for_everything_api():
    """GET request for Everything API."""


@given('URI for Everything API for GET Request')
def uri_for_everything_api_for_get_request():
    logger.info("URI for Everything API is %s",NewsAPI.everything_api)

@when('User performs GET request for Everything')
def user_performs_get_request_for_everything():
    NewsAPI.get_response=requests.get(NewsAPI.everything_api)

@scenario(feature_file_path, 'GET request for News Headlines with Country API')
def test_get_request_for_news_headlines_with_country_api():
    """GET request for News Headlines with Country API."""

@given('URI for Country API for GET Request')
def uri_for_country_api_for_get_request():
    logger.info("URI for Everything API is %s",NewsAPI.country_api)

@when('User performs GET request for Country')
def user_performs_get_request_for_country():
    NewsAPI.get_response =requests.get(NewsAPI.country_api)