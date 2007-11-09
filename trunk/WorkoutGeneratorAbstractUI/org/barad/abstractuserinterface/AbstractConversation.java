package org.barad.abstractuserinterface;

import java.util.LinkedList;

public class AbstractConversation extends AbstractWidget {
	private LinkedList<AbstractWidget> widgets;

	public LinkedList<AbstractWidget> getWidgets() {
		return widgets;
	}

	public void setWidgets(LinkedList<AbstractWidget> widgets) {
		this.widgets = widgets;
	}
	
	public void begin() {
		
	}
	
	public boolean isActive() {
		return true;
	}
	
	//Hack I do not know if this should be here
	public boolean readAndDispatch() {
		return true;
	}
}
