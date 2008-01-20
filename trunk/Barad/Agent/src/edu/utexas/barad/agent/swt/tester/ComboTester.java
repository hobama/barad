package edu.utexas.barad.agent.swt.tester;

import edu.utexas.barad.agent.script.Condition;
import edu.utexas.barad.agent.swt.Displays.IntResult;
import edu.utexas.barad.agent.swt.Displays.Result;
import edu.utexas.barad.agent.swt.Displays.StringResult;
import edu.utexas.barad.agent.swt.Robot;
import edu.utexas.barad.agent.swt.proxy.SWTProxy;
import edu.utexas.barad.agent.swt.proxy.graphics.PointProxy;
import edu.utexas.barad.agent.swt.proxy.graphics.RectangleProxy;
import edu.utexas.barad.agent.swt.proxy.widgets.ComboProxy;
import edu.utexas.barad.agent.swt.proxy.widgets.WidgetProxy;
import org.apache.log4j.Logger;

/**
 * A tester for {@link ComboProxy}s.
 */
public class ComboTester extends CompositeTester implements WidgetTester.Textable {
    private static final Logger logger = Logger.getLogger(ComboTester.class);

    /**
     * Factory method.
     */
    public static ComboTester getComboTester() {
        return (ComboTester) getTester(ComboProxy.class);
    }

    /**
     * Constructs a new {@link ComboTester} associated with the specified {@link Robot}.
     */
    public ComboTester(Robot swtRobot) {
        super(swtRobot);
    }

    /**
     * Proxy for {@link ComboProxy#getItemCount()}. <p/>
     *
     * @param combo the combo under test.
     * @return the number of items.
     */
    public int getItemCount(final ComboProxy combo) {
        checkWidget(combo);
        return syncExec(new IntResult() {
            public int result() {
                return combo.getItemCount();
            }
        });
    }

    /**
     * Proxy for {@link ComboProxy#getItemHeight()}. <p/>
     *
     * @param combo the combo under test.
     * @return the height of one item.
     */
    public int getItemHeight(final ComboProxy combo) {
        checkWidget(combo);
        return syncExec(new IntResult() {
            public int result() {
                return combo.getItemHeight();
            }
        });
    }

    /**
     * Proxy for {@link ComboProxy#getItems()}. <p/>
     *
     * @param combo the combo under test.
     * @return the items in the combo's list.
     */
    public String[] getItems(final ComboProxy combo) {
        checkWidget(combo);
        return (String[]) syncExec(new Result() {
            public Object result() {
                return combo.getItems();
            }
        });
    }

    /**
     * Proxy for {@link ComboProxy#getSelection()}. <p/>
     *
     * @param combo the combo under test.
     * @return a point representing the selection start and end.
     */
    public PointProxy getSelection(final ComboProxy combo) {
        checkWidget(combo);
        return (PointProxy) syncExec(new Result() {
            public Object result() {
                return combo.getSelection();
            }
        });
    }

    /**
     * Proxy for {@link ComboProxy#getSelectionIndex()}. <p/>
     *
     * @param combo the combo under test.
     * @return the selected index.
     */
    public int getSelectionIndex(final ComboProxy combo) {
        checkWidget(combo);
        return syncExec(new IntResult() {
            public int result() {
                return combo.getSelectionIndex();
            }
        });
    }

    /**
     * Proxy for {@link ComboProxy#getText()}. <p/>
     *
     * @param combo the combo under test.
     * @return the contents of the text field.
     */
    public String getText(final ComboProxy combo) {
        checkWidget(combo);
        return syncExec(new StringResult() {
            public String result() {
                return combo.getText();
            }
        });
    }

    /**
     * @see Textable#getText(WidgetProxy)
     */
    public String getText(WidgetProxy widget) {
        return getText((ComboProxy) widget);
    }

    public boolean isTextEditable(WidgetProxy widget) {
        int style = getStyle(widget);
        return (style & SWTProxy.READ_ONLY) == 0;
    }

    /**
     * Proxy for {@link ComboProxy#getTextHeight()}. <p/>
     *
     * @param combo the combo under test.
     * @return the text height.
     */
    public int getTextHeight(final ComboProxy combo) {
        checkWidget(combo);
        return syncExec(new IntResult() {
            public int result() {
                return combo.getTextHeight();
            }
        });
    }

    /**
     * Proxy for {@link ComboProxy#getTextLimit()}. <p/>
     *
     * @param combo the combo under test.
     * @return the text limit.
     */
    public int getTextLimit(final ComboProxy combo) {
        checkWidget(combo);
        return syncExec(new IntResult() {
            public int result() {
                return combo.getTextLimit();
            }
        });
    }

    /* End getters */

