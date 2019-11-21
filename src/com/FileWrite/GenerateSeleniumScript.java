package com.FileWrite;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Date;

import com.dto.DataSetValuesVo;
import com.dto.ScreenDTO;
import com.dto.ScreenElementDTO;
import com.dto.TestCaseElementsVo;
import com.google.gson.Gson;

public class GenerateSeleniumScript {

	private static String envVar;

	public void generateCode(TestCaseElementsVo output) throws Exception {

		System.out.println("testcaseid: " + output.getTestcaseId());
		System.out.println("url: " + output.getUrl());
		System.out.println("" + output.getTestcaseName());

		for (ScreenDTO screen : output.getScreens()) {
			System.out.println("" + screen.getScreenId());
			System.out.println("screen name" + screen.getScreenName());

			for (ScreenElementDTO elements : screen.getScreenElements()) {
				System.out.println("" + elements.getElementId());
				System.out.println("" + elements.getProp_name());
				System.out.println("" + elements.getDataSetValue());
				System.out.println("" + elements.getXpath());
				System.out.println("" + elements.isSelected());
			}
		}

		GenerateSeleniumScript(output);

	}

	public static void GenerateSeleniumScript(TestCaseElementsVo output) throws IOException, Exception {

		envVar = System.getenv("AUTOMATION_PATH");
		
		String str = "", s1, TCName, s2, s3, Screen1, s4, Screen2, s5, s6, s7, s8, s9, s10 = "", s11 = "", s12 = "",
				s13 = "";

		String ReportsPath = "reportsPath", chromeDriverPath = "chromeDriverPath", BrowserName, URL,
				pageObjectClassName, classObjects = "", s1Next = "";
		String FieldType = "", FieldName = "", FieldValue = "", fieldsInfo = "", screenName = "",
				propertiesInfo = "\n\t", testClassPath = "", xmlFile = "";
		BrowserName = "Chrome";
		// URL = "http://devrabbit.com";
		System.out.println("" + output.getTestcaseName());
		TCName = toTitleCase(output.getTestcaseName());
		URL = output.getUrl();
		propertiesInfo += "URL = " + URL + "\n\t";
		xmlFile = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<!DOCTYPE suite SYSTEM \"http://testng.org/testng-1.0.dtd\" >\n<suite name=\"Main Test Suite\" verbose=\"1\">\n\t<test name=\"test\">\n\t\t<classes>\n\t\t\t<class name=\"testclasses."
				+ TCName + "\" />\n\t\t</classes>\n\t\t</test>\n\t</suite>";
		// chromeDriverPath ="D:\\Framework\\Resources";
		writeFile(xmlFile, "testng", envVar, ".xml");
		String configFile = writeXml(TCName,TCName,TCName);
		writeFile(configFile, "extent-config", envVar, ".xml");
		testClassPath = envVar+"\\src\\test\\java\\testclasses";
		for (ScreenDTO screen : output.getScreens()) {
			// System.out.println(""+screen.getScreenId());
			System.out.println("screen name" + screen.getScreenName());
			screenName = toTitleCase(screen.getScreenName());
			fieldsInfo = "";
			for (ScreenElementDTO elements : screen.getScreenElements()) {
				System.out.println("" + elements.getElementId());
				System.out.println("" + elements.getProp_name());
				System.out.println("" + elements.getDataSetValue());
				System.out.println("" + elements.isSelected());

				if (elements.getFieldType().equals("InputText"))
					propertiesInfo += toTitleCase(elements.getProp_name()) + " = " + elements.getDataSetValue()
							+ "\n\t";
				else if (elements.getFieldType().equals("Label") && elements.isSelected())
					propertiesInfo += toTitleCase(elements.getProp_name()) + " = " + elements.getProp_name() + "\n\t";
				else if ((elements.getFieldType().equalsIgnoreCase("Link")||elements.getFieldType().equalsIgnoreCase("Link")) && elements.isSelected())
					propertiesInfo += toTitleCase(elements.getProp_name()) + " = " + elements.getProp_name() + "\n\t";

				// fieldsInfo
				// ="popUpCloseBtn##Button##xpath1&&EnterInputValue##InputBox##xpath2";
				if (fieldsInfo.equals(""))
					fieldsInfo += elements.getProp_name() + "##" + elements.getFieldType() + "##"
							+ elements.getDataSetValue() + "##" + elements.getXpath() + "##" + elements.isSelected();
				else
					fieldsInfo += "&&" + elements.getProp_name() + "##" + elements.getFieldType() + "##"
							+ elements.getDataSetValue() + "##" + elements.getXpath() + "##" + elements.isSelected();

			}
			GenerateSeleniumScreenObjectScript(screenName, fieldsInfo);

			classObjects += "public " + screenName + " obj" + screenName + ";\n\t";
			s1Next += "import pageclasses." + screenName + ";\n";
		}
		writeFile(propertiesInfo, "config", envVar+"\\ConfigurationSetUp", ".properties");

		s1 = "package testclasses;\nimport java.util.concurrent.TimeUnit;\nimport org.apache.http.util.Asserts;\nimport org.apache.log4j.Logger;\nimport org.apache.log4j.PropertyConfigurator;\nimport org.openqa.selenium.chrome.ChromeDriver;\nimport org.openqa.selenium.support.PageFactory;\nimport org.testng.ITestResult;\nimport org.testng.annotations.AfterClass;\nimport org.testng.annotations.BeforeClass;\nimport org.testng.annotations.Test;\nimport com.relevantcodes.extentreports.ExtentReports;\nimport java.io.File;\nimport com.relevantcodes.extentreports.ExtentTest;\nimport com.relevantcodes.extentreports.LogStatus;\n";
		// s1Next ="import pageclasses.Demo_LogOutPage;\nimport
		// pageclasses.Demo_LoginPage;\nimport
		// pageclasses.Demo_RegistrationPage;\nimport utilities.BaseClass;\n\timport
		// utilities.ConfigFilesUtility;\n\timport utilities.Utilities;\npublic class ";
		s1 += s1Next
				+ "import utilities.BaseClass;\nimport utilities.ConfigFilesUtility;\nimport utilities.Utilities;\npublic class ";
		s2 = TCName
				+ " extends BaseClass {\n\tExtentReports reports;\n\tExtentTest test;\n\tITestResult result;\n\tprivate Logger logger;\n\tprivate ConfigFilesUtility configFileObj;\n\t";

		// temp = "public ";
		// Screen1="ScreenName1";
		// s3=temp+Screen1+" obj"+Screen1;
		// Screen2="ScreenName2";
		// s3=s3+"\n\t"+temp+Screen2+" obj"+Screen2;
		s3 = classObjects;

		s4 = "\n\tString projectPath=System.getProperty(\"user.dir\");\n\tString chromeDriverPath=projectPath+\"/Resources/chromedriver.exe\";\n\tString reportsPath=projectPath+\"/Reports/"+TCName+".html\";\n\tpublic ";
		s5 = TCName
				+ "() throws Exception {\n\t\tPropertyConfigurator.configure(\"log4j.properties\");\n\t\tlogger = Logger.getLogger(";
		s6 = TCName
				+ ".class);\n\t\tconfigFileObj= new ConfigFilesUtility();\n\t\tconfigFileObj.loadPropertyFile();\n\t\treports = new ExtentReports(";
		s7 = ReportsPath
				+ ", true);\n\t\t\treports.loadConfig(new File(projectPath+\"/extent-config.xml\"));}\n\t@BeforeClass\n\tpublic void setUP() {\n\t\tSystem.setProperty(\"webdriver.chrome.driver\", ";
		s8 = chromeDriverPath + ");\n\t\t";

		s9 = "driver = new ChromeDriver();\n\t\tlogger.info(\"" + BrowserName
				+ " Browser is Launched\");\n\t\tdriver.manage().deleteAllCookies();\n\t\tdriver.get(configFileObj.getProperty(\"URL\"));\n\t\tlogger.info(\"Application is Launched\");\n\t\tdriver.manage().window().maximize();\n\t\tdriver.manage().timeouts().implicitlyWait(120, TimeUnit.SECONDS);\n\t}\n\t";
		// pageObjectClassName ="Screen1";
		int temp = 1;
		str += s1 + s2 + s3 + s4 + s5 + s6 + s7 + s8 + s9;
		for (ScreenDTO screen : output.getScreens()) {
			System.out.println("" + screen.getScreenId());
			System.out.println("screen name" + screen.getScreenName());
			screenName = toTitleCase(screen.getScreenName());
			s10 = "@Test(priority=" + temp + ")\n\tpublic void " + screenName + "Test() throws Exception {\n\t\tobj"
					+ screenName + " = PageFactory.initElements(driver, " + screenName
					+ ".class);\n\t\ttest = reports.startTest(\"Verify " + screenName + " page\");\n\t\t";
			s11 = "";
			int i=0;
			for (ScreenElementDTO elements : screen.getScreenElements()) {
				i = i+1;
				// System.out.println(""+elements.getElementId());
				System.out.println("" + elements.getProp_name());
				System.out.println("" + elements.getDataSetValue());
				System.out.println("" + elements.getFieldType());
				FieldName = elements.getProp_name();
				FieldValue = elements.getDataSetValue();
				FieldType = elements.getFieldType();
				Boolean FieldType1 = elements.isSelected();
				if (FieldType.equals("Button")) {
					if (FieldType1) {
						s11 +=  "\t\t\tString text"+i+" = obj" + screenName + ".Validate" + toTitleCase(FieldName)
								+ "();\n\t\t"
								+ "if(text"+i+".equalsIgnoreCase(configFileObj.getProperty(\""+toTitleCase(FieldName)+"\"))){"
								+"\n\t\ttest.log(LogStatus.PASS, \"Text is displayed as :  "+ toTitleCase(FieldName)+"\");"
								+ "\n\t\tlogger.info(\"Text is displayed as :" + toTitleCase(FieldName)+"\");"
								+ "\n\t\t}else{"
								+ "\n\t\ttest.log(LogStatus.FAIL, \"Text is displayed as : "+ toTitleCase(FieldName)+"\");"
								+ "\n\t\tlogger.error(\"Text is displayed as :" + toTitleCase(FieldName)+"\");"
								+ "\n\t\ttest.log(LogStatus.INFO, \"Screenshot Taken : \"+Utilities.captureScreenshot(driver,\""+toTitleCase(FieldName)+ " is Failed\"));"
								+ "\n\t\t}\n";
					} else {
					s11 += "String text = obj" + screenName + ".clk" + toTitleCase(FieldName) + "();\n\t\tlogger.info(\"Clicked on "
							+ toTitleCase(FieldName) + "Button\");\n\t\ttest.log(LogStatus.INFO, \"Clicked on "
							+ toTitleCase(FieldName) + "Button on the Pop Up\");\n\t\t";
					}
				} else if (FieldType.equals("Link")) {
					if (FieldType1)
						s11 +=  "\t\t\tString text"+i+" = obj" + screenName + ".Validate" + toTitleCase(FieldName) 
								+ "();\n\t\t"
								+ "if(text"+i+".equalsIgnoreCase(configFileObj.getProperty(\""+toTitleCase(FieldName)+"\"))){"
								+"\n\t\ttest.log(LogStatus.PASS, \"Text is displayed as :  "+ toTitleCase(FieldName)+"\");"
								+ "\n\t\tlogger.info(\"Text is displayed as :" + toTitleCase(FieldName)+"\");"
								+ "\n\t\t}else{"
								+ "\n\t\ttest.log(LogStatus.FAIL, \"Text is displayed as : "+ toTitleCase(FieldName)+"\");"
								+ "\n\t\tlogger.error(\"Text is displayed as :" + toTitleCase(FieldName)+"\");"
								+ "\n\t\ttest.log(LogStatus.INFO, \"Screenshot Taken : \"+Utilities.captureScreenshot(driver,\""+toTitleCase(FieldName)+ " is Failed\"));"
								+ "\n\t\t}\n";

					else
					s11 += "obj" + screenName + ".clk" + toTitleCase(FieldName) 
							+ "();\n\t\tlogger.info(\"Clicked on "+ toTitleCase(FieldName) 
							+ " Link\");\n\t\ttest.log(LogStatus.INFO, \"Clicked on "
							+ toTitleCase(FieldName) + "Link\");\n\t\t";
				} else if (FieldType.equals("InputText")) {
					s11 += "obj" + screenName + ".fill" + toTitleCase(FieldName) + "(configFileObj.getProperty(\""
							+ toTitleCase(FieldName) + "\"));\n\t\tlogger.info(\"Entered " + FieldValue
							+ "\");\n\t\ttest.log(LogStatus.INFO, \"Entered " + FieldValue + "\");\n\t\t";
				} else if (FieldType.equals("Label")) {
					if (FieldType1)
						s11 +=  "\t\t\tString text"+i+" = obj" + screenName + ".Validate" + FieldName.toString().split(" ")[0]
								+ "();\n\t\t"
								+ "if(text"+i+".equalsIgnoreCase(configFileObj.getProperty(\""+toTitleCase(FieldName)+"\"))){"
								+"\n\t\ttest.log(LogStatus.PASS, \"Text is displayed as :  "+ toTitleCase(FieldName)+"\");"
								+ "\n\t\tlogger.info(\"Text is displayed as :" + toTitleCase(FieldName)+"\");"
								+ "\n\t\t}else{"
								+ "\n\t\ttest.log(LogStatus.FAIL, \"Text is displayed as : "+ toTitleCase(FieldName)+"\");"
								+ "\n\t\tlogger.error(\"Text is displayed as :" + toTitleCase(FieldName)+"\");"
								+ "\n\t\ttest.log(LogStatus.INFO, \"Screenshot Taken : \"+Utilities.captureScreenshot(driver,\""+toTitleCase(FieldName)+ " is Failed\"));"
								+ "\n\t\t}\n";

					else
					{
						s11 += "obj" + screenName + ".Label" + FieldName.toString().split(" ")[0]
								+ "();\n\t\tlogger.info(\"Getting the text value:  " + toTitleCase(FieldName)
								+ "\");\n\t\ttest.log(LogStatus.WARNING, \"Getting the text value:  "
								+ toTitleCase(FieldName) + "\");\n\t\t";
					}
				}

			}
			s12 = "\n\t}\n\t";
			str += s10 + s11 + s12;
			temp++;
		}
		s13 = "\n\t@AfterClass\n\tpublic void tearDown() throws Exception {\n\t\treports.endTest(test);\n\t\treports.flush();\n\t\tdriver.quit();\n\t}\n}";
		str += s13;

		writeFile(str, toTitleCase(TCName), testClassPath, ".java");
		
		Thread.sleep(10000);
		BacthFileExecution();
	}

