package edu.utexas.barad.common.testcase;

import edu.utexas.barad.common.swt.WidgetInfo;

/**
 * University of Texas at Austin
 * Barad Project, Jan 19, 2008
 */
public class EnterTextTestStep extends TestStep {
    private String textToEnter;

    public EnterTextTestStep(TestCaseAction action, WidgetInfo widgetInfo, String textToEnter) {
        super(action, widgetInfo);
        setTextToEnter(textToEnter);
    }

    public String getTextToEnter() {
        return textToEnter;
    }

    public void setTextToEnter(String textToEnter) {
        if (textToEnter == null) {
            throw new NullPointerException("textToEnter");
        }
        this.textToEnter = textToEnter;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof EnterTextTestStep)) {
            return false;
        }
        if (!super.equals(object)) {
            return false;
        }

        EnterTextTestStep another = (EnterTextTestStep) object;
        if (!textToEnter.equals(another.textToEnter)) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + textToEnter.hashCode();
        return result;
    }

    @Override
    public String toHTML() {
        StringBuffer buffer = new StringBuffer();
        buffer.append(TestCaseAction.toHTML(getAction()));
        buffer.append(" on ");
        if (getWidgetInfo() != null) {
            buffer.append(getWidgetInfo().toHTML());
            buffer.append(", ");
        }
        buffer.append("textToEnter=").append(getTextToEnter());
        return buffer.toString();
    }

    @Override
    public String toString() {
        return "EnterTextTestStep{" +
                "action=" + getAction() +
                ", widgetInfo=" + getWidgetInfo() +
                ", textToEnter=" + getTextToEnter() +
                '}';
    }
}