import logging
import unittest
import random
import HtmlTestRunner

logger = logging.getLogger()


def add(a, b):
    return a + b


def subtract(a, b):
    return a - b


def multiply(a, b):
    return a * b


def keyword_occurrences_any(keywords_list, logfile_str):
    occurrences = 0
    logfile_lines = logfile_str.split('\n')

    # Using the any technique will only count 1 per line
    for line in logfile_lines:
        if any(key in line for key in keywords_list):
            occurrences += 1

    return occurrences


def keyword_occurrences_for(keywords_list, logfile_str):
    occurrences = 0
    logfile_lines = logfile_str.split('\n')

    # Using the double for technique will get each word
    for line in logfile_lines:
        line_split = line.split(" ")
        for word in line_split:
            if word in keywords_list:
                occurrences += 1

    return occurrences


log_file = """
           systemd[1]: Starting python Network Manager Script Dispatcher Service...
	   systemd[1]: Started Network Manager Script Dispatcher Service.
	   nm-dispatcher: req:1 'dhcp4-change' [enp4s0]: new request (1 scripts)
	   nm-dispatcher: req:1 'dhcp4-change' [enp4s0]: start running ordered scripts...
	   systemd[1]: Starting Clean python session files...
	   systemd[1]: Started Clean python session files.
	   CRON[8283]: (agnosticdev) CMD (cd / && run-parts --report /etc/cron.hourly)
	   """
keywords = ['python', 'error', 'Network', 'failure']

matches = keyword_occurrences_any(keywords, log_file)
print("{0} matches found in the log file".format(matches))

matches = keyword_occurrences_for(keywords, log_file)
print("{0} matches found in the log file".format(matches))


class Employee:
    """A sample Employee class"""

    raise_amt = 1.05

    def __init__(self, first, last, pay):
        self.first = first
        self.last = last
        self.pay = pay

    @property
    def email(self):
        return '{}.{}@email.com'.format(self.first, self.last)

    @property
    def fullname(self):
        return '{} {}'.format(self.first, self.last)

    def apply_raise(self):
        self.pay = int(self.pay * self.raise_amt)


class Person:
    name = []

    def set_name(self, user_name):
        self.name.append(user_name)
        return len(self.name) - 1

    def get_name(self, user_id):
        if user_id >= len(self.name):
            return 'There is no such user'
        else:
            return self.name[user_id]


