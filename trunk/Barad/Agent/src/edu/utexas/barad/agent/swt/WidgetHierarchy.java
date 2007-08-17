package edu.utexas.barad.agent.swt;

import edu.utexas.barad.agent.exceptions.AgentRuntimeException;
import edu.utexas.barad.agent.proxy.IProxyInvocationHandler;
import edu.utexas.barad.agent.swt.proxy.custom.CTabFolderProxy;
import edu.utexas.barad.agent.swt.proxy.custom.CTabItemProxy;
import edu.utexas.barad.agent.swt.proxy.dnd.DragSourceProxy;
import edu.utexas.barad.agent.swt.proxy.dnd.DropTargetProxy;
import edu.utexas.barad.agent.swt.proxy.widgets.*;
import edu.utexas.barad.agent.swt.widgets.MessageBoxHelper;
import edu.utexas.barad.common.Visitable;
import edu.utexas.barad.common.Visitor;
import edu.utexas.barad.common.ReflectionUtils;
import edu.utexas.barad.common.swt.GUID;
import edu.utexas.barad.common.swt.WidgetCategory;
import edu.utexas.barad.common.swt.WidgetID;
import edu.utexas.barad.common.swt.WidgetInfo;
import org.apache.log4j.Logger;

import java.lang.reflect.Proxy;
import java.util.*;

/**
 * University of Texas at Austin
 * Barad Project, Jul 5, 2007
 */
public class WidgetHierarchy implements Visitable {
    private static final Logger logger = Logger.getLogger(WidgetHierarchy.class);
    private static final String BARAD_WIDGET_GUID_DATA_KEY = "BARAD_WIDGET_GUID";

    private DisplayProxy display;
    private Map<GUID, Object> guidProxyCache = new LinkedHashMap<GUID, Object>();
    private Map<WidgetID, Object> widgetIDProxyCache = new LinkedHashMap<WidgetID, Object>();
    private MessageBoxHelper messageBoxHelper;
    private Map<WidgetID, Map<String, String>> propertyValueCache = new LinkedHashMap<WidgetID, Map<String, String>>();
    private WidgetInfo cachedHierarchy;

    public WidgetInfo getWidgetHierarchy() {
        return getWidgetHierarchy(false);
    }

    public WidgetInfo getWidgetHierarchy(boolean rebuild) {
        if (display == null) {
            display = DisplayProxy.Factory.getDefault();
        }

        // Do we have a cached version?  If so, just return it.
        if (!rebuild && cachedHierarchy != null) {
            return cachedHierarchy;
        }

        // Clear the caches.
        guidProxyCache.clear();
        widgetIDProxyCache.clear();
        propertyValueCache.clear();
        messageBoxHelper = null;
        cachedHierarchy = null;

        // Build the hierarchy.
        WidgetInfo displayInfo = buildHierarchy();
        guidProxyCache.put(displayInfo.getGuid(), display);
        widgetIDProxyCache.put(displayInfo.getWidgetID(), display);
        cachedHierarchy = displayInfo;
        return cachedHierarchy;
    }

    public DisplayProxy getDisplay() {
        return display;
    }

    public Object getWidgetProxy(GUID guid) {
        if (guid == null) {
            throw new NullPointerException("guid");
        }
        getWidgetHierarchy(false);

        Object proxy = guidProxyCache.get(guid);
        if (proxy == null) {
            return null;
        }

        if (!Proxy.isProxyClass(proxy.getClass())) {
            throw new IllegalStateException("Object for GUID isn't a proxy, guid=" + guid);
        }
        return proxy;
    }

    public Object getWidgetProxy(WidgetID widgetID) {
        if (widgetID == null) {
            throw new NullPointerException("widgetID");
        }
        getWidgetHierarchy(false);

        Object proxy = widgetIDProxyCache.get(widgetID);
        if (proxy == null) {
            return null;
        }

        if (!Proxy.isProxyClass(proxy.getClass())) {
            throw new IllegalStateException("Object for WidgetID isn't a proxy, widgetID=" + widgetID);
        }
        return proxy;
    }

    public MessageBoxHelper getMessageBoxHelper() {
        return messageBoxHelper;
    }

    public Map<String, String> getWidgetPropertyValues(WidgetID widgetID) {
        if (widgetID == null) {
            throw new NullPointerException("widgetID");
        }
        if (cachedHierarchy == null) {
            getWidgetHierarchy(false);
        }
        return propertyValueCache.get(widgetID);
    }

