package com.jfx;

import java.awt.Label;
import java.util.HashSet;
import java.util.Set;

import org.w3c.dom.events.MouseEvent;

import com.sun.webkit.dom.HTMLDocumentImpl;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;



public class WebViewMain extends Application {


	@Override
	public void start(Stage primaryStage) throws Exception {
		primaryStage.setTitle("WebView test");
		
		Platform.setImplicitExit(false);
		
		GridPane gridPane = new GridPane();
		GridPane gridPane1 = new GridPane();
		  Text idtitle = new Text("id: ");
		  Text nametitle = new Text("name: ");
		  Text typetitle = new Text("type: ");
		  
		  Text id = new Text("id");
		  Text name = new Text("name");
		  Text type = new Text("type");
		  
		gridPane1.setMinSize(200, 200);
		 gridPane1.add(idtitle, 0, 0); 
		 gridPane1.add(nametitle, 0, 1); 
		 gridPane1.add(typetitle, 0, 2); 
		 
		 gridPane1.add(id, 1, 0); 
		 gridPane1.add(name, 1, 1); 
		 gridPane1.add(type, 1, 2);
		
		Rectangle dragBox = new Rectangle(0, 0, 0, 0);
		dragBox.setVisible(false);
		WebView  browser = new WebView();
		WebEngine engine = browser.getEngine();
		String url = "http://www.devrabbit.com/contact/";
		engine.load(url);
		final Set<KeyCode> pressedKeys = new HashSet<>();
		StackPane sp = new StackPane();
		sp.getChildren().add(browser);
		 Label elementInfo = new Label();		
		 gridPane.add(sp, 0, 0);
		 gridPane.add(gridPane1, 1, 0); 
		Scene root = new Scene(gridPane);
		
		
		sp.setOnKeyPressed(e -> pressedKeys.add(e.getCode()));
		sp.setOnKeyReleased(e -> pressedKeys.remove(e.getCode()));

	      /*  
	        sp.setOnMouseClicked(e -> {
	            if (e.getButton() == MouseButton.SECONDARY  && e.isShortcutDown() && e.isShiftDown())
	                System.out.println("handled!");
	           
              String var =  engine.getDocument().getDocumentElement().getNodeName();
              System.out.println(var);
              System.out.println("X: " + e.getX() + " Y: " + e.getY());
            
              
	        });*/
		
		
	
		
	        browser.getEngine().documentProperty().addListener((observable, oldDoc, newDoc) -> {
	            HTMLDocumentImpl realMcCoy = (HTMLDocumentImpl) newDoc;
	            realMcCoy.setOnmousedown(event -> {
	                MouseEvent expertMouser = (MouseEvent) event;
	                if(expertMouser.getCtrlKey()) {
	                	System.out.println("ctrl key");
	                }
	             org.w3c.dom.Element ele=   realMcCoy.elementFromPoint(
	                         expertMouser.getClientX(),
	                         expertMouser.getClientY()
	                 );
	             if(ele != null) {
	            	  id.setText(ele.getAttribute("id").toString());
	 	             name.setText(ele.getAttribute("name").toString());
	 	             type.setText(ele.getAttribute("type").toString()+">>>"+ele.getTagName().toString()); 
	             }
	           
	             
	             
         System.out.println(
        		 ele.toString()
                 
         );
         System.out.println(">>>>name"+
                 realMcCoy.elementFromPoint(
                         expertMouser.getClientX(),
                         expertMouser.getClientY()
                 ).getAttribute("name").toString()         
         );
         System.out.println(">>>>id"+
                 realMcCoy.elementFromPoint(
                         expertMouser.getClientX(),
                         expertMouser.getClientY()
                 ).getAttribute("id").toString()         
         );
         
         System.out.println(">>>"+
                 realMcCoy.elementFromPoint(
                         expertMouser.getClientX(),
                         expertMouser.getClientY()
                 ).getTagName().toString()         
         );
         
	              /*  elementInfo.setText(
	                        realMcCoy.elementFromPoint(
	                                expertMouser.getClientX(),
	                                expertMouser.getClientY()
	                        ).toString()
	                );*/
	            });
	        });
		
		primaryStage.setScene(root);
		primaryStage.show();
	}
	 public class JavaApplication {
	        public void callFromJavascript(String msg) {	           
	            System.out.println("Click from Javascript: " + msg);
	        }
	        }
}