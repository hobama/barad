package barad.symboliclibrary.ui.widgets;

import barad.symboliclibrary.integers.IntegerInterface;
import barad.symboliclibrary.ui.common.SymbolicMockObject;

/**
 * The super class for all symbolic widgets.
 * Provides some basic functionality such as
 * identity and style
 * @author svetoslavganov
 *
 */
public abstract class SymbolicWidget extends SymbolicMockObject { 
	private int index;
	private IntegerInterface style;
	private SymbolicComposite parent;

	SymbolicWidget(SymbolicComposite parent, IntegerInterface style, String name) {
		super(name);
		this.parent = parent;
		this.style = style;	
		if (parent != null) {
			this.parent.getChildren().add(this);
			this.setIndex(parent.getChildren().size() - 1);
		}
	}

	public void setStyle(IntegerInterface style) {
		this.style = style;
	}

	public IntegerInterface getStyle() {
		return style;
	}

	public SymbolicComposite getParent() {
		return parent;
	}

	public void setParent(SymbolicComposite parent) {
		this.parent = parent;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public abstract String getSWTClassEquivalent();
}
