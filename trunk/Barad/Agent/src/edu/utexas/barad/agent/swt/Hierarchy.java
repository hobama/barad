package edu.utexas.barad.agent.swt;

import edu.utexas.barad.agent.proxy.IProxyInvocationHandler;
import edu.utexas.barad.agent.swt.proxy.custom.CTabFolderProxy;
import edu.utexas.barad.agent.swt.proxy.custom.CTabItemProxy;
import edu.utexas.barad.agent.swt.proxy.widgets.*;
import edu.utexas.barad.common.swt.GUID;
import edu.utexas.barad.common.swt.ObjectInfo;

import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * University of Texas at Austin
 * Barad Project, Jul 5, 2007
 */
public class Hierarchy {
    private static final String BARAD_WIDGET_GUID_DATA_KEY = "BARAD_WIDGET_GUID";

    private DisplayProxy display;

    public Hierarchy(DisplayProxy display) {
        this.display = display;
    }

    public ObjectInfo buildHierarchy() {
        ObjectInfo root = newObjectInfo(display);
        List<ShellProxy> shells = Displays.getShells(display);
        for (ShellProxy shell : shells) {
            ObjectInfo shellInfo = newObjectInfo(shell);
            buildHierarchyImpl(shell, shellInfo);
            root.addChild(shellInfo);
        }
        return root;
    }

    private void buildHierarchyImpl(WidgetProxy parent, ObjectInfo parentInfo) {
        List<WidgetProxy> children = getChildren(parent);
        for (WidgetProxy child : children) {
            ObjectInfo childInfo = newObjectInfo(child);
            parentInfo.addChild(childInfo);
            buildHierarchyImpl(child, childInfo);
        }
    }

    private List<WidgetProxy> getChildren(final WidgetProxy widget) {
        checkWidget(widget);

        final List<WidgetProxy> children = new ArrayList<WidgetProxy>();

        display.syncExec(new Runnable() {
            public void run() {
                if (widget.isDisposed()) {
                    return;
                }

                if (widget instanceof ShellProxy) {
                    MenuProxy menu = ((ShellProxy) widget).getMenuBar();
                    if (menu != null) {
                        children.add(menu);
                    }
                }
                if (widget instanceof ControlProxy) {
                    MenuProxy menu = ((ControlProxy) widget).getMenu();
                    if (menu != null) {
                        children.add(menu);
                    }
                }
                if (widget instanceof ScrollableProxy) {
                    ScrollableProxy scrollable = (ScrollableProxy) widget;
                    ScrollBarProxy scrollBar = scrollable.getVerticalBar();
                    if (scrollBar != null) {
                        children.add(scrollBar);
                    }
                    scrollBar = scrollable.getHorizontalBar();
                    if (scrollBar != null) {
                        children.add(scrollBar);
                    }
                }
                if (widget instanceof TreeItemProxy) {
                    TreeItemProxy[] treeItems = ((TreeItemProxy) widget).getItems();
                    children.addAll(Arrays.asList(treeItems));
                }
                if (widget instanceof MenuProxy) {
                    MenuItemProxy[] menuItems = ((MenuProxy) widget).getItems();
                    children.addAll(Arrays.asList(menuItems));
                }
                if (widget instanceof MenuItemProxy) {
                    MenuProxy cascadeMenu = ((MenuItemProxy) widget).getMenu();
                    if (cascadeMenu != null) {
                        children.add(cascadeMenu);
                    }
                }
                if (widget instanceof CompositeProxy) {
                    ControlProxy[] compositeChildren = ((CompositeProxy) widget).getChildren();
                    children.addAll(Arrays.asList(compositeChildren));

                    if (widget instanceof ToolBarProxy) {
                        ToolItemProxy[] toolItems = ((ToolBarProxy) widget).getItems();
                        children.addAll(Arrays.asList(toolItems));
                    }
                    if (widget instanceof TableProxy) {
                        TableProxy table = (TableProxy) widget;
                        children.addAll(Arrays.asList(table.getItems()));
                        children.addAll(Arrays.asList(table.getColumns()));
                    }
                    if (widget instanceof TreeProxy) {
                        TreeProxy tree = (TreeProxy) widget;
                        children.addAll(Arrays.asList(tree.getColumns()));
                        children.addAll(Arrays.asList(tree.getItems()));
                    }
                    if (widget instanceof CoolBarProxy) {
                        CoolItemProxy[] coolItems = ((CoolBarProxy) widget).getItems();
                        children.addAll(Arrays.asList(coolItems));
                    }
                    if (widget instanceof TabFolderProxy) {
                        TabItemProxy[] tabItems = ((TabFolderProxy) widget).getItems();
                        children.addAll(Arrays.asList(tabItems));
                    }
                    if (widget instanceof CTabFolderProxy) {
                        CTabItemProxy[] tabItems = ((CTabFolderProxy) widget).getItems();
                        children.addAll(Arrays.asList(tabItems));
                    }
                }
            }
        });

        return children;
    }

    /**
     * Throws an {@link IllegalArgumentException} if the specified Widget's display isn't
     * the same as the receiver's. Used for argument checking.
     */
    protected void checkWidget(WidgetProxy widget) {
        if (widget == null) {
            throw new IllegalArgumentException("widget is null");
        }
        if (!widget.isDisposed()) {
            checkDisplay(widget.getDisplay());
        }
    }

    /**
     * Throws an {@link IllegalArgumentException} if the specified display isn't the same as the
     * receiver's. Used for argument checking.
     */
    protected void checkDisplay(DisplayProxy display) {
        if (!display.equals(this.display)) {
            throw new IllegalArgumentException("invalid display");
        }
    }

    private static ObjectInfo newObjectInfo(final DisplayProxy display) {
        if (display == null) {
            throw new NullPointerException("display");
        }

        IProxyInvocationHandler invocationHandler = (IProxyInvocationHandler) Proxy.getInvocationHandler(display);
        String className = invocationHandler.getActualClass().getName();

        // Get or create the widget GUID.
        final GUID[] guidHolder = new GUID[1];
        display.syncExec(new Runnable() {
            public void run() {
                GUID guid = (GUID) display.getData(BARAD_WIDGET_GUID_DATA_KEY);
                guidHolder[0] = guid;
            }
        });

        if (guidHolder[0] == null) {
            final GUID guid = new GUID();
            guidHolder[0] = guid;
            display.syncExec(new Runnable() {
                public void run() {
                    display.setData(BARAD_WIDGET_GUID_DATA_KEY, guid);
                }
            });
        }

        return new ObjectInfo(className, null, guidHolder[0]);
    }

    private static ObjectInfo newObjectInfo(final WidgetProxy widget) {
        if (widget == null) {
            throw new NullPointerException("widget");
        }

        IProxyInvocationHandler invocationHandler = (IProxyInvocationHandler) Proxy.getInvocationHandler(widget);
        String widgetClassName = invocationHandler.getActualClass().getName();

        // Get or create the widget GUID.
        DisplayProxy display = widget.getDisplay();
        final GUID[] guidHolder = new GUID[1];
        display.syncExec(new Runnable() {
            public void run() {
                GUID guid = (GUID) widget.getData(BARAD_WIDGET_GUID_DATA_KEY);
                guidHolder[0] = guid;
            }
        });

        if (guidHolder[0] == null) {
            final GUID guid = new GUID();
            guidHolder[0] = guid;
            display.syncExec(new Runnable() {
                public void run() {
                    widget.setData(BARAD_WIDGET_GUID_DATA_KEY, guid);
                }
            });
        }

        return new ObjectInfo(widgetClassName, null, guidHolder[0]);
    }
}