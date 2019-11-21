package com.ui;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jdom2.Attribute;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.openqa.selenium.WebDriver;

import com.DemoLocalTest.CheckInsert;
import com.dto.PageDetailsVo;
import com.util.ChromeDriverProvider;

public class AttributeValues {
	WebDriver driver = null;
	String pagesource = "", previuosTagName = "", previousText = "", valueText = "", childRelativeXpath = "",
			childRelativeXpathByName = "";
	int flag = 0;
	int pageid = 0;
	int urlId = 0;
	ArrayList<PageDetailsVo> pagedetailsList = new ArrayList<PageDetailsVo>();
	private String placeHolder;
	public int inIt(String url, boolean isCreatePage) {
		try {
			if(!isCreatePage) {
				ChromeDriverProvider.doNull();
			}
			driver = ChromeDriverProvider.getInstance(url);
			urlId = CheckInsert.CreateProject(url, "");
		} catch (Exception e) {
			// TODO: handle exception
		}

		return urlId;

	}

	public int openDriver(String url, boolean isCreatePage) {
		try {
			if(!isCreatePage) {
				ChromeDriverProvider.doNull();
			}
			driver = ChromeDriverProvider.getInstance(url);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return urlId;
	}
	public ArrayList<PageDetailsVo> getElements() {
		try {
		readinput(ChromeDriverProvider.getInstance(null), pagesource);
		}catch (Exception e) {
			// TODO: handle exception
		}
		Collections.sort(pagedetailsList, new Comparator<PageDetailsVo>(){
		    public int compare(PageDetailsVo page1, PageDetailsVo page2) {
		        return page1.getPropertyName().compareToIgnoreCase(page2.getPropertyName());
		    }
		});
		return pagedetailsList;
	}

	public void readinput(WebDriver driver, String Pagesource) throws InterruptedException, IOException {
		BufferedReader br = null;

		try {
			String input = "";

			System.out.println("**************************Input value please**************************");
			
			
			System.out.println("**************************Please wait*********************************");
			writeFile(driver.getPageSource());
			
			System.out.println("**************************Please wait*********************************");
			// writeToUTF8();
			getattributes();

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

	public void writeToUTF8() throws IOException {

		BufferedReader br = new BufferedReader(
				new InputStreamReader(new FileInputStream("C:\\chromedriver\\inputfile.txt")));
		String line;

		Writer out = new BufferedWriter(
				new OutputStreamWriter(new FileOutputStream("C:\\chromedriver\\outputfile.txt"), "UTF-8"));

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

	public static void writeFile(String str) throws IOException {
		String pattern = "", str1 = "";
		pattern = "<!--[\\s\\S]*?-->";
		// pattern = "<!--[^-]*(?:-[^-]+)*-->";
		// "<!--.*?-->"
		// Create a Pattern object
		Pattern r = Pattern.compile(pattern);

		// Now create matcher object.
		Matcher m = r.matcher(str);
		str = m.replaceAll("");

		pattern = "<br[\\s]/>";

		// Create a Pattern object
		r = Pattern.compile(pattern);

		// Now create matcher object.
		m = r.matcher(str);
		str = m.replaceAll(" ");

		Writer out = new BufferedWriter(
				new OutputStreamWriter(new FileOutputStream("C:\\chromedriver\\outputfile.txt"), "UTF-8"));
		try {
			out.write(str);
		} finally {
			out.close();
		}

	}

	public void getattributes() {
		try {
			File inputFile = new File("C:\\chromedriver\\outputfile.txt");
			SAXBuilder saxBuilder = new SAXBuilder();
			Document document = saxBuilder.build(inputFile);
			System.out.println("Root element :" + document.getRootElement().getName());
			Element classElement = document.getRootElement();
			String xpath = "//" + classElement.getName(), parentRelativeXpath = "", parentRelativeXpathByName = "", parentTagName="",  parentText="";
			System.out.println("//" + classElement.getName());
			parentRelativeXpath = xpath;
			parentRelativeXpathByName = xpath;
			parser(classElement, xpath, parentRelativeXpath, parentRelativeXpathByName, parentTagName,  parentText );

		} catch (JDOMException e) {
			e.printStackTrace();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}

    public  void parser(Element classElement,String xpath,String parentRelativeXpath,String parentRelativeXpathByName,String parentTagName, String parentText ) {
    	
    	List<Element> children = classElement.getChildren();
    	
    	int position=0,i,childPosition=0;
    	String parentXpath,childXpath="",idValue="",nameValue="",textValue="",childParenXpath="",childParentRelativeXpath="",childParentRelativeXpathByName="",previousParentRelativeXpath="//",previousParentRelativeXpathByName="//",PreviousParentTagName="",PreviousParentText="";	    	  
		ArrayList<String> ab = new ArrayList<String>();
		ArrayList<String> bc = new ArrayList<String>();
		parentXpath = xpath;
		childParentRelativeXpath = parentRelativeXpath;
		childParentRelativeXpathByName = parentRelativeXpathByName;
		bc.clear();
		childParenXpath = "";
		previousParentRelativeXpath = parentRelativeXpath ;
		previousParentRelativeXpathByName = parentRelativeXpathByName;
		PreviousParentTagName = parentTagName;
		PreviousParentText=parentText;
		for( i=0;i<children.size();i++) {
			Element child = children.get(i);
			if(child.getName()=="script"||child.getName()=="polygon"||child.getName()=="rect"||child.getName()=="source"||child.getName()=="style"||child.getName()=="noscript"||child.getName()=="meta"||child.getName()=="link")
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
					parentRelativeXpathByName = previousParentRelativeXpathByName + "/"+child.getName()+"["+position+"]";
				}
				
				//if(child.getName().equals("button"))
					parentTagName=child.getName();
				if(child.getName().equals("label"))
					parentText=child.getText();
				if(PreviousParentTagName.equals("label")) {
					parentTagName = PreviousParentTagName;
					parentText=PreviousParentText;
				}
				parser(child,xpath,parentRelativeXpath,parentRelativeXpathByName,parentTagName,parentText);
				xpath = parentXpath;
				parentRelativeXpath  = previousParentRelativeXpath ;
				parentRelativeXpathByName  = previousParentRelativeXpathByName ;
				//parentTagName = PreviousParentTagName;
				//parentText=PreviousParentText;
				//System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
			//	System.out.println("Parent TagName: "+parentTagName);
			//	System.out.println("Parent Text: "+parentText);
			//	System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
			} 
			else {	
			//	System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");
			//	System.out.println("Parent TagName: "+parentTagName);
			//	System.out.println("Parent Text: "+parentText);
			//	System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");

				childPosition=tagsinfo(child.getName(),bc);
				childParenXpath=parentXpath;
				childXpath=xpath+"/"+child.getName()+"["+childPosition+"]";        	  			            	  			            	        	 			            	 
				Attribute id =  child.getAttribute("id");
				Attribute Name =  child.getAttribute("name");
				Attribute Type =  child.getAttribute("type");
				String 	typeValue="",Title="";
		
				if(Type!=null) {
					typeValue = Type.toString().split("\"")[1];
				
				}
				if(typeValue.equals("hidden")) {
					continue;
				}
				
				if(child.getName().equals("input")) {
					Attribute Value =  child.getAttribute("value");
					Attribute Placeholder =  child.getAttribute("placeholder");
					Attribute TitleValue =  child.getAttribute("title");
					 if(parentTagName.equals("label")) {
						textValue = parentText;
					} else if(TitleValue!=null) {
						
						Title = TitleValue.toString().split("\"")[1];
						if(Title.length()!=0)
							textValue = Title;
					} else if(Value!=null) {							
						valueText = Value.toString().split("\"")[1];
						if(valueText.length()!=0)
						textValue = valueText;
					} else if(Placeholder!=null) {
						placeHolder = Placeholder.toString().split("\"")[1];
						if(placeHolder.length()!=0)
							textValue = placeHolder;
					} else {
						textValue = previousText;
					}


					if(typeValue.equals("submit")||typeValue.equals("button")) {
						typeValue="Button";
					}
					else{
						typeValue="InputText";
					}
				}
				else if(child.getText().toString().length()!=0) {
						textValue = child.getText();
					if(child.getName().equals("a")) {
						typeValue="Link";
					}
					else if(child.getName().equals("img")) {
						
						typeValue="Image";
					}
					else if(parentTagName.equals("Button")) {
						typeValue="Button";
					}
					else {
						typeValue="Label";
					}

				}else {
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
			
				
			//	System.out.println("textValue.toString().length() : "+textValue.toString().length());

				
				if((textValue.toString().length()!=0&&(!textValue.isEmpty())&&textValue!="\n"&&textValue!="")||child.getName().equals("input")) {
					if(childXpath.equals(childRelativeXpath))
						childRelativeXpath="";
					if(childXpath.equals(childRelativeXpathByName))
						childRelativeXpathByName="";					
					System.out.println("\nAbsolutePath: " +childXpath);
					System.out.println("\nRelativePathById: " +childRelativeXpath);
					System.out.println("\nRelativePathByName: " +childRelativeXpathByName);
					//System.out.println("\nTagName: "+child.getName() + ' '+ "name: " +  nameValue + ' '+ "id: " +  idValue + ' '+ "Text:   " +  textValue+"\n");
					System.out.println("\nTagName: "+child.getName()  + "  Displayed Text: "+ textValue + "        Type Value:    "+typeValue+"\n");
					
					PageDetailsVo vo = new PageDetailsVo();
					vo.setxPath(childXpath);
					vo.setRelativeXpathbyName(childRelativeXpathByName);
					vo.setRelativeXpathbyId(childRelativeXpath);
					vo.setPropertyName(textValue);
					vo.setInputValue(typeValue);
					vo.setTagName(child.getName());
					vo.setId(idValue);
					vo.setText_for_idtext(nameValue);
					vo.setCreatedBy("user");
					vo.setPageid(pageid);
					pagedetailsList.add(vo);

				}
				else {
					textValue = "";
				}
				if(child.getName().equals("label"))
					previousText = child.getText();
				else if(parentTagName.equals("label")) {
					previousText = parentText;
				}
				else {
					previousText = "";
				}
					

             }		  
		}

    }
	public static int tagsinfo(String tagName, ArrayList<String> pq) {
		int k = 0;
		if (!pq.contains(tagName)) {
			pq.add(tagName);
			return 1;
		} else {
			pq.add(tagName);
			Iterator<String> itr = pq.iterator();
			while (itr.hasNext()) {
				if (itr.next() == tagName)
					k++;
			}
			return k;
		}
	}

	public String readFile(String file) throws IOException {

		BufferedReader reader = new BufferedReader(new FileReader(file));
		String line = null;
		StringBuilder stringBuilder = new StringBuilder();
		String ls = System.getProperty("line.separator");
		int flag = 0;

		try {
			while ((line = reader.readLine()) != null) {

				// if(line.contains("<body"))
				// flag=1;

				// if(flag==1) {
				stringBuilder.append(line);
				stringBuilder.append(ls);
				// }
				// if(line.contains("/body>"))
				// flag=0;
			}
			writeFile(stringBuilder.toString(), "C:\\chromedriver\\outputfile.txt");
			return stringBuilder.toString();
		} finally {
			reader.close();
		}

	}

	public static void writeFile(String str, String file1) {
		BufferedWriter bufferedWriter = null;
		try {
			File file = new File(file1);
			if (!file.exists()) {
				file.createNewFile();
			}
			// FileWriter fileWriter = new FileWriter(file);
			Writer writer = new FileWriter(file);
			bufferedWriter = new BufferedWriter(writer);
			bufferedWriter.write(str);

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (bufferedWriter != null)
					bufferedWriter.close();
			} catch (Exception ex) {

			}
		}
	}

}
