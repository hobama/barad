package barad.symboliclibrary.ui.widgets;

import barad.symboliclibrary.integers.ICONST;
import barad.symboliclibrary.integers.IntegerInterface;
import barad.symboliclibrary.path.Path;
import barad.symboliclibrary.strings.StringInterface;
import barad.symboliclibrary.strings.SymbolicString;
import barad.symboliclibrary.test.Descriptor;

/**
 * Symbolic widget used to replace the Text instances
 * from the SWT library. All private fields accessed
 * during the opration of the widget from other classes
 * are replaced with symbolic equivalents.
 * @author svetoslavganov
 *
 */
public class SymbolicText extends SymbolicWidget {
	public static final long serialVersionUID = 1L;
	private static final String SWT_CLASS_EQUIVALENT = "org.eclipse.swt.widgets.Text";
	private IntegerInterface length;
	private StringInterface text;
	private Object layoutData;
	
	public SymbolicText(SymbolicComposite parent, IntegerInterface style) {
		super(parent, style, "SymbolicText");
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
	
	public StringInterface getText() {
		Path.addInputVariable(text);
		return text;
	}

	public void setText(StringInterface text) {
		this.text = text;
	}

	public void setLayoutData(Object layoutData) {
		this.layoutData = layoutData;
	}

	public Object getLayoutData() {
		return layoutData;
	}

	public IntegerInterface getLength() {
		return length;
	}

	public void setLength(IntegerInterface length) {
		this.length = length;
	}
	
	@Override
	public String getSWTClassEquivalent() {
		return SWT_CLASS_EQUIVALENT;
	}
}
