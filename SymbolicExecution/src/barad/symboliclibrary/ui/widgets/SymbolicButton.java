package barad.symboliclibrary.ui.widgets;

import barad.symboliclibrary.integers.IntegerInterface;
import barad.symboliclibrary.strings.StringInterface;
import barad.symboliclibrary.ui.events.listeners.SymbolicSelectionListener;

@SuppressWarnings("unused")
public class SymbolicButton extends SymbolicWidget {
	public static final long serialVersionUID = 1L;
	private static final String SWT_CLASS_EQUIVALENT = "org.eclipse.swt.widgets.Button";
	private Object layoutData;
	private StringInterface text;
	private SymbolicSelectionListener selectionListener;

	public SymbolicButton(SymbolicComposite parent, IntegerInterface style) {
		super(parent, style, "SymbolicButton");
	}
	
	@Override
	public String getSWTClassEquivalent() {
		return SWT_CLASS_EQUIVALENT;
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
}
