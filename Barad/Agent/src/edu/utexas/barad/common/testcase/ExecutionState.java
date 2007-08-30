package edu.utexas.barad.common.testcase;

/**
 * University of Texas at Austin
 * Barad Project, Aug 25, 2007
 */
public enum ExecutionState {
    STARTED("STARTED"),
    PAUSED("PAUSED"),
    STOPPED("STOPPED");

    private String displayName;

    ExecutionState(String displayName) {
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