package com.ui;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jdom2.*;
import org.jdom2.input.SAXBuilder;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;




 class GetAttributeValues{
	 static WebDriver driver;
	static String pagesource="",previuosTagName="",previousText="",valueText="",childRelativeXpath="",childRelativeXpathByName="";
	static int flag = 0;
	    public static void main(String[] args) throws InterruptedException, IOException {

	    	//String url =  "https://www.nio.io/";
		   // String URL =	"http://www.devrabbit.com/";
	    	String url = "https://www.harborfreight.com/";
	    	//String url = "https://s3-us-west-1.amazonaws.com/scheduling-dublin-infiniti/index.html";

			System.setProperty("webdriver.chrome.driver", "E:\\java_workspace\\TO\\chromedriver_win32\\chromedriver.exe");
			 driver = new ChromeDriver();
			driver.get(url);
			
				do {
					readinput(driver,pagesource);
				}while(flag==1);
				System.out.println("Completed the process");
	     }

	public static void readinput(WebDriver driver, String Pagesource) throws InterruptedException, IOException {
        BufferedReader br = null;

        try {
            String input = "";
            while (true) {
            	System.out.println("**************************Input value please**************************");
                br = new BufferedReader(new InputStreamReader(System.in));
                input = br.readLine();

                if(input.equals("aaa")) {
                	System.out.println("**************************Please wait*********************************");
    				writeFile(driver.getPageSource());
    				writeToUTF8();
    				getattributes();
    				continue;
                }
                else {
	                if (input.equals("qqq")) {
	                    flag=0;
	                    break;
	                }
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

	
	
	
	public static  void writeToUTF8() throws IOException {
		
		
	    BufferedReader br = new BufferedReader(new InputStreamReader( new FileInputStream("E:\\java_workspace\\pageSource_Automation\\inputfile.txt")));
	    String line;

	    Writer out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("E:\\java_workspace\\pageSource_Automation\\outputfile.txt"), "UTF-8"));

	    try {

	        while ((line = br.readLine()) != null) {
	            out.write(line);
	            out.write("\n");

	        }

	    } finally {

	        br.close();
	        out.close();

	    }
	}
	
	
		public static  void writeFile(String str) {	
			
		      String pattern = "<!--[\\s\\S]*?-->";

		      // Create a Pattern object
		      Pattern r = Pattern.compile(pattern);

		      // Now create matcher object.
		      Matcher m = r.matcher(str);
		      str= m.replaceAll("");
			
			 BufferedWriter bufferedWriter = null;
			try {
				File file = new File("E:\\java_workspace\\pageSource_Automation\\inputfile.txt");
	            if (!file.exists()) {
	            	file.createNewFile();
	            }
			     Writer writer = new FileWriter(file);
	            bufferedWriter = new BufferedWriter(writer);
	            bufferedWriter.write(str);

	        } catch (IOException e) {
	            e.printStackTrace();
	        } finally{
	            try{
	                if(bufferedWriter != null) bufferedWriter.close();
	            } catch(Exception ex){
	                 
	            }
	        }
	    }

	  public static void  getattributes(){
	        try {
		           File inputFile = new File("E:\\java_workspace\\pageSource_Automation\\outputfile.txt");
		           SAXBuilder saxBuilder = new SAXBuilder();
		           Document document = saxBuilder.build(inputFile);
		           System.out.println("Root element :" + document.getRootElement().getName());
		           Element classElement = document.getRootElement();
		           String xpath = "//"+classElement.getName(),parentRelativeXpath="",parentRelativeXpathByName="";
		           System.out.println("//"+classElement.getName());
		           parentRelativeXpath = xpath;
		           parentRelativeXpathByName = xpath;
		           parser(classElement,xpath,parentRelativeXpath,parentRelativeXpathByName);        
		        } catch(JDOMException e) {
		           e.printStackTrace();
		        } catch(IOException ioe) {
		           ioe.printStackTrace();
		        }
	    }
 
	    public static void parser(Element classElement,String xpath,String parentRelativeXpath,String parentRelativeXpathByName ) {
	    	
	    	List<Element> children = classElement.getChildren();
	    	int temp=0,flag=1;
	    	int position=0,i,j,childPosition=0;
	    	String parentXpath,childXpath="",idValue="",nameValue="",textValue="",childParenXpath="",childParentRelativeXpath="",childParentRelativeXpathByName="",previousParentRelativeXpath="//",previousParentRelativeXpathByName="//";	    	  
			ArrayList<String> ab = new ArrayList<String>();
			ArrayList<String> bc = new ArrayList<String>();
			parentXpath = xpath;
			childParentRelativeXpath = parentRelativeXpath;
			childParentRelativeXpathByName = parentRelativeXpathByName;
			bc.clear();
			childParenXpath = "";
			previousParentRelativeXpath = parentRelativeXpath ;
			previousParentRelativeXpathByName = parentRelativeXpathByName;
			
			for( i=0;i<children.size();i++) {
				Element child = children.get(i);
				if(child.getName()=="script"||child.getName()=="style"||child.getName()=="noscript"||child.getName()=="meta"||child.getName()=="link")
				{
					continue;
				}		
				if(child.getChildren().size()>0) {
					position = tagsinfo(child.getName(),ab);
					if(parentXpath==childParenXpath) {
						position=tagsinfo(child.getName(),bc);	
					}
					else {
						childPosition=tagsinfo(child.getName(),bc);
					}
					xpath=parentXpath+"/"+child.getName()+"["+position+"]";
					Attribute id =  child.getAttribute("id");
					Attribute Name =  child.getAttribute("name");
					if(id!=null) {
						idValue = id.toString().split("\"")[1];
					}
					else
						idValue = null;
					
					if(idValue!=null) {
						parentRelativeXpath = "//"+child.getName()+"[@id='"+idValue+"']";
					}
					else {
						//parentRelativeXpath = xpath;
						parentRelativeXpath = previousParentRelativeXpath + "/"+ child.getName()+"["+position+"]";
					}
					
					if(Name!=null) {
						nameValue = Name.toString().split("\"")[1];
					}
					else
						nameValue = null;
					
					if(nameValue!=null) {
						parentRelativeXpathByName = "//"+child.getName()+"[@name='"+nameValue+"']";
					}
					else {
					//	parentRelativeXpathByName = xpath;
						
						parentRelativeXpathByName = previousParentRelativeXpathByName + "/"+child.getName()+"["+position+"]";
					}
					parser(child,xpath,parentRelativeXpath,parentRelativeXpathByName);
					xpath = parentXpath;
					parentRelativeXpath  = previousParentRelativeXpath ;
					parentRelativeXpathByName  = previousParentRelativeXpathByName ;
				//	childPosition=tagsinfo(child.getName(),bc);
				}
				else {	
	
					childPosition=tagsinfo(child.getName(),bc);
					//previousTagName = child.getName();
					childParenXpath=parentXpath;
					childXpath=xpath+"/"+child.getName()+"["+childPosition+"]";        	  			            	  			            	        	 			            	 
					Attribute id =  child.getAttribute("id");
				//	Attribute Text =  child.getAttribute("type");
				//	Attribute Value =  child.getAttribute("value");
					Attribute Name =  child.getAttribute("name");
				//	Attribute AlternateText =  child.getAttribute("alt");
					

										
					if(child.getName().equals("input")) {
						Attribute Value =  child.getAttribute("value");
						if(Value!=null) {
								valueText = Value.toString().split("\"")[1];
								if(valueText.length()!=0)
									textValue = valueText;
								else
									textValue = previousText;	
						}
						else {
							textValue = previousText;
						//	previousText = "";
						}
					}
					else {
						if(child.getText().toString().length()!=0)
							textValue = child.getText();
						else
							textValue = "";
						if(textValue=="0")
							textValue = "";
					}

					if(child.getName().equals("img")) {
						Attribute AlternateText =  child.getAttribute("alt");
						if(AlternateText!=null) {
							valueText = AlternateText.toString().split("\"")[1];
							if(valueText.length()!=0) {
								textValue = "image-"+valueText;
							}
							else {
								textValue = "image";
							}
						}
						else {
							textValue = "image";
						}
						
					}
					
					if(id!=null) {
						idValue = id.toString().split("\"")[1];
					}
					else
						idValue = null;
					
					if(idValue!=null) {
						childRelativeXpath = "//"+child.getName()+"[@id='"+idValue+"']";
					}
					else {
						childRelativeXpath=childParentRelativeXpath + "/" +child.getName()+"["+childPosition+"]";
					}


					if(Name!=null) {
						nameValue = Name.toString().split("\"")[1];
					}
					else
						nameValue = null;
					if(nameValue!=null) {
						childRelativeXpathByName = "//"+child.getName()+"[@name='"+nameValue+"']";
					}
					else {
						childRelativeXpathByName = childParentRelativeXpathByName +"/" +child.getName()+"["+childPosition+"]";
					}
					
					if(nameValue==null)
						nameValue="";

					if(idValue==null)
						idValue="";
					if(textValue.toString()!=""||textValue.toString()!=" "||textValue.toString()!="0"||(child.getName().equals("input"))) {
				
						if(childXpath.equals(childRelativeXpath))
							childRelativeXpath="";
						if(childXpath.equals(childRelativeXpathByName))
							childRelativeXpathByName="";					
						System.out.println("\nAbsolutePath: " +childXpath);
						System.out.println("\nRelativePathById: " +childRelativeXpath);
						System.out.println("\nRelativePathByName: " +childRelativeXpathByName);
						System.out.println("\nTagName: "+child.getName() + ' '+ "name: " +  nameValue + ' '+ "id: " +  idValue + ' '+ "Text:   " +  textValue+"\n");
				
					}
					else {
						textValue = null;
					}
					previousText = child.getText();
	             }		  
			}
   
	    }
	    public static int tagsinfo(String tagName,ArrayList<String> pq) {
	    int k=0;
	    if(!pq.contains(tagName)) {
	    	pq.add(tagName);
	    	return 1;
	    }
	    else {
	    		pq.add(tagName);
	    		Iterator<String> itr = pq.iterator();
	    		while(itr.hasNext()){
	    			if(itr.next()==tagName)
	    				k++;
			   		}
		   	   		return k;
				}    	
	    	}
	    
		public static  String readFile(String file) throws IOException {
			
		    BufferedReader reader = new BufferedReader(new FileReader (file));
		    String         line = null;
		    StringBuilder  stringBuilder = new StringBuilder();
		    String         ls = System.getProperty("line.separator");
		    int flag =0;

		    try {
		        while((line = reader.readLine()) != null) {
		        	
		       // 	if(line.contains("<body"))
		        //		flag=1;
		        	
		      //  	if(flag==1) {
			            stringBuilder.append(line);
			            stringBuilder.append(ls);
		      //  	}
		       // 	if(line.contains("/body>"))
		        //		flag=0;
		        }
		        writeFile(stringBuilder.toString(),"E:\\java_workspace\\pageSource_Automation\\outputfile.txt");
		        return stringBuilder.toString();
		    } finally {
		        reader.close();
		    }
		    
		}
		public static  void writeFile(String str,String file1) {	
			 BufferedWriter bufferedWriter = null;
			try {
				File file = new File(file1);
	            if (!file.exists()) {
	            	file.createNewFile();
	            }
			//	FileWriter fileWriter = new FileWriter(file);
			     Writer writer = new FileWriter(file);
	            bufferedWriter = new BufferedWriter(writer);
	            bufferedWriter.write(str);

	        } catch (IOException e) {
	            e.printStackTrace();
	        } finally{
	            try{
	                if(bufferedWriter != null) bufferedWriter.close();
	            } catch(Exception ex){
	                 
	            }
	        }
	    }
		


 }
