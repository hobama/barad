package com.webcodefocus.blogcaster.view;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class TestLauncher {
	/**
	 * @param args
	 */
	 public static void main(String[] args) {
		MainWindow mainWindow = new MainWindow();
		 	
		// if (args.length > 0) {
			//try{
			    //print fields
		 
				System.out.println("[CLASS FIELDS]");
				Class clazz = mainWindow.getClass();			//Class.forName(args[0]);
				Field[] fileds = clazz.getFields();
				for (int i = 0; i < fileds.length; i++) {
					String fieldName = "[FIELD NAME] " + fileds[i].getName() + '\t';
					Object object = null;
					try{
						object = fileds[i].get(mainWindow);
					}catch(Exception e){}
					if (object != null) {
					    System.out.println(fieldName + "[FIELD VALUE] " + object.toString());
					}
				}
				System.out.println();
				//print methods
				System.out.println("[CLASS METHODS]");
				Method[] methods = clazz.getMethods();
				for (int i = 0; i < methods.length; i++) {
					String methodName = "[METHOD NAME] " + methods[i].getName() + '\t';
					Object object = null;
					try{
						object = methods[i].getReturnType();
					}catch(Exception e){}
					if (object != null) {
					    System.out.println(methodName + "[RETURN VALUE] " + object.toString());
					}
					
				}
				System.out.println();
				//print widget/listener mappings
				System.out.println("[WIDGET/LISTENER MAPPINGS]");
			    WidgetRegistry.printMappings();
 
				
			//}catch(ClassNotFoundException cnfe){}
			//catch(IllegalAccessException iae){}
			//(InstantiationException ie){}
		//}
	 }
}
