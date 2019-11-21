package com.dto;

import java.util.ArrayList;

public class TestCaseElementsVo {
        int testcaseId;
        String testcaseName;
        String url;
        ArrayList<ScreenDTO> screens = new ArrayList<>();
         
        public int getTestcaseId() {
                return testcaseId;
        }

        public void setTestcaseId(int testcaseId) {
                this.testcaseId = testcaseId;
        }

        public String getTestcaseName() {
                return testcaseName;
        }

        public void setTestcaseName(String testcaseName) {
                this.testcaseName = testcaseName;
        }

        public ArrayList<ScreenDTO> getScreens() {
                return screens;
        }

        public void setScreens(ArrayList<ScreenDTO> screens) {
                this.screens = screens;
        }

		public String getUrl() {
			return url;
		}

		public void setUrl(String url) {
			this.url = url;
		}
        
}
