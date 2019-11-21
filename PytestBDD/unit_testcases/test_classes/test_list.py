import unittest
import HtmlTestRunner

class MyTest(unittest.TestCase):

    def setUp(self):
        self.lst = ['1','2','4']

    def test_in_one(self):
        self.assertIn('1', self.lst)
        print("Assert verification passed")
        self.lst.remove('1')
        print(self.lst)
        print('Successfully removed')

    def test_in_two(self):
        self.assertIn('1', self.lst)
        print("Assert verification passed")


if __name__ == '__main__':
    unittest.main(testRunner=HtmlTestRunner.HTMLTestRunner(output='example_dir', report_name="python-unit-test-results",
                                                           add_timestamp=False, combine_reports=True))