import unittest

import HtmlTestRunner


class SomeTest(unittest.TestCase):
    def setUp(self):
        super(SomeTest, self).setUp()
        self.mock_data = [1,2,3,4,5]

    def test(self):
        print(self.mock_data)
        self.assertEqual(len(self.mock_data), 5)
        print('list len is equal and assertion passed')


    def tearDown(self):
        super(SomeTest, self).tearDown()
        self.mock_data = []
        print(self.mock_data)


if __name__ == '__main__':
    unittest.main(testRunner=HtmlTestRunner.HTMLTestRunner(output='example_dir', report_name="python-unit-test-results",
                                                           add_timestamp=False, combine_reports=True))