	public static void GenerateSeleniumScreenObjectScript(String screenName, String fieldsInfo) throws IOException {

		String labelCheck = "false";
		String str, str1, str2, str3, xpath, screenNanme, str4 = "", str5, fieldName, str6, funcName,
				fieldType = "Button", fieldNamePrefix = "";
		// String fieldsInfo ="popUpCloseBtn&&clk&&//div[@id='welcome_close']/a[1]";
		String str7 = "", str8 = "", str9 = "", str10 = "", strTemp = "", fieldValue = "", fieldName1 = "", text = "",
				returnString = "", strReturn = "";
		String[] fieldValues, fieldString;
		String classPath = "", Beforestr9 = "", Beforestr6 = "";
		classPath = envVar+"\\src\\test\\java\\pageclasses";
		// fieldsInfo
		// ="popUpCloseBtn##Button##xpath1&&EnterInputValue##InputBox##xpath2";
		// fieldsInfo =fieldsInfo;
		str1 = "package pageclasses;\nimport org.openqa.selenium.WebDriver;\nimport org.openqa.selenium.WebElement;\nimport org.openqa.selenium.support.FindBy;\nimport org.openqa.selenium.support.How;\nimport utilities.BaseClass;\npublic class ";
		// screenNanme = "ClassName-1";
		str2 = " extends BaseClass {\n\tpublic WebDriver driver;\n\tpublic ";
		str3 = " (WebDriver driver) {\n\t\tthis.driver = driver;\n\t}";

		str4 = "\n\t@FindBy(how = How.XPATH, using = \"";
		// xpath = "//div[@id='welcome_close']/a[1]";
		str5 = "\")\tprivate WebElement\t";

		str = str1 + screenName + str2 + screenName + str3;
		// fieldsInfo
		// ="popUpCloseBtn##Button##xpath1&&EnterInputValue##InputBox##xpath2";
		if (!fieldsInfo.isEmpty()) {
			fieldString = fieldsInfo.split("&&");
			System.out.println("fieldString.length: " + fieldString.length);
			for (int i = 0; i < fieldString.length; i++) {
				Beforestr6 = "()";
				str6 = " {\n\t\ttry {\n\t\t\twaitForExpectedElement(driver, ";
				// fieldName = "popUpCloseBtn";
				str7 = ";\n\tpublic void ";
				returnString = ";\n\tpublic String ";
				str8 = ");\n\t\t\t";
				Beforestr9 = ".click();";
				text = ".getText();";
				str9 = "\n\t\t} catch (Exception ex) {\n\t\t\tSystem.out.println(ex.getMessage());\n\t\t\tSystem.out.println(\"Element is not displayed \");\n\t\t}\n\t\t\treturn \"\";\n}";
				str10 = "\n}";

				fieldValues = fieldString[i].split("##");
				fieldName = fieldValues[0];
				fieldType = fieldValues[1];
				fieldValue = fieldValues[2];
				xpath = fieldValues[3];
				labelCheck = fieldValues[4];

				if (fieldType.equals("Button") || fieldType.equals("Link")) {
					if (labelCheck.equals("true")) {
						fieldNamePrefix = "Validate";

						System.out.println("********************FieldName1*********************: " + fieldValue);
					} else {
					fieldNamePrefix = "clk";
					}
					fieldName1 = toTitleCase(fieldName);

					str6 = Beforestr6 + str6;

					System.out.println(
							"********************FieldName1 in Link and  Button*********************: " + fieldName1);
				} else if (fieldType.equals("InputText")) {
					fieldNamePrefix = "fill";
					fieldName1 = toTitleCase(fieldName);
					str6 = "(String varInputValue)" + str6;
					str9 = ".sendKeys(varInputValue);" + str9;

					System.out
							.println("********************FieldName1 in InputText*********************: " + fieldName1);
				} else if (fieldType.equals("Label")) {
					if (labelCheck.equals("true")) {
						fieldNamePrefix = "Validate";

						System.out.println("********************FieldName1*********************: " + fieldValue);
					} else {
						fieldNamePrefix = "Label";
						System.out.println("********************FieldName1*********************: " + fieldValue);
					}
					fieldName1 = fieldName.toString().split(" ")[0];
					// str9=Beforestr9+"\n\t\treturn "+toTitleCase(fieldName1)+text+str9;
					str6 = Beforestr6 + str6;
					System.out.println("********************FieldName1 in Label*********************: " + fieldName1);
				}
				// System.out.println("********************FieldName1 Before IF
				// condition*********************: "+fieldName1);
				/*
				 * if(fieldType.equals("Button")||fieldType.equals("Link")) {
				 * funcName=fieldNamePrefix+fieldName1+str6+fieldName1+
				 * str8+"\n\t\t String text = "+fieldName1+text+"\n\t\t\t"+fieldName1+
				 * Beforestr9+"\nreturn text;"+str9; }else
				 */if (!fieldType.equals("InputText")) {
					funcName = fieldNamePrefix + fieldName1 + str6 + fieldName1 + str8 + "\n\t\t\t String text = "
							+ fieldName1 + text + "\n\t\t\t" + fieldName1 + Beforestr9 + "\n\t\t" + "return text;"
							+ str9;

				} else {
					funcName = fieldNamePrefix + fieldName1 + str6 + fieldName1 + str8 + fieldName1 + str9;
				}
				// funcName=fieldNamePrefix+fieldName+str6+fieldName+str7+fieldName+str8;

				// strTemp+=str4+xpath+str5+fieldName1+str7+funcName;
				strTemp += str4 + xpath + str5 + fieldName1 + returnString + funcName;

			}
		} else {
			System.out.println("Invalid input string!");
		}
		str += strTemp + str10;
		writeFile(str, toTitleCase(screenName), classPath, ".java");
	}

