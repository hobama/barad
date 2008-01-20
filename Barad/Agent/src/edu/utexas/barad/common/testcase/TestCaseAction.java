package edu.utexas.barad.common.testcase;

/**
 * University of Texas at Austin
 * Barad Project, Jul 30, 2007
 */
public enum TestCaseAction {
    LEFT_MOUSE_CLICK("LeftMouseClick"),
    RIGHT_MOUSE_CLICK("RightMouseClick"),
    ENTER_TEXT("EnterText"),
    SELECT_ITEM("SelectItem");

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

    public static String toHTML(TestCaseAction testCaseAction) {
        if (testCaseAction == null) {
            return "Unknown";
        }
        StringBuffer buffer = new StringBuffer();
        switch (testCaseAction) {
            case LEFT_MOUSE_CLICK: {
                buffer.append("Left Mouse Click");
                break;
            }

            case RIGHT_MOUSE_CLICK: {
                buffer.append("Right Mouse Click");
                break;
            }

            case ENTER_TEXT: {
                buffer.append("Input Text");
                break;
            }

            case SELECT_ITEM: {
                buffer.append("Select Item");
                break;
            }

            default: {
                break;
            }

        }
        return buffer.toString();
    }
}