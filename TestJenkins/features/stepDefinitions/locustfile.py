from locust import TaskSet, task, seq_task, HttpLocust, TaskSequence
from GlobalVaribles import GlobalVariables


class UserBehavior(TaskSequence):
    @seq_task(1)
    def get_request(self):
        # try:
        print("SEQUENCE")
        response = self.client.get(GlobalVariables.url_swapi)
        print(response)
        response_data = response.json()
        print(response_data)

    @seq_task(2)
    def get_request_reqres(self):
        # try:
        print("SEQUENCE")
        response = self.client.get(GlobalVariables.get_url_reqres)
        print(response)
        response_data = response.json()
        print(response_data)

    @seq_task(3)
    def post_request_reqres(self):
        response = self.client.post(GlobalVariables.post_url_reqres,
                                    json=GlobalVariables.post_data_reqres)
        GlobalVariables.response_result = response.json()
        GlobalVariables.id = GlobalVariables.response_result.get("id")
        print(id)
        print(GlobalVariables.response_result)
        print("response id : " + str(response))

    @seq_task(4)
    def put_request_reqres(self):
        # print("SEQUENCE TASK : THREE")
        response = self.client.put(
            GlobalVariables.put_url_reqres
            + str(GlobalVariables.id),
            GlobalVariables.put_data_reqres)
        print(" put response id : " + str(response))
        print(response.text)
        self.interrupt()

    @task()
    def post_request_jsonplacer(self):
        response = self.client.post(GlobalVariables.url_jsonplacer,
                                    json=GlobalVariables.post_data_jsonplacer)
        GlobalVariables.response_result = response.json()
        GlobalVariables.id = GlobalVariables.response_result.get("id")
        print(id)
        print(GlobalVariables.response_result)
        print("response id : " + str(response))

    @task()
    def get_request_jsonplacer(self):
        response = self.client.get(GlobalVariables.url_jsonplacer)
        print(response)
        response_data = response.json()
        print(response_data)

    @task()
    def get_request_toheadlines_newapi(self):
        response = self.client.get(GlobalVariables.newsapi_url_topheadlines)
        print(response)
        response_data = response.json()
        print(response_data)

    @task()
    def get_request_everything_newapi(self):
        response = self.client.get(GlobalVariables.newsapi_url_everything)
        print(response)
        response_data = response.json()
        print(response_data)

    @task()
    def get_request_sources_newsapi(self):
        response = self.client.get(GlobalVariables.newsapi_url_sources)
        print(response)
        response_data = response.json()
        print(response_data)

    @task()
    def get_request_newaapi_country(self):
        response = self.client.get(GlobalVariables.newsapi_url_topheadlines_country)
        print(response)
        response_data = response.json()
        print(response_data)


class SubTaskSet(TaskSequence):
    @task()
    def get_request(self):
        print("WITHOUT SEQUENCE")
        response = self.client.get(GlobalVariables.url_swapi)
        # print(response.text)
        response_data = response.json()
        print(response_data)
        self.interrupt()


class Behave(TaskSet):
    tasks = {UserBehavior, SubTaskSet}


class WebsiteUser(HttpLocust):
    task_set = Behave
    host = "http://127.0.0.1:8089"
    max_wait = 1000
    min_wait = 1000
