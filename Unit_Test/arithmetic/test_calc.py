import unittest
import HtmlTestRunner
import calculations


class TestCalc(unittest.TestCase):

    def test_add(self):
        result = calculations.add(10, 5)
        self.assertEqual(result, 15)

    def test_subtract(self):
        result = calculations.subtract(10, 5)
        self.assertEqual(result, 5)

    def test_multiply(self):
        result = calculations.multiply(2, 3)
        self.assertEqual(result, 6)

    def test_divide(self):
        result = calculations.divide(10, 2)
        self.assertEqual(result, 5)

if __name__ == '__main__':
    # unittest.main()
    #unittest.main(testRunner=HtmlTestRunner.HTMLTestRunner(output='example_dir'))
    unittest.main(testRunner=HtmlTestRunner.HTMLTestRunner(output='example_dir', report_name="python-unit-test-results", add_timestamp=False, combine_reports=True))



