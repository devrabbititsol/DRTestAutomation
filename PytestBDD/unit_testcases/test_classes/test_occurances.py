import unittest

import HtmlTestRunner

from class_methods.log_parser import keyword_occurrences_any, keyword_occurrences_for


class KeywordOccurrencesTestCase(unittest.TestCase):

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

    def tearDown(self):
        del self.log_1
        del self.log_2
        del self.log_3

    def test_occurrences(self):
        # Test the for one occurrence in each line.
        occurrence_value = keyword_occurrences_any(['python', 'Network'], self.log_1)

        self.assertEqual(occurrence_value, 4)

        # Test 0 occurrences
        occurrence_value = keyword_occurrences_any(['python', 'Network'], self.log_2)

        self.assertEqual(occurrence_value, 0)

    def test_any_versus_for(self):
        occurrence_any = keyword_occurrences_any(['python', 'Network'], self.log_3)

        occurrence_for = keyword_occurrences_for(['python', 'Network'], self.log_3)

        # This is expected to fail because the keyword_occurrences_any method only searches by line
        # and returns a boolean if a keyword was found, but not multiple keywords.
        #
        # While as the keyword_occurrences_for method searches word by word and counts each word.
        # The for technique used a word by word count
        self.assertEqual(occurrence_any, occurrence_for)

        # Another technique for a passing test would be to check the inequality.
        # self.assertNotEqual(occurrence_any, occurrence_for)


if __name__ == "__main__":
    unittest.main(testRunner=HtmlTestRunner.HTMLTestRunner(output='example_dir', report_name="python-unit-test-results",
                                                           add_timestamp=False, combine_reports=True))