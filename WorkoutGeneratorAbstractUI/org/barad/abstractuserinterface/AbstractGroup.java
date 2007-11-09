package org.barad.abstractuserinterface;

import java.util.LinkedList;
import java.util.List;

public class AbstractGroup extends AbstractWidget {
	private List<AbstractWidget> widgets;
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
	private int length;
	
	public AbstractGroup() {
		this.widgets = new LinkedList<AbstractWidget>();
	}
	
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
	
	public int getLength() {
		return length;
	}
	
	public void setLength(int length) {
		this.length = length;
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

	public List<AbstractWidget> getWidgets() {
		return widgets;
	}

	public void setWidgets(List<AbstractWidget> widgets) {
		this.widgets = widgets;
	}

	public int getRight() {
		return right;
	}

	public void setRight(int right) {
		this.right = right;
	}
}