    public WidgetProxy getParent(final WidgetProxy widget) {
        checkWidget(widget);
        return (WidgetProxy) Displays.syncExec(display, new Displays.Result() {
            public Object result() {
                if (widget instanceof ControlProxy)
                    return ((ControlProxy) widget).getParent();
                if (widget instanceof CaretProxy)
                    return ((CaretProxy) widget).getParent();
                if (widget instanceof MenuProxy)
                    return ((MenuProxy) widget).getParent();
                if (widget instanceof ScrollBarProxy)
                    return ((ScrollBarProxy) widget).getParent();
                if (widget instanceof CoolItemProxy)
                    return ((CoolItemProxy) widget).getParent();
                if (widget instanceof MenuItemProxy)
                    return ((MenuItemProxy) widget).getParent();
                if (widget instanceof TabItemProxy)
                    return ((TabItemProxy) widget).getParent();
                if (widget instanceof TableColumnProxy)
                    return ((TableColumnProxy) widget).getParent();
                if (widget instanceof TableItemProxy)
                    return ((TableItemProxy) widget).getParent();
                if (widget instanceof ToolItemProxy)
                    return ((ToolItemProxy) widget).getParent();
                if (widget instanceof TreeColumnProxy)
                    return ((TreeColumnProxy) widget).getParent();
                if (widget instanceof TreeItemProxy)
                    return ((TreeItemProxy) widget).getParent();
                if (widget instanceof DragSourceProxy)
                    return ((DragSourceProxy) widget).getControl().getParent();
                if (widget instanceof DropTargetProxy)
                    return ((DropTargetProxy) widget).getControl().getParent();
                return null;
            }
        });
    }

    private WidgetInfo buildHierarchy() {
        WidgetInfo displayInfo = newWidgetInfo(display);
        displayInfo.setWidgetID(newWidgetID(display, null, displayInfo.getText(), -1));

        List<ShellProxy> shells = Displays.getShells(display);
        for (int i = 0; i < shells.size(); ++i) {
            ShellProxy shell = shells.get(i);
            WidgetInfo shellInfo = newWidgetInfo(shell);
            WidgetID widgetID = newWidgetID(shell, displayInfo.getWidgetID(), shellInfo.getText(), i);
            shellInfo.setWidgetID(widgetID);
            guidProxyCache.put(shellInfo.getGuid(), shell);
            widgetIDProxyCache.put(widgetID, shell);
            buildHierarchyImpl(shell, shellInfo);
            displayInfo.addChild(shellInfo);

            MessageBoxHelper messageBoxHelper = MessageBoxHelper.newInstance(shell, shellInfo.getWidgetID());
            if (messageBoxHelper != null) {
                if (this.messageBoxHelper != null) {
                    throw new AgentRuntimeException("MessageBoxHelper has already been set.");
                }
                this.messageBoxHelper = messageBoxHelper;
                WidgetInfo messageBoxInfo = newWidgetInfo(messageBoxHelper);
                WidgetID messageBoxID = newWidgetID(messageBoxHelper, shellInfo.getWidgetID(), messageBoxHelper.getStaticText(), -1);
                messageBoxInfo.setWidgetID(messageBoxID);
                shellInfo.addChild(messageBoxInfo);
            }
        }
        return displayInfo;
    }

