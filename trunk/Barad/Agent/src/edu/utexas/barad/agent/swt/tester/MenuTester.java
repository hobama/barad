package edu.utexas.barad.agent.swt.tester;

import edu.utexas.barad.agent.swt.Displays;
import edu.utexas.barad.agent.swt.Displays.BooleanResult;
import edu.utexas.barad.agent.swt.Displays.IntResult;
import edu.utexas.barad.agent.swt.Displays.Result;
import edu.utexas.barad.agent.swt.Displays.StringResult;
import edu.utexas.barad.agent.swt.SWTWorkarounds;
import edu.utexas.barad.agent.swt.Robot;
import edu.utexas.barad.agent.swt.proxy.SWTProxy;
import edu.utexas.barad.agent.swt.proxy.graphics.RectangleProxy;
import edu.utexas.barad.agent.swt.proxy.widgets.*;

/**
 * University of Texas at Austin
 * Barad Project, Jul 25, 2007
 */
public class MenuTester extends WidgetTester {
    public static final String POPUP_ROOT_FLAG = "POPUP_ROOT";

    public static final String ROOT_FLAG = "MENU_ROOT";

    // TODO Put methods from WidgetTester into MenuTester and add getters

    /**
     * Factory method.
     */
    public static MenuTester getMenuTester() {
        return (MenuTester) WidgetTester.getTester(MenuProxy.class);
    }

    /**
     * Constructs a new {@link MenuTester} associated with the specified {@link TesterRobot}.
     */
    public MenuTester(Robot swtRobot) {
        super(swtRobot);
    }

    /* Actions */

    public void actionClickItem(MenuProxy menu, String path) {
        actionClickItem(menu, new ItemPath(path));
    }

    public void actionClickItem(MenuProxy menu, String path, String delimiter) {
        actionClickItem(menu, new ItemPath(path, delimiter));
    }

    public void actionClickItem(MenuProxy menu, ItemPath path) {
        checkWidget(menu);
        if (!isVisible(menu))
            throw new IllegalStateException("menu is not visible");
        clickItem(menu, path);
    }

    void clickItem(MenuProxy menu, ItemPath path) {
        MenuItemProxy item = getItem(menu, path.getSegment(0));
        MenuItemTester.getMenuItemTester().actionClickItem(item, path.subPath(1));
    }

    MenuItemProxy getItem(MenuProxy menu, String text) {
        MenuItemProxy[] items = getItems(menu);
        return MenuItemTester.getMenuItemTester().findItem(items, text);
    }

    /**
     * @deprecated Do not use this.
     */
    MenuItemProxy findItem(MenuProxy menu, String text) {
        MenuItemTester menuItemTester = MenuItemTester.getMenuItemTester();
        MenuItemProxy[] items = getItems(menu);
        for (MenuItemProxy item : items) {
            if (text.equals(menuItemTester.getText(item)))
                return item;
        }

        for (MenuItemProxy item : items) {
            MenuProxy submenu = menuItemTester.getMenu(item);
            if (submenu != null) {
                MenuItemProxy subitem = findItem(submenu, text);
                if (subitem != null)
                    return subitem;
            }
        }

        return null;
    }

    /* Proxies */

    public RectangleProxy getBounds(final MenuProxy menu) {
        checkWidget(menu);
        return (RectangleProxy) syncExec(new Result() {
            public Object result() {
                return SWTWorkarounds.getBounds(menu);
            }
        });
    }

    /* Use the MenuProxy's parentItem to getText */
    public String getText(final MenuProxy menu) {
        return Displays.syncExec(menu.getDisplay(), new StringResult() {
            public String result() {
                String s = "";
                MenuItemProxy parentItem = menu.getParentItem();
                if (parentItem != null) {
                    s = parentItem.getText();
                }
                if (s == null || s.equals("")) {
                    if ((menu.getStyle() & SWTProxy.POP_UP) > 0) {
                        s = POPUP_ROOT_FLAG;
                    } else {
                        s = ROOT_FLAG;
                    }
                }
                return s;
            }
        });
    }

    /**
     * Proxy for {@link MenuProxy#getParentItem()}.{@link MenuItemProxy#toString()}.
     */
    public String toString(final MenuProxy m) {
        return Displays.syncExec(m.getDisplay(), new StringResult() {
            public String result() {
                String s = "";
                MenuItemProxy parentItem = m.getParentItem();
                if (parentItem != null) {
                    s = parentItem.toString();
                }
                return s;
            }
        });
    }

    /**
     * Proxy for {@link MenuProxy#getDefaultItem()}. <p/>
     *
     * @param menu the menu under test.
     * @return the default item.
     */
    public MenuItemProxy getDefaultItem(final MenuProxy menu) {
        MenuItemProxy result = (MenuItemProxy) Displays.syncExec(menu.getDisplay(), new Result() {
            public Object result() {
                return menu.getDefaultItem();
            }
        });
        return result;
    }

    /**
     * Proxy for {@link MenuProxy#getEnabled()}. <p/>
     *
     * @param menu the menu under test.
     * @return true if the menu is enabled.
     */
    public boolean getEnabled(final MenuProxy menu) {
        return Displays.syncExec(menu.getDisplay(), new BooleanResult() {
            public boolean result() {
                return menu.getEnabled();
            }
        });
    }

