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
    public int hashCode() {
        int hashCode = 0;
        if (getClassName() != null) {
            hashCode += getClassName().hashCode();
        }
        hashCode += getChildIndex();
        if (getText() != null) {
            hashCode += getText().hashCode();
        }
        if (getParentID() != null) {
            hashCode += getParentID().hashCode();
        }
        return hashCode;
    }

    @Override
    public boolean equals(Object object) {
        if (object instanceof WidgetID) {
            WidgetID another = (WidgetID) object;

            if (!another.getClassName().equals(getClassName())) {
                return false;
            }

            if (another.getChildIndex() != getChildIndex()) {
                return false;
            }

            if (another.getText() != null && getText() == null) {
                return false;
            }
            if (another.getText() == null && getText() != null) {
                return false;
            }
            if (another.getText() != null && getText() != null) {
                if (!another.getText().equals(getText())) {
                    return false;
                }
            }

            if (another.getParentID() == null && getParentID() != null) {
                return false;
            }
            if (another.getParentID() != null && getParentID() == null) {
                return false;
            }
            if (another.getParentID() != null && getParentID() != null) {
                if (!another.getParentID().equals(getParentID())) {
                    return false;
                }
            }

            return true;
        }

        return false;
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