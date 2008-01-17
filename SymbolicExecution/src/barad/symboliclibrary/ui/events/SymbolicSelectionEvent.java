package barad.symboliclibrary.ui.events;

import java.io.Serializable;

import barad.symboliclibrary.integers.ICONST;
import barad.symboliclibrary.ui.widgets.SymbolicWidget;

@SuppressWarnings("unused")
public class SymbolicSelectionEvent extends SymbolicEvent implements Serializable {
	public static final long serialVersionUID = 1L;
	private SymbolicWidget eventSourceWidget;
	
	public SymbolicSelectionEvent() {
		/*leave empty*/
	} 
	
	public SymbolicSelectionEvent(SymbolicWidget eventSourceWidget) {
		this.eventSourceWidget = eventSourceWidget;
	} 
	
    //extra detail information about the selection, depending on the widget  
	public ICONST 	detail;
	
	//a flag indicating whether the operation should be allowed
	public ICONST doit;
	 
	//the height of selected area
	public ICONST height;
	
	//the item that was selected
	public SymbolicWidget item;
	
	//the state of the keyboard modifier keys at the time the event was generated
	public ICONST stateMask;
	
	//the width of selected area
	public ICONST width;
	
	//the x location of the selected area
	public ICONST x;
	
	//the y location of selected area
	public ICONST y;
}
