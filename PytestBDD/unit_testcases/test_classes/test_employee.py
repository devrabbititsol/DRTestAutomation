import unittest

import HtmlTestRunner

from class_methods.employe import Employee

class TestEmployee(unittest.TestCase):

    @classmethod
    def setUpClass(cls):
        print('setupclass')

    @classmethod
    def tearDownClass(cls):
        print('teardownclass')

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

if __name__ == "__main__":
    unittest.main(testRunner=HtmlTestRunner.HTMLTestRunner(output='example_dir', report_name="python-unit-test-results",
                                                           add_timestamp=False, combine_reports=True))
