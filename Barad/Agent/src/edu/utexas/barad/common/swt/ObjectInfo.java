package edu.utexas.barad.common.swt;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * University of Texas at Austin
 * Barad Project, Jul 5, 2007
 */
public class ObjectInfo implements Serializable {
    private String className;
    private String text;
    private GUID guid;
    private ObjectInfo parent;
    private List<ObjectInfo> children = new ArrayList<ObjectInfo>();

    public ObjectInfo(String className, String text, GUID guid) {
        setClassName(className);
        setText(text);
        setGuid(guid);
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
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

    public ObjectInfo getParent() {
        return parent;
    }

    public void setParent(ObjectInfo parent) {
        this.parent = parent;
    }

    public boolean addChild(ObjectInfo child) {
        if(!children.contains(child)) {
            children.add(child);
            return true;
        }
        return false;
    }

    public boolean removeChild(ObjectInfo child) {
        return children.remove(child);
    }

    public int getChildCount() {
        return children.size();
    }

    public ObjectInfo getChildAt(int index) {
        return children.get(index);
    }

    @Override
    public boolean equals(Object object) {
        if (object == null) {
            return false;
        }
        if (object instanceof ObjectInfo) {
            ObjectInfo another = (ObjectInfo) object;
            return another.getGuid().equals(getGuid());
        }
        return false;
    }

    @Override
    public int hashCode() {
        return getGuid().hashCode();
    }

    @Override
    public String toString() {
        return getText();
    }
}