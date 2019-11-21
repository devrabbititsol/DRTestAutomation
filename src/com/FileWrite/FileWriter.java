package com.FileWrite;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;

public class FileWriter {
	 public void createFile() {
	        try {
	            //Whatever the file path is.
	            File statText = new File("C:/demo_files/TestCase.java");
	            FileOutputStream is = new FileOutputStream(statText);
	            OutputStreamWriter osw = new OutputStreamWriter(is);    
	            Writer w = new BufferedWriter(osw);
	            w.write("public class TestCase{"
	            		+ "}");
	            w.close();
	        } catch (IOException e) {
	            System.err.println("Problem writing to the file statsTest.txt");
	        }
}
}
