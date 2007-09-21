package barad.examples;

import static barad.util.Properties.LOG4J_PROPERTIES_FILE_NAME;

import org.apache.log4j.xml.DOMConfigurator;

import barad.symboliclibrary.integers.ICONST;
import barad.util.Util;

public class Examples {
	public static void main(String[] args) {
		DOMConfigurator.configure(LOG4J_PROPERTIES_FILE_NAME); 
		Util.loadFromSystemPropetiesXML();
		
		/*
		Path.createNewState();
		ICONST iconst = new ICONST(10);
		Path.addLocalVariable(iconst, "X");
		Path.addLocalVariable(new ICONST(100), "X");
		iconst.setValue(12345);
		Path.addLocalVariable(new ICONST(103), "Y");
		Path.getLastState().printValueToClonePairs();	
		*/
		
		//addLocalVariable(new ICONST(10), "X");
		//ICONST result = (ICONST)deepCloneBySerialization(iconst);
		//LinkedList<Integer> list = new LinkedList<Integer>();
		//LinkedList<Integer> result = (LinkedList<Integer>)deepCloneBySerialization(list);
		//System.out.println(result);
			
		IfExample example = new IfExample(); 
	}
}
