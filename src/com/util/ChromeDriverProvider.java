package com.util;

import java.util.ArrayList;

import org.openqa.selenium.chrome.ChromeDriver;

import com.dto.PageDetailsVo;

public class ChromeDriverProvider {
   
	private static ChromeDriver driver;
	public static ArrayList<PageDetailsVo> pagedetailsList;
	
	public static void doNull() {
		driver  = null;
	}
	
	
	public static void doSaveArrayLst(ArrayList<PageDetailsVo> data) {
		pagedetailsList = data;
	}
	
	public static ArrayList<PageDetailsVo>  getPageList() {
		return pagedetailsList;
	}
	
    public static ChromeDriver getInstance(String url) {
        if (driver == null && url!= null) {
        	System.setProperty("webdriver.chrome.driver", "C:\\chromedriver\\chromedriver.exe");
			driver = new ChromeDriver();
			driver.get(url);
        }
        return driver;
    }
}