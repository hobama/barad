package org.barad.abstractuserinterface;

public class AbstractTextOutput {
	private String label;
	private String text;
	private boolean visible;
	private boolean enabled;
	private boolean ediable;
	private int length;
	
	public boolean isEdiable() {
		return ediable;
	}
	public void setEdiable(boolean ediable) {
		this.ediable = ediable;
	}
	public boolean isEnabled() {
		return enabled;
	}
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public boolean isVisible() {
		return visible;
	}
	public void setVisible(boolean visible) {
		this.visible = visible;
	}
	public int getLength() {
		return length;
	}
	public void setLength(int length) {
		this.length = length;
	}
	
}
