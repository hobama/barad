package edu.utexas.barad.common.swt;

import edu.utexas.barad.agent.swt.proxy.SWTProxyFactory;

/**
 * University of Texas at Austin
 * Barad Project, Jul 10, 2007
 */
public enum WidgetCategory {    
    BUTTON("Button"),
    DISPLAY("Display"),
    SHELL("Shell"),
    LABEL("Label"),
    MENU("Menu"),
    TAB("Tab"),
    TABLE("Table"),
    TEXT("Text"),
    TREE("Tree"),
    WINDOW("Window"),
    OTHER("Other");

    private String displayName;

    WidgetCategory(String displayName) {
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