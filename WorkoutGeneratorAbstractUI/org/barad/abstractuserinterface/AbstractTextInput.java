package org.barad.abstractuserinterface;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;

@SuppressWarnings("all")
public class AbstractTextInput {
	private AbstractLabel label;
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
	
	public AbstractTextInput() {
	
	}
	
	public int getLength() {
		return length;
	}
	
	public void setLength(int length) {
		this.length = length;
	}
	
	public boolean getEdiable() {
		return ediable;
	}
	
	public void setEdiable(boolean ediable) {
		this.ediable = ediable;
	}
	
	public boolean getEnabled() {
		return enabled;
	}
	
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
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

	public int getTop() {
		return top;
	}

	public void setTop(int top) {
		this.top = top;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getRight() {
		return right;
	}

	public void setRight(int right) {
		this.right = right;
	}

	public AbstractLabel getLabel() {

		return label;
	}

	public void setLabel(AbstractLabel abstractLabel) {
		this.label = abstractLabel;
	}
	
	public void addEventCallback(OnMethod onMethod, Object owner, String methodSubscriber) {
		//	HashSet methodSubscribers = null;
		//	if ((methodSubscribers = (HashSet)callBackMap.get(methodEvent)) == null) {
		//		methodSubscribers = new HashSet();
		//	}
		//	methodSubscribers.add(methodSubscriber);
		//	callBackMap.put(methodEvent, methodSubscribers);
		}
		
		public void removeEventCallback(String methodEvent, Object owner, String methodSubscriber) {
		//	HashSet methodSubscribers = null;
		//	if ((methodSubscribers = (HashSet)callBackMap.get(methodEvent)) != null) {
		//		methodSubscribers.remove(methodSubscriber);
		//	}
		}
	
	public enum OnMethod {
		GET_LENGTH("getLength", new Class[]{}),
		SET_LENGTH("setLength", new Class[]{Integer.class}),
		GET_EDITABLE("getEditable", new Class[]{}),
		SET_EDITABLE("setEditable", new Class[]{Boolean.class}),
		GET_ENABLED("getEnabled", new Class[]{}),
		SET_ENABLED("setEnabled", new Class[]{Boolean.class}),
		GET_LABEL("getLabel", new Class[]{}),
		SET_LABEL("setLabel", new Class[]{AbstractLabel.class}),
		GET_TEXT("getText", new Class[]{}),
		SET_TEXT("setText", new Class[]{String.class}),
		GET_TOP("getTop", new Class[]{}),
		SET_TOP("setTop", new Class[]{Integer.class}),
		GET_LEFT("getLeft", new Class[]{}),
		SET_LEFT("setLeft", new Class[]{Integer.class}),
		GET_RIGHT("getRight", new Class[]{}),
		SET_RIGHT("setRight", new Class[]{Integer.class}),
		GET_WIDTH("getWidth", new Class[]{}),
		SET_WIDTH("setWidth", new Class[]{Integer.class}),
		GET_HEIGHT("getHeight", new Class[]{}),
		SET_HEIGHT("setHeight", new Class[]{Integer.class}),
		GET_VISIBLE("getVisible", new Class[]{}),
		SET_VISIBLE("setVisible", new Class[]{Boolean.class});
		
		private Method method;
		
		private OnMethod(String name, Class[] parameters) {
			try {
				this.method = getClass().getMethod(name, parameters);
			} catch (NoSuchMethodException nsme) {
				/*never thrown since all is hardcoded*/
			}
		}
		
		public Method getMethod() {
			return method;
		}
	}
}