    private void buildHierarchyImpl(WidgetProxy parent, WidgetInfo parentInfo) {
        List<WidgetProxy> children = getChildren(parent);
        for (int i = 0; i < children.size(); ++i) {
            WidgetProxy child = children.get(i);
            WidgetInfo childInfo = newWidgetInfo(child);
            WidgetID widgetID = newWidgetID(child, parentInfo.getWidgetID(), childInfo.getText(), i);
            childInfo.setWidgetID(widgetID);
            guidProxyCache.put(childInfo.getGuid(), child);
            widgetIDProxyCache.put(widgetID, child);
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

                try {
                    if (widget instanceof ShellProxy) {
                        ShellProxy shell = (ShellProxy) widget;
                        MenuProxy menu = shell.getMenuBar();
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

    private void checkWidget(WidgetProxy widget) {
        if (widget == null) {
            throw new NullPointerException("widget");
        }
        if (!widget.isDisposed()) {
            checkDisplay(widget.getDisplay());
        }
    }

    private void checkDisplay(DisplayProxy display) {
        if (!display.equals(this.display)) {
            throw new IllegalArgumentException("Invalid display.");
        }
    }

    private static WidgetInfo newWidgetInfo(final DisplayProxy display) {
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
                if (guid == null) {
                    guid = new GUID();
                    display.setData(BARAD_WIDGET_GUID_DATA_KEY, guid);
                }
                guidHolder[0] = guid;
            }
        });

        return new WidgetInfo(className, null, guidHolder[0], WidgetCategory.DISPLAY);
    }

    private static WidgetInfo newWidgetInfo(final WidgetProxy widget) {
        if (widget == null) {
            throw new NullPointerException("widget");
        }

        IProxyInvocationHandler invocationHandler = (IProxyInvocationHandler) Proxy.getInvocationHandler(widget);
        String widgetClassName = invocationHandler.getActualClass().getName();

        // Get or create the widget GUID.
        DisplayProxy display = widget.getDisplay();
        final Object[] holder = new Object[3];
        display.syncExec(new Runnable() {
            public void run() {
                GUID guid = (GUID) widget.getData(BARAD_WIDGET_GUID_DATA_KEY);
                if (guid == null) {
                    guid = new GUID();
                    widget.setData(BARAD_WIDGET_GUID_DATA_KEY, guid);
                }
                holder[2] = guid;

                WidgetCategory category = null;
                String text = null;
                try {
                    text = (String) ReflectionUtils.invokeMethod(widget, "getText", null, null);
                } catch (Exception ignore) {
                    // Ignore exception.
                }

                if (widget instanceof ShellProxy) {
                    category = WidgetCategory.SHELL;
                } else if (widget instanceof MenuItemProxy) {
                    category = WidgetCategory.MENU;
                } else if (widget instanceof MenuProxy) {
                    category = WidgetCategory.MENU;
                } else if (widget instanceof ToolBarProxy) {
                    category = WidgetCategory.MENU;
                } else if (widget instanceof ToolItemProxy) {
                    category = WidgetCategory.MENU;
                } else if (widget instanceof TreeProxy) {
                    category = WidgetCategory.TREE;
                } else if (widget instanceof TreeItemProxy) {
                    category = WidgetCategory.TREE;
                } else if (widget instanceof TreeColumnProxy) {
                    category = WidgetCategory.TREE;
                } else if (widget instanceof TableProxy) {
                    category = WidgetCategory.TABLE;
                } else if (widget instanceof TableItemProxy) {
                    category = WidgetCategory.TABLE;
                } else if (widget instanceof TableColumnProxy) {
                    category = WidgetCategory.TABLE;
                } else if (widget instanceof TabFolderProxy) {
                    category = WidgetCategory.TAB;
                } else if (widget instanceof TabItemProxy) {
                    category = WidgetCategory.TAB;
                } else if (widget instanceof CTabFolderProxy) {
                    category = WidgetCategory.TAB;
                } else if (widget instanceof CTabItemProxy) {
                    category = WidgetCategory.TAB;
                } else if (widget instanceof TextProxy) {
                    category = WidgetCategory.TEXT;
                } else if (widget instanceof LabelProxy) {
                    category = WidgetCategory.LABEL;
                } else if (widget instanceof ButtonProxy) {
                    category = WidgetCategory.BUTTON;
                } else if (widget instanceof ComboProxy) {
                    category = WidgetCategory.TEXT;
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
        GUID guid = (GUID) holder[2];

        return new WidgetInfo(widgetClassName, text, guid, category);
    }

    private static WidgetInfo newWidgetInfo(final MessageBoxHelper helper) {
        if (helper == null) {
            throw new NullPointerException("helper");
        }
        String className = helper.getClass().getName();
        String text = helper.getStaticText() + ", buttons=[";
        if (helper.hasOKButton()) {
            text += "OK";
        }
        if (helper.hasCancelButton()) {
            text += ", Cancel";
        }
        if (helper.hasYesButton()) {
            text += "Yes";
        }
        if (helper.hasNoButton()) {
            text += ", No";
        }
        if (helper.hasAbortButton()) {
            text += "Abort";
        }
        if (helper.hasRetryButton()) {
            text += ", Retry";
        }
        if (helper.hasIgnoreButton()) {
            text += ", Ignore";
        }
        text += "]";
        GUID guid = new GUID();
        WidgetCategory category = WidgetCategory.WINDOW;
        return new WidgetInfo(className, text, guid, category);
    }

    private static WidgetID newWidgetID(final Object object, WidgetID parentID, String text, int childIndex) {
        if (object == null) {
            throw new NullPointerException("object");
        }

        String className;
        if (Proxy.isProxyClass(object.getClass())) {
            IProxyInvocationHandler invocationHandler = (IProxyInvocationHandler) Proxy.getInvocationHandler(object);
            className = invocationHandler.getActualClass().getName();
        } else {
            className = object.getClass().getName();
        }

        WidgetID widgetID = new WidgetID();
        widgetID.setClassName(className);
        widgetID.setParentID(parentID);
        widgetID.setChildIndex(childIndex);
        widgetID.setText(text);
        return widgetID;
    }

    public void accept(Visitor visitor) {
        if (visitor == null) {
            return;
        }
        WidgetInfo hierarchy = getWidgetHierarchy(false);
        accept(hierarchy, visitor);
    }

    private void accept(WidgetInfo widgetInfo, Visitor visitor) {
        visitor.visit(widgetInfo);
        for (int i = 0; i < widgetInfo.getChildCount(); ++i) {
            WidgetInfo childInfo = widgetInfo.getChildAt(i);
            accept(childInfo, visitor);
        }
    }

    public void populateValues() {
        propertyValueCache.clear();

        accept(new Visitor() {
            public void visit(Object object) {
                WidgetInfo widgetInfo = (WidgetInfo) object;
                if (!isMessageBoxHelper(widgetInfo)) {
                    Object widget = guidProxyCache.get(widgetInfo.getGuid());
                    DisplayProxy display;
                    if (widget instanceof DisplayProxy) {
                        display = (DisplayProxy) widget;
                    } else {
                        WidgetProxy widgetProxy = (WidgetProxy) widget;
                        display = widgetProxy.getDisplay();
                    }
                    IProxyInvocationHandler handler = (IProxyInvocationHandler) Proxy.getInvocationHandler(widget);
                    Object actualInstance = handler.getActualInstance();
                    Map<String, String> values = WidgetValueBuilder.buildPropertyValues(actualInstance, display, CompareHierarchies.propertyNamesToCompare);
                    propertyValueCache.put(widgetInfo.getWidgetID(), values);
                } else {
                    MessageBoxHelper messageBoxHelper = getMessageBoxHelper();
                    Map<String, String> values = WidgetValueBuilder.buildPropertyValues(messageBoxHelper, CompareHierarchies.propertyNamesToCompare);
                    propertyValueCache.put(widgetInfo.getWidgetID(), values);
                }
            }
        });
    }

    public static boolean isMessageBoxHelper(WidgetInfo widgetInfo) {
        return widgetInfo.getClassName().equals(MessageBoxHelper.class.getName());
    }

    public ShellProxy getShell(WidgetInfo widgetInfo) {
        if (widgetInfo == null) {
            return null;
        }
        getWidgetHierarchy();

        WidgetInfo temp = widgetInfo;
        do {
            Object object = guidProxyCache.get(temp.getGuid());
            if (object instanceof ShellProxy) {
                return (ShellProxy) object;
            }
            temp = temp.getParent();
        } while (temp != null);
        return null;
    }

    @Override
    public int hashCode() {
        int hashCode = 0;
        hashCode += display != null ? display.hashCode() : 0;
        hashCode += widgetIDProxyCache.hashCode();
        hashCode += messageBoxHelper != null ? messageBoxHelper.hashCode() : 0;
        hashCode += propertyValueCache.hashCode();
        return hashCode;
    }

    @Override
    public boolean equals(Object object) {
        if (object instanceof WidgetHierarchy) {
            WidgetHierarchy another = (WidgetHierarchy) object;
            if (another.display == null && this.display != null) {
                return false;
            }
            if (another.display != null && this.display == null) {
                return false;
            }
            if (another.display != null && this.display != null && !another.display.equals(this.display)) {
                return false;
            }

            if (!another.widgetIDProxyCache.equals(this.widgetIDProxyCache)) {
                return false;
            }

            if (another.messageBoxHelper != null && this.messageBoxHelper == null) {
                return false;
            }
            if (another.messageBoxHelper == null && this.messageBoxHelper != null) {
                return false;
            }
            if (another.messageBoxHelper != null && this.messageBoxHelper != null && !another.messageBoxHelper.equals(this.messageBoxHelper)) {
                return false;
            }

            if (!another.propertyValueCache.equals(this.propertyValueCache)) {
                return false;
            }
            return true;
        }

        return false;
    }

    public String toString() {
        return "WidgetHierarchy{" +
                "display=" + display +
                ", guidProxyCache=" + guidProxyCache +
                ", widgetIDProxyCache=" + widgetIDProxyCache +
                ", messageBoxHelper=" + messageBoxHelper +
                ", propertyValueCache=" + propertyValueCache +
                ", cachedHierarchy=" + cachedHierarchy +
                '}';
    }
}