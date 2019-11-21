package com.dto;

import java.util.ArrayList;

import com.db.PostgresDBHelper;

public class ScreenDTO {
        int screenId;
        String screenName;
        String screenDescription;
        int pageId;
        ArrayList<ScreenElementDTO> elements = new ArrayList<>();
        
        public ArrayList<ScreenElementDTO> getScreenElements() {
                return elements;
        }

        public void setScreenElements(ArrayList<ScreenElementDTO> screenElements) {
                this.elements = screenElements;
        }
 
        public int getScreenId() {
                return screenId;
        }

        public void setScreenId(int screenId) {
                this.screenId = screenId;
        }

        public String getScreenName() {
                return screenName;
        }

        public void setScreenName(String screenName) {
                this.screenName = screenName;
        }

		public String getScreenDescription() {
			return screenDescription;
		}

		public void setScreenDescription(String screenDescription) {
			this.screenDescription = screenDescription;
		}

		public int getPageId() {
			return pageId;
		}

		public void setPageId(int pageId) {
			this.pageId = pageId;
		}

		public boolean isDuplicateScreen(ScreenDTO screenvo) {
			PostgresDBHelper client = new PostgresDBHelper();
			try {
				if (client.connect()) {
					return client.checkForDuplicateScreen(screenvo);
				}
			} catch (Exception e) {
				e.printStackTrace();
			} 
			return false;
		}
        
        
}
