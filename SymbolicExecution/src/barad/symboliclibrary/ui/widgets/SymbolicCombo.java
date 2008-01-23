package barad.symboliclibrary.ui.widgets;

import org.apache.log4j.Logger;

import barad.symboliclibrary.arrays.SymbolicArray;
import barad.symboliclibrary.integers.ICONST;
import barad.symboliclibrary.integers.IntegerInterface;
import barad.symboliclibrary.path.Path;
import barad.symboliclibrary.strings.StringInterface;
import barad.symboliclibrary.strings.SymbolicString;
import barad.symboliclibrary.test.Descriptor;
import barad.symboliclibrary.ui.events.SymbolicSelectionEvent;
import barad.symboliclibrary.ui.events.listeners.SymbolicSelectionListener;

/**
 * Symbolic widget used to replace the Combo instances
 * from the SWT library. All private fields accessed
 * during the opration of the widget from other classes
 * are replaced with symbolic equivalents.
 * @author svetoslavganov
 *
 */
public class SymbolicCombo extends SymbolicWidget {
	public static final long serialVersionUID = 1L;
	private static final String SWT_CLASS_EQUIVALENT = "org.eclipse.swt.widgets.Combo";
	private Logger log;
	private  StringInterface text;
	private IntegerInterface length;
	private Object layoutData;
	private SymbolicArray<StringInterface> items;
	private SymbolicSelectionListener selectionListener;
	
	public SymbolicCombo(SymbolicComposite parent, IntegerInterface style) {
		super(parent, style, "SymbolicCombo");
		this.log = Logger.getLogger(getClass());
		this.length = new ICONST(20);
		//set the text ptoperty and add a descriptor to the variable
		SymbolicString text = new SymbolicString(20);
		Descriptor descriptor = new Descriptor();
		descriptor.setParentId(getParent().getName());
		descriptor.setParentClass(getParent().getSWTClassEquivalent());
		descriptor.setParentIndex(String.valueOf(parent.getIndex()));
		descriptor.setWidgetId(getName());
		descriptor.setWidgetClass(SWT_CLASS_EQUIVALENT);
		descriptor.setWidgetProperty("text");
		descriptor.setWidgetIndex(String.valueOf(getIndex()));
		text.setDescriptor(descriptor);
		this.text = text;
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
			Path.setExecutedMethod("widgetSelected");
			//invoke the method
			selectionListener.widgetSelected(event);
			//generate test cases
			Path.generateTestSuite();
			//set up
			event = new SymbolicSelectionEvent(this);
			Path.addInputVariable(event);
			Path.setExecutedMethod("widgetDefaultSelected");
			//invoke the method
			selectionListener.widgetDefaultSelected(event);
			//generate test cases
			Path.generateTestSuite();
		} else {
			log.warn("There is no selection listener added to SymbolicCombo " + getId());
		}
	}
	
	public void select(IntegerInterface index) {

	}
	
	public void addSelectionListener(SymbolicSelectionListener selectionListener) {
		this.selectionListener = selectionListener;
	}
	
	public Object getLayoutData() {
		return layoutData;
	}

	public void setLayoutData(Object layoutData) {
		this.layoutData = layoutData;
	}
	
	public StringInterface getText() {
		Path.addInputVariable(text);
		return text;
	}

	public void setText(StringInterface text) {
		this.text = text;
	}

	public SymbolicArray<StringInterface> getItems() {
		return items;
	}

	public void setItems(SymbolicArray<StringInterface> items) {
		this.items = items;
	}

	public IntegerInterface getLength() {
		return length;
	}

	public void setLength(IntegerInterface length) {
		this.length = length;
	}
}
