package barad.symboliclibrary.ui.widgets;

import java.util.LinkedList;
import java.util.List;

import org.eclipse.swt.widgets.Widget;

import barad.symboliclibrary.integers.ICONST;
import barad.symboliclibrary.integers.IntegerInterface;
import barad.symboliclibrary.ui.layout.SymbolicLayout;

public class SymbolicComposite extends SymbolicWidget {
	public static final long serialVersionUID = 1L;
	private static final String SWT_CLASS_EQUIVALENT = "org.eclipse.swt.widgets.Composite";
	private List<SymbolicWidget> children;
	private SymbolicLayout layout;
	
	public SymbolicComposite() {
		super(null, new ICONST(0), "SymbolicComosite");
		children = new LinkedList<SymbolicWidget>();
	}
	
	@Override
	public String getSWTClassEquivalent() {
		return SWT_CLASS_EQUIVALENT;
	}
	
	/**
	 * Executed event handler methods of each child
	 */
	@Override
	public void executeEventHandlerMethods() {
		for (SymbolicWidget child: children) {
			child.executeEventHandlerMethods();
		}
	}
	
	public SymbolicComposite(Widget parent, IntegerInterface style) {
		super(null, style, "SymbolicComosite");
		children = new LinkedList<SymbolicWidget>();
	}
	
	public SymbolicComposite(SymbolicComposite parent, IntegerInterface style) {
		super(parent, style, "SymbolicComosite");
		children = new LinkedList<SymbolicWidget>();
	}

	public List<SymbolicWidget> getChildren() {
		return children;
	}
	
	public void layout(){
	
	}
	
	public SymbolicLayout getLayout() {
		return layout;
	}

	public void setLayout(SymbolicLayout layout) {
		this.layout = layout;
	}
}
