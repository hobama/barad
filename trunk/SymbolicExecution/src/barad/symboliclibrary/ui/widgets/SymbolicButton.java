package barad.symboliclibrary.ui.widgets;

import org.apache.log4j.Logger;

import barad.symboliclibrary.integers.IVAR;
import barad.symboliclibrary.integers.IntegerInterface;
import barad.symboliclibrary.path.Path;
import barad.symboliclibrary.strings.StringInterface;
import barad.symboliclibrary.test.Descriptor;
import barad.symboliclibrary.ui.events.SymbolicSelectionEvent;
import barad.symboliclibrary.ui.events.listeners.SymbolicSelectionListener;

@SuppressWarnings("unused")
public class SymbolicButton extends SymbolicWidget {
	public static final long serialVersionUID = 1L;
	private static final String SWT_CLASS_EQUIVALENT = "org.eclipse.swt.widgets.Button";
	private Logger log;
	private Object layoutData;
	private StringInterface text;
	private SymbolicSelectionListener selectionListener;
	private IntegerInterface selection;

	public SymbolicButton(SymbolicComposite parent, IntegerInterface style) {
		super(parent, style, "SymbolicButton");
		this.log = Logger.getLogger(getClass());
		IVAR selection = new IVAR();
		selection.setMin(0);
		selection.setMax(1);
		selection.setDescriptor(getDescriptor());
		this.selection = selection;
	}
	
	@Override
	public String getSWTClassEquivalent() {
		return SWT_CLASS_EQUIVALENT;
	}
	
	/**
	 * Executes all methods of all event handlers registered 
	 * for events in this widget. The executed method is
	 * identified by the concatenation of the widget class
	 * name, 
	 */
	@Override
	public void executeEventHandlerMethods() {
		//selection event listener
		if  (selectionListener != null) {
			//set up
			SymbolicSelectionEvent event = new SymbolicSelectionEvent(this);
			Path.addInputVariable(event);
			//set a descriptor for which widget and event execute the handler
			addEventDescriptor("SelectionEvent.widgetSelected");
			Path.setExecutedMethod("widgetSelected");
			//invoke the method
			selectionListener.widgetSelected(event);
			//generate test cases
			Path.generateTestSuite();
			//set up
			event = new SymbolicSelectionEvent(this);
			Path.addInputVariable(event);
			//set a descriptor for which widget and event execute the handler
			addEventDescriptor("SelectionEvent.widgetDefaultSelected");
			Path.setExecutedMethod("widgetDefaultSelected");
			//invoke the method
			selectionListener.widgetDefaultSelected(event);
			//generate test cases
			Path.generateTestSuite();
		} else {
			log.warn("There is no selection listener added to SymbolicCombo " + getId());
		}
	}

	public void addSelectionListener(SymbolicSelectionListener selectionListener) {
		this.selectionListener = selectionListener;
	}
	
	public void removeSelectionListener() {
		this.selectionListener = null;
	}
	
	public Object getLayoutData() {
		return layoutData;
	}

	public void setLayoutData(Object layoutData) {
		this.layoutData = layoutData;
	}
	
	public StringInterface getText() {
		return text;
	}

	public void setText(StringInterface text) {
		this.text = text;
	}
	
	public IntegerInterface getSelection() {
		Path.addInputVariable(selection);
		return selection;
	}
	
	public void setSelection(IntegerInterface selection) {
		this.selection = selection;
	}
	
	private void addEventDescriptor(String eventDescription) {
		Descriptor descriptor = getDescriptor();
		descriptor.setWidgetProperty(eventDescription);
		Path.setTriggeringEventDescriptor(descriptor);
	}
	
	private Descriptor getDescriptor () {
		Descriptor descriptor = new Descriptor();
		descriptor.setParentId(getParent().getName());
		descriptor.setParentClass(getParent().getSWTClassEquivalent());
		descriptor.setParentIndex(String.valueOf(getParent().getIndex()));
		descriptor.setWidgetId(getName());
		descriptor.setWidgetClass(SWT_CLASS_EQUIVALENT);
		descriptor.setWidgetProperty("text");
		descriptor.setWidgetIndex(String.valueOf(getIndex()));
		return descriptor;
	}
}
