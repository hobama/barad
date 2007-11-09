package org.barad.abstractuserinterface;

public class AbstractSelectionInput extends AbstractWidget {
	private AbstractLabel abstractLabel;
	private String[] options;
	private String text;
	private boolean visible;
	private boolean enabled;
	private boolean editable;
	
	public AbstractSelectionInput() {
		this.visible = true;
		this.enabled = true;
		this.editable = true;
	}
	
	public boolean isEditable() {
		return editable;
	}
	
	public void setEditable(boolean ediable) {
		this.editable = ediable;
	}
	
	public boolean isEnabled() {
		return enabled;
	}
	
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	
	public AbstractLabel getLabel() {
		return abstractLabel;
	}
	
	public void setLabel(AbstractLabel abstractLabel) {
		this.abstractLabel = abstractLabel;
	}
	
	public String[] getOptions() {
		return options;
	}
	
	public void setOptions(String[] options) {
		this.options = options;
	}
	
	public boolean isVisible() {
		return visible;
	}
	
	public void setVisible(boolean visible) {
		this.visible = visible;
	}
	
	public void select(int option) {
		if (option > options.length) {
			throw new IndexOutOfBoundsException();
		}
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
}