	public static String toTitleCase(String givenString) {
		String[] arr = givenString.split(" ");
		StringBuffer sb = new StringBuffer();

		for (int i = 0; i < arr.length; i++) {
			if (arr[i].trim().equals(""))
				continue;
			sb.append(Character.toUpperCase(arr[i].charAt(0))).append(arr[i].substring(1)).append("");
		}
		return sb.toString().trim();
	}

	public static void writeFile(String str, String className, String filePath, String fileExtension)
			throws IOException {
		Writer out = new BufferedWriter(
				new OutputStreamWriter(new FileOutputStream(filePath + "\\" + className + fileExtension), "UTF-8"));
		try {
			out.write(str);
		} finally {
			out.close();
		}
	}

	
	
	public static String writeXml(String tcName,String reportHeadLine,String reportName) {
		
		
		String str ="<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n" + 
				"<extentreports>\r\n" + 
				"  <configuration>\r\n" + 
				"    <!-- report theme -->\r\n" + 
				"    <!-- standard, dark -->\r\n" + 
				"    <theme>standard</theme>\r\n" + 
				"\r\n" + 
				"    <!-- document encoding -->\r\n" + 
				"    <!-- defaults to UTF-8 -->\r\n" + 
				"    <encoding>UTF-8</encoding>\r\n" + 
				"\r\n" + 
				"    <!-- protocol for script and stylesheets -->\r\n" + 
				"    <!-- defaults to https -->\r\n" + 
				"    <protocol>https</protocol>\r\n" + 
				"\r\n" + 
				"    <!-- title of the document -->\r\n" + 
				"    <documentTitle>"+tcName+"</documentTitle>\r\n" + 
				"\r\n" + 
				"    <!-- report name - displayed at top-nav -->\r\n" + 
				"    <reportName>DevRabbit - </reportName>\r\n" + 
				"\r\n" + 
				"    <!-- report headline - displayed at top-nav, after reportHeadline -->\r\n" + 
				"    <reportHeadline>TEST AUTOMATION FRAMEWORK</reportHeadline>\r\n" + 
				"\r\n" + 
				"    <!-- global date format override -->\r\n" + 
				"    <!-- defaults to yyyy-MM-dd -->\r\n" + 
				"    <dateFormat>yyyy-MM-dd</dateFormat>\r\n" + 
				"\r\n" + 
				"    <!-- global time format override -->\r\n" + 
				"    <!-- defaults to HH:mm:ss -->\r\n" + 
				"    <timeFormat>HH:mm:ss</timeFormat>\r\n" + 
				"\r\n" + 
				"    <!-- custom javascript -->\r\n" + 
				"    <scripts>\r\n" + 
				"      <![CDATA[\r\n" + 
				"                $(document).ready(function() {\r\n" + 
				"                    $(\".logo-content\").hide();\r\n" + 
				"                    $(\".logo-container\").html(\""+tcName+"\");\r\n" + 
				"                });\r\n" + 
				"            ]]>\r\n" + 
				"    </scripts>\r\n" + 
				"\r\n" + 
				"    <!-- custom styles -->\r\n" + 
				"    <styles>\r\n" + 
				"      <![CDATA[\r\n" + 
				"       .logo-content, nav{\r\n" + 
				"        background-color: #ff8f00;\r\n" + 
				"       }    \r\n" + 
				"                            \r\n" + 
				"      .nav-right li {\r\n" + 
				"        border-left:0px;\r\n" + 
				"        font-size: 18px;    \r\n" + 
				"      }\r\n" + 
				"      \r\n" + 
				"      .nav-right li:last-child {\r\n" + 
				"      Display:none;\r\n" + 
				"      }\r\n" + 
				"            ]]>\r\n" + 
				"    </styles>\r\n" + 
				"  </configuration>\r\n" + 
				"</extentreports>\r\n" + 
				"";
		
		
		
		return str;
		
		
		
	}
	
	
	public static void BacthFileExecution() {

		// try{
		// Process p = Runtime.getRuntime().exec("D:/Framework/Execute.bat");
		// p.waitFor();

		try {
			String[] command = { "cmd.exe", "/C", "Start", System.getenv("AUTOMATION_PATH")+"\\Suite_cmd.bat" };
			Runtime.getRuntime().exec(command);

		} catch (IOException ex) {
		}
	}

