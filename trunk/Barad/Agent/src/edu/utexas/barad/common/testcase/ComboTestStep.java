package edu.utexas.barad.common.testcase;

import edu.utexas.barad.common.swt.WidgetInfo;

/**
 * University of Texas at Austin
 * Barad Project, Jan 19, 2008
 */
public class ComboTestStep extends TestStep {
    private int itemIndex = -1;
    private static final long serialVersionUID = 1806490090284633011L;

    public ComboTestStep(TestCaseAction action, WidgetInfo widgetInfo, int itemIndex) {
        super(action, widgetInfo);
        setItemIndex(itemIndex);
    }

    public int getItemIndex() {
        return itemIndex;
    }

    public void setItemIndex(int itemIndex) {
        if (itemIndex < 0) {
            throw new IllegalArgumentException("itemIndex");
        }
        this.itemIndex = itemIndex;
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof ComboTestStep)) {
            return false;
        }
        if (!super.equals(object)) {
            return false;
        }

        ComboTestStep another = (ComboTestStep) object;
        if (itemIndex != another.itemIndex) {
            return false;
        }

        return true;
    }

    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + itemIndex;
        return result;
    }

    public String toHTML() {
        StringBuffer buffer = new StringBuffer();
        buffer.append(TestCaseAction.toHTML(getAction()));
        buffer.append(" on index ").append(getItemIndex());
        return buffer.toString();
    }

    @Override
    public String toString() {
        return "ComboTestStep{" +
                "action=" + getAction() +
                ", widgetInfo=" + getWidgetInfo() +
                ", itemIndex=" + getItemIndex() +
                '}';
    }
}