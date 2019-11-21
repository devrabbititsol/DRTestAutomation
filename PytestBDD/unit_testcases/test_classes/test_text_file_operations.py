import unittest


class TestMethods(unittest.TestCase):

   def setUp(self):
       pass

   def test_finding_text(self):
       text = "Hello, welcome to my world."
       self.assertTrue(text.find("welcome"), 'Can not find a string in given sentence')
       print("Finding String in given setence is successfull")

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
       self.assertEqual(text.replace("fruits", "chocolate"), "I like chocolate", "String is not replacing with the given text")
       print("String replacement is successfull with the given text")

if __name__ == '__main__':
   unittest.main()