import os

from selenium import webdriver
from selenium.webdriver.chrome.options import Options


class BaseClass:
    driver = webdriver
    project_path = os.path.dirname(os.path.dirname(__file__))

    @classmethod
    def launch_browser(cls):
        chrome_options = Options()
        chrome_options.add_argument('--headless')
        chrome_options.add_argument('--no-sandbox')
        chrome_options.add_argument('--disable-dev-shm-usage')
        cls.driver = webdriver.Chrome(executable_path='/usr/local/bin/chromedriver', chrome_options=chrome_options)
        # cls.driver = webdriver.Chrome(cls.project_path + '/drivers/chromedriver')
        cls.driver.maximize_window()
        cls.driver.get("https://parabank.parasoft.com/parabank/index.htm")
        return cls.driver

    @classmethod
    def feature_file_path(cls, feature):
        print("Feature file path : ", os.path.join(cls.project_path + "/features", feature))
        return os.path.join(cls.project_path + "/features", feature)