    /**
     * Proxy for {@link MenuProxy#getItem(int)}. <p/>
     *
     * @param menu  the menu under test.
     * @param index the index of the item to get.
     * @return the item at the index given.
     */
    public MenuItemProxy getItem(final MenuProxy menu, final int index) {
        return (MenuItemProxy) Displays.syncExec(menu.getDisplay(), new Result() {
            public Object result() {
                return menu.getItem(index);
            }
        });
    }

    /**
     * Proxy for {@link MenuProxy#getParentItem()}. <p/>
     *
     * @param menu the menu under test.
     * @return the parent item.
     */
    public MenuItemProxy getParentItem(final MenuProxy menu) {
        return (MenuItemProxy) Displays.syncExec(menu.getDisplay(), new Result() {
            public Object result() {
                return menu.getParentItem();
            }
        });
    }

    /**
     * Proxy for {@link MenuProxy#getItemCount()}. <p/>
     *
     * @param menu the menu under test.
     * @return the number of items under this menu.
     */
    public int getItemCount(final MenuProxy menu) {
        return Displays.syncExec(menu.getDisplay(), new IntResult() {
            public int result() {
                return menu.getItemCount();
            }
        });
    }

    /**
     * Proxy for {@link MenuProxy#getItems()}. <p/>
     *
     * @param menu the menu under test.
     * @return the children.
     */
    public MenuItemProxy[] getItems(final MenuProxy menu) {
        return (MenuItemProxy[]) Displays.syncExec(menu.getDisplay(), new Result() {
            public Object result() {
                return menu.getItems();
            }
        });
    }

    /**
     * Proxy for {@link MenuProxy#getParent()}. <p/>
     *
     * @param menu the menu under test.
     * @return the parent.
     */
    public DecorationsProxy getParent(final MenuProxy menu) {
        return (DecorationsProxy) Displays.syncExec(menu.getDisplay(), new Result() {
            public Object result() {
                return menu.getParent();
            }
        });
    }

    /**
     * Proxy for {@link MenuProxy#getParentMenu()}. <p/>
     *
     * @param menu the menu under test.
     * @return the parent menu.
     */
    public MenuProxy getParentMenu(final MenuProxy menu) {
        return (MenuProxy) Displays.syncExec(menu.getDisplay(), new Result() {
            public Object result() {
                return menu.getParentMenu();
            }
        });
    }

    /**
     * Gets the top-most (root) {@link MenuProxy} of a menu hierarchy.
     */
    public MenuProxy getRootMenu(MenuProxy menu) {
        MenuProxy parent = getParentMenu(menu);
        if (parent == null)
            return menu;
        return getRootMenu(parent);
    }

    /**
     * Proxy for {@link MenuProxy#getShell()}. <p/>
     *
     * @param menu the menu under test.
     * @return the shell of the menu.
     */
    public ShellProxy getShell(final MenuProxy menu) {
        return (ShellProxy) Displays.syncExec(menu.getDisplay(), new Result() {
            public Object result() {
                return menu.getShell();
            }
        });
    }

    /**
     * Proxy for {@link MenuProxy#getVisible()}. <p/>
     *
     * @param menu the menu under test.
     * @return the menu's visibility state.
     */
    public boolean getVisible(final MenuProxy menu) {
        return Displays.syncExec(menu.getDisplay(), new BooleanResult() {
            public boolean result() {
                return menu.getVisible();
            }
        });
    }

    /**
     * Proxy for {@link MenuProxy#indexOf(MenuItemProxy)}. <p/>
     *
     * @param menu     the menu under test.
     * @param menuItem the item to check.
     * @return the index of the item given.
     */
    public int indexOf(final MenuProxy menu, final MenuItemProxy menuItem) {
        return Displays.syncExec(menu.getDisplay(), new IntResult() {
            public int result() {
                return menu.indexOf(menuItem);
            }
        });
    }

    /**
     * Proxy for {@link MenuProxy#isEnabled()}. <p/>
     *
     * @param menu the menu under test.
     * @return true if the menu is enabled.
     */
    public boolean isEnabled(final MenuProxy menu) {
        return Displays.syncExec(menu.getDisplay(), new BooleanResult() {
            public boolean result() {
                return menu.isEnabled();
            }
        });
    }

    /**
     * Proxy for {@link MenuProxy#isVisible()}. <p/>
     *
     * @param menu the menu under test.
     * @return the menu's visibility state.
     */
    public boolean isVisible(final MenuProxy menu) {
        return syncExec(new BooleanResult() {
            public boolean result() {
                return menu.isVisible() && menu.getParent().isVisible();
            }
        });
    }

    /**
     * @see WidgetTester#isVisible(WidgetProxy)
     */
    public boolean isVisible(WidgetProxy widget) {
        if (widget instanceof MenuProxy)
            return isVisible((MenuProxy) widget);
        return super.isVisible(widget);
    }

    /* Miscellaneous */

    public boolean isPopUp(MenuProxy menu) {
        return (getStyle(menu) & SWTProxy.POP_UP) != 0;
    }
}