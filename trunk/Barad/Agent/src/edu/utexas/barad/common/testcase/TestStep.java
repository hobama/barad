package edu.utexas.barad.common.testcase;

import edu.utexas.barad.common.swt.WidgetInfo;

import java.io.Serializable;

/**
 * University of Texas at Austin
 * Barad Project, Jul 30, 2007
 */
public class TestStep implements Cloneable, Serializable {
    private static final long serialVersionUID = -6664793161088229613L;

    private TestCaseAction action;
    private WidgetInfo widgetInfo;

    public TestStep(TestCaseAction action, WidgetInfo widgetInfo) {
        setAction(action);
        setWidgetInfo(widgetInfo);
    }

    public TestCaseAction getAction() {
        return action;
    }

    public void setAction(TestCaseAction action) {
        this.action = action;
    }

    public WidgetInfo getWidgetInfo() {
        return widgetInfo;
    }

    public void setWidgetInfo(WidgetInfo widgetInfo) {
        this.widgetInfo = widgetInfo;
    }

    @Override
    public String toString() {
        return "TestStep{" +
                "action=" + action +
                ", widgetInfo=" + widgetInfo +
                '}';
    }

    public String toHTML() {
        StringBuffer buffer = new StringBuffer();
        buffer.append(TestCaseAction.toHTML(action));
        buffer.append(" on ");
        if (widgetInfo != null) {
            buffer.append(widgetInfo.toHTML());
        }
        return buffer.toString();
    }

    @Override
    public boolean equals(Object object) {
        if (object instanceof TestStep) {
            TestStep another = (TestStep) object;
            if (another.action != this.action) {
                return false;
            }
            if (another.widgetInfo != null && this.widgetInfo == null) {
                return false;
            }
            if (another.widgetInfo == null && this.widgetInfo != null) {
                return false;
            }
            if (another.widgetInfo != null && this.widgetInfo != null && !another.widgetInfo.equals(this.widgetInfo)) {
                return false;
            }
            return true;
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hashCode = 0;
        hashCode += action.hashCode();
        hashCode += widgetInfo != null ? widgetInfo.hashCode() : 0;
        return hashCode;
    }
}