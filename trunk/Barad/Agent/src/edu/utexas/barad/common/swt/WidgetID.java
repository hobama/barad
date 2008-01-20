package edu.utexas.barad.common.swt;

import java.io.Serializable;

/**
 * University of Texas at Austin
 * Barad Project, Jul 31, 2007
 */
public class WidgetID implements Serializable {
    private static final long serialVersionUID = 4834667537324255840L;

    private String className;
    private int childIndex;
    private WidgetID parentID;
    private String text;

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public int getChildIndex() {
        return childIndex;
    }

    public void setChildIndex(int childIndex) {
        this.childIndex = childIndex;
    }

    public WidgetID getParentID() {
        return parentID;
    }

    public void setParentID(WidgetID parentID) {
        this.parentID = parentID;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof WidgetID)) {
            return false;
        }

        WidgetID widgetID = (WidgetID) object;

        if (childIndex != widgetID.childIndex) {
            return false;
        }
        if (!className.equals(widgetID.className)) {
            return false;
        }
        if (parentID != null ? !parentID.equals(widgetID.parentID) : widgetID.parentID != null) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result;
        result = className.hashCode();
        result = 31 * result + childIndex;
        result = 31 * result + (parentID != null ? parentID.hashCode() : 0);
        return result;
    }

    public String toShortDisplayString() {
        return "WidgetID{" +
                "className='" + className + '\'' +
                ", childIndex=" + childIndex +
                ", text='" + text + '\'' +
                '}';
    }

    @Override
    public String toString() {
        return "WidgetID{" +
                "className='" + className + '\'' +
                ", childIndex=" + childIndex +
                ", text='" + text + '\'' +
                ", parentID=" + parentID +
                '}';
    }
}