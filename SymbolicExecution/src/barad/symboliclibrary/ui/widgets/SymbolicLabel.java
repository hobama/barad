package barad.symboliclibrary.ui.widgets;

import barad.symboliclibrary.integers.IntegerInterface;

//TODO: Finish implemenation
public class SymbolicLabel extends SymbolicWidget {
	public static final long serialVersionUID = 1L;
	private static final String SWT_CLASS_EQUIVALENT = "org.eclipse.swt.widgets.Composite";
	
	public SymbolicLabel(SymbolicComposite parent, IntegerInterface style) {
		super(parent, style, "SymbolicLabel");
	}
	
	@Override
	public String getSWTClassEquivalent() {
		return SymbolicLabel.SWT_CLASS_EQUIVALENT;
	}

}
