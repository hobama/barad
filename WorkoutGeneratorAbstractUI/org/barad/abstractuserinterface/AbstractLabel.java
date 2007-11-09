package org.barad.abstractuserinterface;

public class AbstractLabel extends AbstractWidget {
	private AbstractWidget widget;
	private String text;
	private Object font;
	private int height;
	private int width;
	private int top;
	private int left;
	private boolean visible;
	private boolean enabled;
	private boolean editable;
	private int speed;
	
	public boolean isEditable() {
		return editable;
	}
	
	public void setEditable(boolean editable) {
		this.editable = editable;
	}
	
	public boolean isEnabled() {
		return enabled;
	}
	
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	
	public Object getFont() {
		return font;
	}
	
	public void setFont(Object font) {
		this.font = font;
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
	
	public int getSpeed() {
		return speed;
	}
	
	public void setSpeed(int speed) {
		this.speed = speed;
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
	
	public void setWidth(int weight) {
		this.width = weight;
	}

	public AbstractWidget getWidget() {
		return widget;
	}

	public void setWidget(AbstractWidget widget) {
		this.widget = widget;
	}
}
