package edu.utexas.barad.common.swt;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * University of Texas at Austin
 * Barad Project, Jul 5, 2007
 */
public class WidgetInfo implements Serializable {
    private static final long serialVersionUID = 621832094938826603L;

    private String className;
    private WidgetCategory category;
    private String text;
    private GUID guid;
    private WidgetInfo parent;
    private List<WidgetInfo> children = new ArrayList<WidgetInfo>();
    private WidgetID widgetID;

    public WidgetInfo(String className, String text, GUID guid, WidgetCategory category) {
        setClassName(className);
        setText(text);
        setGuid(guid);
        setCategory(category);
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public WidgetCategory getCategory() {
        return category;
    }

    public void setCategory(WidgetCategory category) {
        this.category = category;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public GUID getGuid() {
        return guid;
    }

    public void setGuid(GUID guid) {
        if (guid == null) {
            throw new NullPointerException("guid");
        }
        this.guid = guid;
    }

    public WidgetInfo getParent() {
        return parent;
    }

    public void setParent(WidgetInfo parent) {
        this.parent = parent;
    }

    public boolean addChild(WidgetInfo child) {
        if(!children.contains(child)) {
            children.add(child);
            return true;
        }
        return false;
    }

    public boolean removeChild(WidgetInfo child) {
        return children.remove(child);
    }

    public int getChildCount() {
        return children.size();
    }

    public WidgetInfo getChildAt(int index) {
        return children.get(index);
    }

    public WidgetID getWidgetID() {
        return widgetID;
    }

    public void setWidgetID(WidgetID widgetID) {
        this.widgetID = widgetID;
    }

    @Override
    public boolean equals(Object object) {
        if (object == null) {
            return false;
        }
        if (object instanceof WidgetInfo) {
            WidgetInfo another = (WidgetInfo) object;
            return another.getWidgetID().equals(getWidgetID());
        }
        return false;
    }

    @Override
    public int hashCode() {
        return getWidgetID().hashCode();
    }

    @Override
    public String toString() {
        return getWidgetID().toString();
    }
}