package edu.utexas.barad.common.testcase;

import edu.utexas.barad.agent.swt.widgets.MessageBoxHelper.Button;

/**
 * University of Texas at Austin
 * Barad Project, Aug 6, 2007
 */
public class MessageBoxTestStep extends TestStep {
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

    @Override
    public String toString() {
        return "MessageBoxTestStep{" +
                "action=" + getAction() +
                ", widgetInfo=" + getWidgetInfo() +
                ", button=" + getButton() +
                '}';
    }
}