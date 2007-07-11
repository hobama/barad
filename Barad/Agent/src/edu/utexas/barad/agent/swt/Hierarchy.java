package edu.utexas.barad.agent.swt;

import edu.utexas.barad.agent.proxy.IProxyInvocationHandler;
import edu.utexas.barad.agent.swt.proxy.custom.CTabFolderProxy;
import edu.utexas.barad.agent.swt.proxy.custom.CTabItemProxy;
import edu.utexas.barad.agent.swt.proxy.widgets.*;
import edu.utexas.barad.common.swt.GUID;
import edu.utexas.barad.common.swt.WidgetCategory;
import edu.utexas.barad.common.swt.WidgetInfo;
import org.apache.log4j.Logger;

import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * University of Texas at Austin
 * Barad Project, Jul 5, 2007
 */
public class Hierarchy {
    private static final Logger logger = Logger.getLogger(Hierarchy.class);
    private static final String BARAD_WIDGET_GUID_DATA_KEY = "BARAD_WIDGET_GUID";

    private DisplayProxy display;

    public Hierarchy(DisplayProxy display) {
        this.display = display;
    }

    public WidgetInfo buildHierarchy(final Map<GUID, Object> proxyObjectCache) {
        WidgetInfo root = newObjectInfo(display);
        List<ShellProxy> shells = Displays.getShells(display);
        for (ShellProxy shell : shells) {
            WidgetInfo shellInfo = newObjectInfo(shell);
            proxyObjectCache.put(shellInfo.getGuid(), shell);
            buildHierarchyImpl(shell, shellInfo, proxyObjectCache);
            root.addChild(shellInfo);
        }
        return root;
    }

    private void buildHierarchyImpl(WidgetProxy parent, WidgetInfo parentInfo, final Map<GUID, Object> proxyObjectCache) {
        List<WidgetProxy> children = getChildren(parent);
        for (WidgetProxy child : children) {
            WidgetInfo childInfo = newObjectInfo(child);
            proxyObjectCache.put(childInfo.getGuid(), child);
            parentInfo.addChild(childInfo);
            buildHierarchyImpl(child, childInfo, proxyObjectCache);
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

                try {
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
                } catch (Exception e) {
                    logger.error("Exception getting children, widget=" + widget, e);
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

    private static WidgetInfo newObjectInfo(final DisplayProxy display) {
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

        return new WidgetInfo(className, null, guidHolder[0], WidgetCategory.DISPLAY);
    }

    private static WidgetInfo newObjectInfo(final WidgetProxy widget) {
        if (widget == null) {
            throw new NullPointerException("widget");
        }

        IProxyInvocationHandler invocationHandler = (IProxyInvocationHandler) Proxy.getInvocationHandler(widget);
        String widgetClassName = invocationHandler.getActualClass().getName();

        GUID guid = new GUID();

        // Get or create the widget GUID.
        DisplayProxy display = widget.getDisplay();
        final Object[] holder = new Object[2];
        display.syncExec(new Runnable() {
            public void run() {
                WidgetCategory category = null;
                String text = null;

                if (widget instanceof ShellProxy) {
                    category = WidgetCategory.SHELL;
                    text = ((ShellProxy) widget).getText();
                } else if (widget instanceof MenuItemProxy) {
                    category = WidgetCategory.MENU;
                    text = ((MenuItemProxy) widget).getText();
                } else if (widget instanceof MenuProxy) {
                    category = WidgetCategory.MENU;
                } else if (widget instanceof ToolBarProxy) {
                    category = WidgetCategory.MENU;
                } else if (widget instanceof ToolItemProxy) {
                    category = WidgetCategory.MENU;
                    text = ((ToolItemProxy) widget).getText();
                } else if (widget instanceof TreeProxy) {
                    category = WidgetCategory.TREE;
                } else if (widget instanceof TreeItemProxy) {
                    category = WidgetCategory.TREE;
                    text = ((TreeItemProxy) widget).getText();
                } else if (widget instanceof TreeColumnProxy) {
                    category = WidgetCategory.TREE;
                    text = ((TreeColumnProxy) widget).getText();
                } else if (widget instanceof TableProxy) {
                    category = WidgetCategory.TABLE;
                } else if (widget instanceof TableItemProxy) {
                    category = WidgetCategory.TABLE;
                    text = ((TableItemProxy) widget).getText();
                } else if (widget instanceof TableColumnProxy) {
                    category = WidgetCategory.TABLE;
                    text = ((TableColumnProxy) widget).getText();
                } else if (widget instanceof TabFolderProxy) {
                    category = WidgetCategory.TAB;
                } else if (widget instanceof TabItemProxy) {
                    category = WidgetCategory.TAB;
                    text = ((TabItemProxy) widget).getText();
                } else if (widget instanceof CTabFolderProxy) {
                    category = WidgetCategory.TAB;
                } else if (widget instanceof CTabItemProxy) {
                    category = WidgetCategory.TAB;
                    text = ((CTabItemProxy) widget).getText();
                } else if (widget instanceof CompositeProxy) {
                    if (widget instanceof TextProxy) {
                        category = WidgetCategory.TEXT;
                        text = ((TextProxy) widget).getText();
                    } else if (widget instanceof LabelProxy) {
                        category = WidgetCategory.LABEL;
                        text = ((LabelProxy) widget).getText();
                    } else if (widget instanceof ButtonProxy) {
                        category = WidgetCategory.BUTTON;
                        text = ((ButtonProxy) widget).getText();
                    }
                }
                if (category == null) {
                    category = WidgetCategory.OTHER;
                }

                holder[0] = category;
                holder[1] = text;
            }
        });

        WidgetCategory category = (WidgetCategory) holder[0];
        String text = (String) holder[1];

        return new WidgetInfo(widgetClassName, text, guid, category);
    }
}