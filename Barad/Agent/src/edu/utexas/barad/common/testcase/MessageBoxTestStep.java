package edu.utexas.barad.common.testcase;

import edu.utexas.barad.agent.swt.widgets.MessageBoxHelper.Button;

/**
 * University of Texas at Austin
 * Barad Project, Aug 6, 2007
 */
public class MessageBoxTestStep extends TestStep {
    private static final long serialVersionUID = 7319865153413215980L;

    private Button button;

    public MessageBoxTestStep(TestCaseAction action, Button button) {
        super(action, null);
        setButton(button);
    }

    public Button getButton() {
        return button;
    }

    public void setButton(Button button) {
        this.button = button;
    }

    public String toHTML() {
        StringBuffer buffer = new StringBuffer();
        buffer.append(TestCaseAction.toHTML(getAction()));
        buffer.append(" on ");
        if (button != null) {
            buffer.append(button.toString());
        }
        return buffer.toString();
    }

    @Override
    public String toString() {
        return "MessageBoxTestStep{" +
                "action=" + getAction() +
                ", widgetInfo=" + getWidgetInfo() +
                ", button=" + getButton() +
                '}';
    }
}