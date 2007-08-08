package edu.utexas.barad.agent.swt.tester;

import edu.utexas.barad.agent.exceptions.WidgetNotFoundException;
import edu.utexas.barad.agent.swt.Displays;
import edu.utexas.barad.agent.swt.Displays.BooleanResult;
import edu.utexas.barad.agent.swt.Displays.IntResult;
import edu.utexas.barad.agent.swt.Displays.Result;
import edu.utexas.barad.agent.swt.SWTWorkarounds;
import edu.utexas.barad.agent.swt.Robot;
import edu.utexas.barad.agent.swt.proxy.graphics.RectangleProxy;
import edu.utexas.barad.agent.swt.proxy.widgets.ItemProxy;
import edu.utexas.barad.agent.swt.proxy.widgets.MenuItemProxy;
import edu.utexas.barad.agent.swt.proxy.widgets.MenuProxy;
import edu.utexas.barad.agent.swt.proxy.widgets.WidgetProxy;

import java.util.Iterator;

/**
 * University of Texas at Austin
 * Barad Project, Jul 25, 2007
 */
public class MenuItemTester extends ItemTester {
    public static final String ARM_LISTENER_NAME = "a4sArmListener";
    public static final String SELECTION_LISTENER_NAME = "a4sSelectionListener";
    public static final String WATCHER_NAME = "a4sWatcher";
    public static final int PATH_CLICKING_WAIT_TIME = 500000;
    public static final String DEFAULT_MENUITEM_PATH_DELIMITER = "/";

    /**
     * Factory method.
     */
    public static MenuItemTester getMenuItemTester() {
        return (MenuItemTester) getTester(MenuItemProxy.class);
    }

    /**
     * Constructs a new {@link MenuItemTester} associated with the specified {@link TesterRobot}.
     */
    public MenuItemTester(Robot swtRobot) {
        super(swtRobot);
    }

    /* Actions */

    public void actionClickItem(MenuItemProxy item, ItemPath path) {
        checkWidget(item);
        clickItem(item, path);
    }

    public void actionClickItem(MenuItemProxy item, String path) {
        actionClickItem(item, new ItemPath(path));
    }

    public void actionClickItem(MenuItemProxy item, String path, String delimiter) {
        actionClickItem(item, new ItemPath(path, delimiter));
    }

    /**
     * Clicks on a child of a {@link MenuItemProxy} given an {@link ItemPath}.
     *
     * @param item the {@link MenuItemProxy}
     * @param path the path to the desired {@link edu.utexas.barad.agent.swt.proxy.widgets.MenuItemProxy}
     */
    void clickItem(MenuItemProxy item, ItemPath path) {
        clickItem(item, path.iterator());
    }

    private void clickItem(MenuItemProxy item, Iterator<String> iterator) {
        click(item);
        if (iterator.hasNext()) {
            String text = iterator.next();
            MenuProxy menu = getMenu(item);
            if (menu == null)
                throw new WidgetNotFoundException("No menu for item, item=" + item, null);
            MenuTester menuTester = MenuTester.getMenuTester();
            menuTester.waitVisible(menu);
            MenuItemProxy[] items = menuTester.getItems(menu);
            clickItem(findItem(items, text), iterator);
        }
    }

    MenuItemProxy findItem(MenuItemProxy[] items, String text) {
        for (MenuItemProxy item : items) {
            if (text.equals(getText(item)))
                return item;
        }
        throw new WidgetNotFoundException(text, null);
    }

    /* Proxies */

    /**
     * Proxy for {@link MenuItemProxy#getAccelerator()}. <p/>
     *
     * @param menuItem the menuItem under test.
     * @return the accelerator.
     */
    public int getAccelerator(final MenuItemProxy menuItem) {
        checkWidget(menuItem);
        return syncExec(new IntResult() {
            public int result() {
                return menuItem.getAccelerator();
            }
        });
    }

    /**
     * Proxy for {@link MenuItemProxy#getEnabled()}. <p/>
     *
     * @param menuItem the menuItem under test.
     * @return the menuItem's enabled state.
     */
    public boolean getEnabled(final MenuItemProxy menuItem) {
        checkWidget(menuItem);
        return syncExec(new BooleanResult() {
            public boolean result() {
                return menuItem.getEnabled();
            }
        });
    }

    /**
     * Proxy for {@link MenuItemProxy#getSelection()}. <p/>
     *
     * @param menuItem the menuItem under test.
     * @return true if the menuItem is selected.
     */
    public boolean getSelection(final MenuItemProxy menuItem) {
        checkWidget(menuItem);
        return syncExec(new BooleanResult() {
            public boolean result() {
                return menuItem.getSelection();
            }
        });
    }

    /**
     * Note that this method is different than {@link WidgetTester#getMenu(edu.utexas.barad.agent.swt.proxy.widgets.WidgetProxy)}.
     *
     * @see MenuItemProxy#getMenu()
     */
    public MenuProxy getMenu(final MenuItemProxy menuItem) {
        checkWidget(menuItem);
        return (MenuProxy) syncExec(new Result() {
            public Object result() {
                return menuItem.getMenu();
            }
        });
    }

    /**
     * Gets the top-most (root) {@link MenuProxy} of the menu hierarchy a {@link MenuItemProxy} is in.
     */
    public MenuProxy getRootMenu(MenuItemProxy item) {
        return MenuTester.getMenuTester().getRootMenu(getParent(item));
    }

    /**
     * Proxy for {@link MenuItemProxy#getParent()}. <p/>
     *
     * @param menuItem the menuItem under test.
     * @return the menuItem's parent.
     */
    public MenuProxy getParent(final MenuItemProxy menuItem) {
        checkWidget(menuItem);
        return (MenuProxy) syncExec(new Result() {
            public Object result() {
                return menuItem.getParent();
            }
        });
    }

    /**
     * Proxy for {@link MenuItemProxy#isEnabled()}. <p/>
     *
     * @param menuItem the menuItem under test.
     * @return true if the menuItem is enabled.
     */
    public boolean isEnabled(final MenuItemProxy menuItem) {
        checkWidget(menuItem);
        return syncExec(new Displays.BooleanResult() {
            public boolean result() {
                return menuItem.isEnabled();
            }
        });
    }

    public boolean isVisible(final MenuItemProxy menuItem) {
        MenuProxy menu = getParent(menuItem);
        return MenuTester.getMenuTester().isVisible(menu);
    }

    public boolean isVisible(WidgetProxy widget) {
        if (widget instanceof MenuItemProxy)
            return isVisible((MenuItemProxy) widget);
        return super.isVisible(widget);
    }

    /**
     * Computes the bounds of the menuItem given. <p/>
     *
     * @param menuItem the menuItem under test.
     * @return the bounds of the menuItem.
     */
    public RectangleProxy getBounds(final MenuItemProxy menuItem) {
        checkWidget(menuItem);
        return (RectangleProxy) syncExec(new Result() {
            public Object result() {
                return SWTWorkarounds.getBounds(menuItem);
            }
        });
    }

    /* Miscellaneous */

    /**
     * @see ItemTester#getParentItem(edu.utexas.barad.agent.swt.proxy.widgets.ItemProxy)
     */
    protected ItemProxy getParentItem(ItemProxy item) {
        MenuProxy menu = getParent((MenuItemProxy) item);
        return MenuTester.getMenuTester().getParentItem(menu);
    }
}