	//
	//
	// }catch( IOException ex ){
	// //Validate the case the file can't be accesed (not enought permissions)
	//
	// }catch( InterruptedException ex ){
	// //Validate the case the process is being stopped by some external situation
	//
	// }

	/*
	 * ProcessBuilder pb = new ProcessBuilder("cmd", "/c", "Suite_cmd.bat"); File
	 * dir = new File("C:/Users/Administrator/Desktop/Suite_cmd");
	 * pb.directory(dir); Process p = pb.start();
	 */
	// Process run = Runtime.getRuntime().exec("cmd.exe", "/c",
	// "C:/Users/Administrator/Desktop/Suite_cmd.bat");
	// }

	private DataSetValuesVo[] jsonParse(String output) {
		// [{"element_id":"gjfhj","value":"4500","screenId":"32"},{"element_id":"fdfd","value":"4499","screenId":"32"},{"element_id":"fdsd","value":"4498","screenId":"32"},{"element_id":"sdfd","value":"4497","screenId":"32"},{"element_id":"fdsfg","value":"4496","screenId":"32"},{"element_id":"fssdf","value":"4495","screenId":"32"},{"element_id":"fsss","value":"4494","screenId":"32"},{"element_id":"fsdfdff","value":"4493","screenId":"32"},{"element_id":"fsds","value":"4500","screenId":"33"},{"element_id":"sfs","value":"4499","screenId":"33"},{"element_id":"ssf","value":"4498","screenId":"33"},{"element_id":"ghjfjf","value":"4497","screenId":"33"},{"element_id":"","value":"4496","screenId":"33"},{"element_id":"gjg","value":"4569","screenId":"35"},{"element_id":"fdj","value":"4568","screenId":"35"}]

		Gson gson = new Gson();
		DataSetValuesVo[] datasetValues = gson.fromJson(output, DataSetValuesVo[].class);
		// ArrayList<DataSetValuesVo> lsit = (ArrayList<DataSetValuesVo>)
		// Arrays.asList(datasetValues);
		return datasetValues;

	}

}
