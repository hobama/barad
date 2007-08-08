package edu.utexas.barad.common.testcase;

/**
 * University of Texas at Austin
 * Barad Project, Jul 30, 2007
 */
public enum TestCaseAction {
    LEFT_MOUSE_CLICK("LeftMouseClick"),
    RIGHT_MOUSE_CLICK("RightMouseClick"),
    ENTER_TEXT("EnterText");

    private String displayName;

    TestCaseAction(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    @Override
    public String toString() {
        return getDisplayName();
    }
}