import locustfile


class GlobalVariables():

        url = "http://dummy.restapiexample.com/api/v1/employees"
        url_swapi = "https://swapi.co/api/planets/3/"
        json_response=" "
        id=" "
        response_result=""
        post_data_reqres = {"name": "morpheus", "job": "leader"}
        post_url_reqres = "https://reqres.in/api/create"
        put_data_reqres = {"name": "morpheus", "job": "zion resident"}
        put_url_reqres = "https://reqres.in/api/user/"
        get_url_reqres = "https://reqres.in/api/user/"
        post_data_jsonplacer = {"userId": 1, "title": "delectus aut autem", "completed": "false" }
        url_jsonplacer = "https://jsonplaceholder.typicode.com/todos/"
        newsapi_url_topheadlines = "https://newsapi.org/v2/top-headlines?country=us&apiKey=03bebe1127dd427f881b36aa9be1f93c"
        newsapi_url_everything = "https://newsapi.org/v2/everything?q=bitcoin&apiKey=03bebe1127dd427f881b36aa9be1f93c"
        newsapi_url_sources = "https://newsapi.org/v2/sources?apiKey=03bebe1127dd427f881b36aa9be1f93c"
        newsapi_url_topheadlines_country = "https://newsapi.org/v2/top-headlines?country=us&apiKey=03bebe1127dd427f881b36aa9be1f93c"
