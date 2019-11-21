# Python code to demonstrate working of unittest
import unittest

import HtmlTestRunner


class TestStringMethods(unittest.TestCase):

    def setUp(self):
        pass

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


if __name__ == '__main__':
    unittest.main(testRunner=HtmlTestRunner.HTMLTestRunner(output='example_dir', report_name="python-unit-test-results",
                                                           add_timestamp=False, combine_reports=True))