    /**
     * Drop down the menu for the given Combo box WARNING: This method is platform-dependent.
     */
    protected void dropDownCombo(ComboProxy combo) {
        checkWidget(combo);
        int style = getStyle(combo);
        final int BUTTON_SIZE = 16;
        if ((style & SWTProxy.DROP_DOWN) == SWTProxy.DROP_DOWN) {
            RectangleProxy bounds = getGlobalBounds(combo);
            mouseMove(bounds.__fieldGetx() + bounds.__fieldGetwidth() - BUTTON_SIZE / 2, bounds.__fieldGety() + bounds.__fieldGetheight() - BUTTON_SIZE / 2);
            mousePress(SWTProxy.BUTTON1);
            mouseRelease(SWTProxy.BUTTON1);
            actionWaitForIdle();
        }
    }

    /**
     * Move the mouse pointer over the item with the given index
     */
    public void mouseMoveIndex(ComboProxy combo, int index) {
        checkWidget(combo);
        int style = getStyle(combo);
        if ((style & SWTProxy.DROP_DOWN) == SWTProxy.DROP_DOWN) {
            // TODO Add code to scroll down and move the mouse pointer;
            // may not be possible b/c combo.getVerticalBar() returns null even when the
            // bar on the drop-down is visible
        } else {// SWT.SIMPLE
            // TODO Add code to scroll so item is visible and move
            // pointer over item
        }
    }

    /**
     * Select the item from the Combo at the given index.
     *
     * @param combo Combo from which to select
     * @param index Index of item to select
     */
    public void actionSelectIndex(final ComboProxy combo, final int index) {
        checkWidget(combo);
        actionFocus(combo);
        int current = getSelectionIndex(combo);
        dropDownCombo(combo);
        while (current != index) {
            if (current < index) {
                actionKeyPress(SWTProxy.ARROW_DOWN);
                actionKeyRelease(SWTProxy.ARROW_DOWN);
                current++;
            } else {
                actionKeyPress(SWTProxy.ARROW_UP);
                actionKeyRelease(SWTProxy.ARROW_UP);
                current--;
            }
        }
        actionKeyChar(SWTProxy.CR);
        actionWaitForIdle();
        int selected = getSelectionIndex(combo);
        if (selected != index) {
            String msg = "Was not able to select the correct index for Combo:" + selected + "!="
                    + index;
            throw new AssertionError(msg);
        }
    }

    /**
     * Select the given item from the Combo.
     *
     * @param combo Combo from which to select
     * @param item  String to select
     */
    public void actionSelectItem(final ComboProxy combo, String item) {
        checkWidget(combo);
        String[] items = getItems(combo);
        boolean found = false;
        for (int i = 0; i < items.length; i++) {
            if (item.equals(items[i])) {
                found = true;
                actionSelectIndex(combo, i);
                break;
            }
        }
        if (!found) {
           /*
            * @todo: i think we should REALLY throw a WidgetNotFound exception, here, but that
            * would require an api change, and i think that should be part of a change that tries
            * to standardize on that, i.e. in TreeItemTester, and MenuItemTester. And the signature
            * should, also, throw a MultipleWidgetsFoundException.
            */
            logger.debug("actionSelectItem: item \"" + item + "\" not found");
        }
    }

    /**
     * Returns the item at the given index.
     *
     * @param combo Combo from which to obtain the item
     * @param index Index of the item
     * @return the item at the given index, or null if index is out-of-bounds
     */
    public String getItem(final ComboProxy combo, final int index) {
        checkWidget(combo);
        return syncExec(new StringResult() {
            public String result() {
                return combo.getItem(index);
            }
        });
    }

    /**
     * Indicates if the given index is the index of the currently selected item.
     *
     * @param combo Combo to check
     * @param index Index of the item to check
     * @return whether the item at the given index is selected
     */
    public boolean assertIndexSelected(final ComboProxy combo, int index) {
        checkWidget(combo);
        int selected = getSelectionIndex(combo);
        return selected == index;
    }

    /**
     * Indicates if the given item is currently selected.
     *
     * @param combo Combo to check
     * @param item  Item to check
     * @return whether the given item is selected
     */
    public boolean assertItemSelected(final ComboProxy combo, String item) {
        checkWidget(combo);
        int selected = getSelectionIndex(combo);
        if (selected != -1) {
            String selectedItem = getItem(combo, selected);
            return item.equals(selectedItem);
        }
        return false;
    }

    /**
     * Proxy for {@link ComboProxy#setText(String)}.
     */
    public void setText(final ComboProxy combo, final String string) {
        checkWidget(combo);
        actionFocus(combo);
        syncExec(new Runnable() {
            public void run() {
                combo.setText(string);
            }
        });
        wait(new Condition() {
            public boolean test() {
                return string.equals(getText(combo));
            }
        });
    }
}
