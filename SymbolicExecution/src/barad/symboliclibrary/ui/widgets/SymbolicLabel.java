package barad.symboliclibrary.ui.widgets;

import barad.symboliclibrary.integers.IntegerInterface;
import barad.symboliclibrary.path.Path;
import barad.symboliclibrary.strings.StringInterface;

//TODO: Finish implemenation
public class SymbolicLabel extends SymbolicWidget {
	public static final long serialVersionUID = 1L;
	private static final String SWT_CLASS_EQUIVALENT = "org.eclipse.swt.widgets.Composite";
	private  StringInterface text;
	private Object layoutData;
	
	public SymbolicLabel(SymbolicComposite parent, IntegerInterface style) {
		super(parent, style, "SymbolicLabel");
	}
	
	@Override
	public String getSWTClassEquivalent() {
		return SymbolicLabel.SWT_CLASS_EQUIVALENT;
	}
	
	@Override
	public void executeEventHandlerMethods() {
		// TODO Auto-generated method stub
		
	}
	
	public StringInterface getText() {
		Path.addInputVariable(text);
		return text;
	}

	public void setText(StringInterface text) {
		this.text = text;
	}
	
	public Object getLayoutData() {
		return layoutData;
	}

	public void setLayoutData(Object layoutData) {
		this.layoutData = layoutData;
	}
}
