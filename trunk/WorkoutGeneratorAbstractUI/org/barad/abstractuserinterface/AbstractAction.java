package org.barad.abstractuserinterface;

public class AbstractAction extends AbstractWidget{
	private AbstractLabel abstractLabel;
	private String text;
	private int height;
	private int width;
	private int top;
	private int left;
	private int right;
	private boolean visible;
	private boolean enabled;
	private boolean ediable;
	
	public AbstractLabel getAbstractLabel() {
		return abstractLabel;
	}
	
	public void setAbstractLabel(AbstractLabel abstractLabel) {
		this.abstractLabel = abstractLabel;
	}
	
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
	
	public int getHeight() {
		return height;
	}
	
	public void setHeight(int height) {
		this.height = height;
	}
	
	public int getLeft() {
		return left;
	}
	
	public void setLeft(int left) {
		this.left = left;
	}
	
	public int getRight() {
		return right;
	}
	
	public void setRight(int right) {
		this.right = right;
	}
	
	public String getText() {
		return text;
	}
	
	public void setText(String text) {
		this.text = text;
	}
	
	public int getTop() {
		return top;
	}
	
	public void setTop(int top) {
		this.top = top;
	}
	
	public boolean isVisible() {
		return visible;
	}
	
	public void setVisible(boolean visible) {
		this.visible = visible;
	}
	
	public int getWidth() {
		return width;
	}
	
	public void setWidth(int width) {
		this.width = width;
	}	
}