class TestUnittest(unittest.TestCase):

    def setUp(self):
        self.log_1 = """
        			       systemd[1]: Starting Network Manager Script Dispatcher Service...
        			       systemd[1]: Started Network Manager Script Dispatcher Service.
        			       systemd[1]: Starting Clean python session files...
        			       systemd[1]: Started Clean python session files.
        			       """

        self.log_2 = """
        			       systemd[1]: Starting Daemon Manager Script Dispatcher Service...
        			       systemd[1]: Started Daemon Manager Script Dispatcher Service.
        			       systemd[1]: Starting Clean php session files...
        			       systemd[1]: Started Clean php session files.
        			       """

        self.log_3 = """
        			       systemd[1]: Starting Network python Manager Script Dispatcher Service...
        			       systemd[1]: Started Network Manager Script Dispatcher Service.
        			       systemd[1]: Starting Clean python session files...
        			       systemd[1]: Started Clean python session files.
        			       """
        self.seq = list(range(10))
        self.lst = ['1', '2', '4']

        super(TestUnittest, self).setUp()
        self.mock_data = [1, 2, 3, 4, 5]

    def test_shuffle(self):
        # make sure the shuffled sequence does not lose any elements
        random.shuffle(self.seq)
        print(self.seq)
        self.seq.sort()
        self.assertEqual(self.seq, list(range(10)))
        print('both list are equal')

        # should raise an exception for an immutable sequence
        self.assertRaises(TypeError, random.shuffle, (1, 2, 3))

    def test_choice(self):
        element = random.choice(self.seq)
        self.assertTrue(element in self.seq)
        print(element, ' element is in seq')

    def test_sample(self):
        with self.assertRaises(ValueError):
            random.sample(self.seq, 20)
        for element in random.sample(self.seq, 5):
            self.assertTrue(element in self.seq)
            print(element, 'element is in seq')

    """
    The basic class that inherits unittest.TestCase
    """
    person = Person()  # instantiate the Person Class
    user_id = []  # variable that stores obtained user_id
    user_name = []  # variable that stores person name

    # test case function to check the Person.set_name function
    def test_0_set_name(self):
        print("Start set_name test\n")
        """
        Any method which starts with ``test_`` will considered as a test case.
        """
        for i in range(4):
            # initialize a name
            name = 'name' + str(i)
            # store the name into the list variable
            self.user_name.append(name)
            # get the user id obtained from the function
            user_id = self.person.set_name(name)
            # check if the obtained user id is null or not
            self.assertIsNotNone(user_id)  # null user id will fail the test
            # store the user id to the list
            self.user_id.append(user_id)
        print("user_id length = ", len(self.user_id))
        print(self.user_id)
        print("user_name length = ", len(self.user_name))
        print(self.user_name)
        print("\nFinish set_name test\n")

    # test case function to check the Person.get_name function
    def test_1_get_name(self):
        print("\nStart get_name test\n")
        """
        Any method that starts with ``test_`` will be considered as a test case.
        """
        length = len(self.user_id)  # total number of stored user information
        print("user_id length = ", length)
        print("user_name length = ", len(self.user_name))
        for i in range(6):
            # if i not exceed total length then verify the returned name
            if i < length:
                # if the two name not matches it will fail the test case
                self.assertEqual(self.user_name[i], self.person.get_name(self.user_id[i]))
            else:
                print("Testing for get_name no user test")
                # if length exceeds then check the 'no such user' type message
                self.assertEqual('There is no such user', self.person.get_name(i))
        print("\nFinish get_name test\n")

    def test_finding_text(self):
        text = "Hello, welcome to my world."
        self.assertTrue(text.find("welcome"), 'Can not find a string in given sentence')
        print("Finding String in given sentence is successful")

    def test_find_word_count(self):
        text = "Here I am testing a method"
        self.assertEqual(text.count("e"), 4, "Wrong Count")
        print("Counting Words along with spaces is successfully completed")

    def test_find_letter_index(self):
        text = "unittestcase"
        self.assertEqual(text.index("n"), 1, "Wrong Index")
        print("Correct Index number")

    def test_verify_digits(self):
        text = "50800"
        self.assertTrue(text.isdigit(), "Given text is not digit")
        print("Given text is digit")

    def test_replace_fun(self):
        text = "I like fruits"
        self.assertEqual(text.replace("fruits", "chocolate"), "I like chocolate",
                         "String is not replacing with the given text")
        print("String replacement is successfull with the given text")

    def test_occurrences(self):
        # Test the for one occurrence in each line.
        occurrence_value = keyword_occurrences_any(['python', 'Network'], self.log_1)

        self.assertEqual(occurrence_value, 4)
        print(occurrence_value)

        # Test 0 occurrences
        occurrence_value = keyword_occurrences_any(['python', 'Network'], self.log_2)

        self.assertEqual(occurrence_value, 0)
        print(occurrence_value)

    def test_any_versus_for(self):
        occurrence_any = keyword_occurrences_any(['python', 'Network'], self.log_3)

        occurrence_for = keyword_occurrences_for(['python', 'Network'], self.log_3)
        self.assertEqual(occurrence_any, occurrence_for)
        print(occurrence_any, occurrence_for)

    def test_email(self):
        emp_1 = Employee('test', 'smith', 50000)
        emp_2 = Employee('test2', 'smith2', 60000)

        self.assertEqual(emp_1.email, 'test.smith@email.com')
        print(emp_1.email)
        self.assertEqual(emp_2.email, 'test2.smith2@email.com')
        print(emp_2.email)

        emp_1.first = 'john'
        emp_2.first = 'dev'

        self.assertEqual(emp_1.email, 'john.smith@email.com')
        print(emp_1.email)
        self.assertEqual(emp_2.email, 'dev.smith2@email.com')
        print(emp_2.email)

    def test_fullname(self):
        emp_1 = Employee('test', 'smith', 50000)
        emp_2 = Employee('test2', 'smith2', 60000)

        self.assertEqual(emp_1.fullname, 'test smith')
        print(emp_1.fullname)
        self.assertEqual(emp_2.fullname, 'test2 smith2')
        print(emp_2.fullname)

        emp_1.first = 'john'
        emp_2.first = 'dev'

        self.assertEqual(emp_1.fullname, 'john smith')
        print(emp_1.fullname)
        self.assertEqual(emp_2.fullname, 'dev smith2')
        print(emp_2.fullname)

    def test_apply_raise(self):
        emp_1 = Employee('test', 'smith', 50000)
        emp_2 = Employee('test2', 'smith2', 60000)

        emp_1.apply_raise()
        emp_2.apply_raise()

        self.assertEqual(emp_1.pay, 52500)
        print(emp_1.pay)
        self.assertEqual(emp_2.pay, 63000)
        print(emp_2.pay)

    def test_add(self):
        result = add(10, 5)
        self.assertEqual(result, 15)
        print("addition is successful")

    def test_subract(self):
        result = subtract(10, 5)
        self.assertEqual(result, 5)
        print("subtraction is successful")

    def test_multiply(self):
        result = multiply(2, 3)
        self.assertEqual(result, 6)
        print("multiply is successful")

    def test_in_one(self):
        self.assertIn('1', self.lst)
        print("Assert verification passed")
        self.lst.remove('1')
        print(self.lst)
        print('Successfully removed')

    def test_in_two(self):
        self.assertIn('1', self.lst)
        print("Assert verification passed")

    def test(self):
        print(self.mock_data)
        self.assertEqual(len(self.mock_data), 5)
        print('list len is equal and assertion passed')

    # Returns True if the string contains 4 a.
    def test_strings_a(self):
        self.assertEqual('a' * 4, 'aaaa', 'string does not contains 4 a')
        print("string contains 4 a's ")

        # Returns True if the string is in upper case.

    def test_upper(self):
        self.assertEqual('foo'.upper(), 'FOO', 'string is not in upper case')
        print("string is in upper case 'foo' ")

        # Returns TRUE if the string is in uppercase

    # else returns False.
    def test_isupper(self):
        self.assertTrue('FOO'.isupper(), 'string is not upper cases')
        print("String is in upper case")
        self.assertFalse('Foo'.isupper())

        # Returns true if the string is stripped and

    # matches the given output.
    def test_strip(self):
        s = 'geeksforgeeks'
        self.assertEqual(s.strip('geek'), 'sforgeeks')
        print("string is stripped and equal ")

        # Returns true if the string splits and matches

    # the given output.
    def test_split(self):
        s = 'hello world'
        self.assertEqual(s.split(), ['hello', 'world'])
        print("string is splits and matches ")
        with self.assertRaises(TypeError):
            s.split(2)

    def tearDown(self):
        super(TestUnittest, self).tearDown()
        self.mock_data = []
        print(self.mock_data)


if __name__ == '__main__':
    unittest.main(testRunner=HtmlTestRunner.HTMLTestRunner(output='example_dir', report_name="python-unit-test-results",
                                                           add_timestamp=False, combine_reports=True))
