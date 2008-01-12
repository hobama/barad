package barad.symboliclibrary.ui.widgets;

import barad.symboliclibrary.arrays.SymbolicArray;
import barad.symboliclibrary.integers.ICONST;
import barad.symboliclibrary.integers.IntegerInterface;
import barad.symboliclibrary.path.Path;
import barad.symboliclibrary.strings.StringInterface;
import barad.symboliclibrary.strings.SymbolicString;
import barad.symboliclibrary.test.Descriptor;

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
	private  StringInterface text;
	private IntegerInterface length;
	private Object layoutData;
	private SymbolicArray<StringInterface> items;
	
	public SymbolicCombo(SymbolicComposite parent, IntegerInterface style) {
		super(parent, style, "SymbolicCombo");
		this.length = new ICONST(20);
		//set the text ptoperty and add a descriptor to the variable
		SymbolicString text = new SymbolicString(20);
		Descriptor descriptor = new Descriptor();
		descriptor.setParentId(getParent().getName());
		descriptor.setParentClass(getParent().getSWTClassEquivalent());
		descriptor.setWidgetId(getName());
		descriptor.setWidgetClass(SWT_CLASS_EQUIVALENT);
		descriptor.setWidgetProperty("text");
		descriptor.setIndex(String.valueOf(getIndex()));
		text.setDescriptor(descriptor);
		this.text = text;
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

	@Override
	public String getSWTClassEquivalent() {
		return SWT_CLASS_EQUIVALENT;
	}

	public IntegerInterface getLength() {
		return length;
	}

	public void setLength(IntegerInterface length) {
		this.length = length;
	}
}
