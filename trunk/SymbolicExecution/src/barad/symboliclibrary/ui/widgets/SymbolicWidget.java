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
/**
 * @author svetoslavganov
 *
 */
public abstract class SymbolicWidget extends SymbolicMockObject { 
	private int index;
	private IntegerInterface style;
	private SymbolicComposite parent;
	private IntegerInterface width;
	private IntegerInterface height;

	public SymbolicWidget(SymbolicComposite parent, IntegerInterface style, String name) {
		super(name);
		this.parent = parent;
		this.style = style;	
		if (parent != null) {
			this.parent.getChildren().add(this);
			this.setIndex(parent.getChildren().size() - 1);
		}
	}
	
	public abstract String getSWTClassEquivalent();
	
	public abstract void executeEventHandlerMethods();

	public void setSize(IntegerInterface height, IntegerInterface width) {
		this.height = height;
		this.width = height;
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

	public IntegerInterface getWidth() {
		return width;
	}

	public void setWidth(IntegerInterface width) {
		this.width = width;
	}
	
	public IntegerInterface getHeight() {
		return height;
	}

	public void setHeight(IntegerInterface height) {
		this.height = height;
	}
}
