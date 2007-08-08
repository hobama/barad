package edu.utexas.barad.agent.swt.tester;

import edu.utexas.barad.agent.exceptions.ActionFailedException;
import edu.utexas.barad.agent.script.Condition;
import edu.utexas.barad.agent.script.WidgetReference;
import edu.utexas.barad.agent.swt.Displays.BooleanResult;
import edu.utexas.barad.agent.swt.Displays.IntResult;
import edu.utexas.barad.agent.swt.Displays.Result;
import edu.utexas.barad.agent.swt.Displays.StringResult;
import edu.utexas.barad.agent.swt.Robot;
import edu.utexas.barad.agent.swt.WidgetHierarchy;
import edu.utexas.barad.agent.swt.WidgetLocator;
import edu.utexas.barad.agent.swt.proxy.SWTProxy;
import edu.utexas.barad.agent.swt.proxy.events.DisposeListenerProxy;
import edu.utexas.barad.agent.swt.proxy.graphics.PointProxy;
import edu.utexas.barad.agent.swt.proxy.graphics.RectangleProxy;
import edu.utexas.barad.agent.swt.proxy.widgets.*;

import javax.accessibility.AccessibleContext;
import javax.accessibility.AccessibleIcon;
import java.lang.reflect.Method;
import java.util.*;

/**
 * University of Texas at Austin
 * Barad Project, Jul 25, 2007
 */
public class WidgetTester extends TesterRobot {
    /**
     * Factory interface.
     */
    public interface Factory {

        boolean addPackage(String packageName);

        boolean removePackage(String packageName);

        WidgetTester getTester(Class widgetClass);

        WidgetTester getTester(WidgetProxy widget);

        void setTester(Class widgetClass, WidgetTester tester);
    }

    /**
     * Our current Factory.
     */
    private static Factory FACTORY;

    /**
     * Mutex for FACTORY manipulation.
     *
     * @see #getFactory()
     */
    private static final Object FACTORY_LOCK = new Object();

    /**
     * Gets the current {@link Factory}.
     *
     * @return the current {@link Factory}
     */
    public static Factory getFactory() {
        synchronized (FACTORY_LOCK) {
            if (FACTORY == null)
                FACTORY = WidgetTesterFactory.getDefault();
            return FACTORY;
        }
    }

    /**
     * Establish the given WidgetTester as the one to use for the given class. This may be used to
     * override the default tester for a given core class. Note that this will only work with
     * widgets loaded by the framework class loader, not those loaded by the class loader for the
     * code under test.
     */
    public static void setTester(Class widgetClass, WidgetTester tester) {
        getFactory().setTester(widgetClass, tester);
    }

    /**
     * Factory method (generic). Gets a tester for the specified {@link WidgetProxy}.
     */
    public static WidgetTester getTester(WidgetProxy widget) {
        return getFactory().getTester(widget);
    }

    /**
     * Factory method (generic). Gets a tester for the specified {@link WidgetProxy} class.
     */
    public static WidgetTester getTester(Class widgetClass) {
        return getFactory().getTester(widgetClass);
    }

    /**
     * Factory method. Gets a {@link WidgetTester}.
     */
    public static WidgetTester getWidgetTester() {
        return getTester(WidgetProxy.class);
    }

    /**
     * Constructs a new {@link WidgetTester} on a specified {@link Robot}.
     * <p/>
     * <b>Note:</b> You should probably not use this constructor in your tests. Instead, use the
     * factory methods {@link #getTester(Class)} et al. because they do caching for you.
     *
     * @param swtRobot the {@link TesterRobot} to be associated with the new {@link WidgetTester}
     */
    public WidgetTester(Robot swtRobot) {
        super(swtRobot);
    }

    private WidgetHierarchy hierarchy;

    /**
     * Convenience method to get a {@link WidgetHierarchy} (for our {@link DisplayProxy}). In general,
     * {@link WidgetTester}s shouldn't need to use {@link WidgetHierarchy}s, but a few do.
     */
    protected synchronized WidgetHierarchy getHierarchy() {
        if (hierarchy == null)
            hierarchy = new WidgetHierarchy();
        return hierarchy;
    }

    /**
     * Return the {@link WidgetProxy} class that corresponds to this {@link WidgetTester} class.
     */
    public Class getTestedClass(Class widgetClass) {
        while (getTester(widgetClass.getSuperclass()) == this) {
            widgetClass = widgetClass.getSuperclass();
        }
        return widgetClass;
    }

    /* Actions */

    /* Actions for invoking pop-up menus. */

    /**
     * Clicks a menu item in a {@link WidgetProxy}'s context (pop-up) menu.
     */
    public void actionClickMenuItem(WidgetProxy widget, ItemPath menuPath) {
        checkWidget(widget);
        clickMenuItem(widget, menuPath);
    }

    /**
     * Clicks a menu item in a {@link WidgetProxy}'s context (pop-up) menu.
     */
    public void actionClickMenuItem(WidgetProxy widget, String menuPath) {
        actionClickMenuItem(widget, new ItemPath(menuPath));
    }

    /**
     * Clicks a menu item in a {@link WidgetProxy}'s context (pop-up) menu.
     */
    public void actionClickMenuItem(WidgetProxy widget, String menuPath, String delimiter) {
        actionClickMenuItem(widget, new ItemPath(menuPath, delimiter));
    }

    void clickMenuItem(WidgetProxy widget, ItemPath menuPath) {

        // Get the pop-up MenuProxy.
        // We get a specialized tester for the actual class of the widget in order to
        // invoke the appropriate getMenu(WidgetProxy) implementation.
        WidgetTester tester = getTester(widget);
        MenuProxy menu = tester.getMenu(widget);
        if (menu == null)
            throw new ActionFailedException("no menu");

        // Bring up the pop-up MenuProxy and wait for it to become visible.
        MenuTester menuTester = MenuTester.getMenuTester();
        if (!menuTester.isVisible(menu)) {
            click(widget, BUTTON_POPUP);
            menuTester.waitVisible(menu);
        }

        // Find and click the MenuItemProxy speified by the menuPath.
        menuTester.clickItem(menu, menuPath);

    }

    /**
     * @
     * @deprecated Use {@link MenuTester#actionClickItem(MenuProxy,String)} or similar.
     */
    public void actionSelectMenuItemByText(MenuProxy menu, String text) {
        checkWidget(menu);
        selectMenuItemByText(menu, text);
    }

    /**
     * Select the given menu item.
     *
     * @deprecated Use {@link MenuTester#actionClickMenuItem(WidgetProxy,ItemPath)} or similar.
     */
    public void actionSelectMenuItem(MenuItemProxy item) {
        checkWidget(item);

        MenuItemTester menuItemTester = MenuItemTester.getMenuItemTester();
        MenuTester menuTester = MenuTester.getMenuTester();

        MenuProxy menu = menuItemTester.getRootMenu(item);
        ItemPath path = menuItemTester.getPath(item);
        if ((menuTester.getStyle(menu) & SWTProxy.POP_UP) == SWTProxy.POP_UP) {
            ControlProxy control = menuTester.getParent(menu);
            actionClickMenuItem(control, path);
        } else {
            menuTester.clickItem(menu, path);
        }
    }

    /**
     * Open the item's popup menu at the given coordinates of its parent control, and select the
     * given item.
     *
     * @deprecated Use {@link #actionClickMenuItem(WidgetProxy,ItemPath)} or similar. @
     */
    public void actionSelectPopupMenuItem(MenuItemProxy item, int x, int y) {
        checkWidget(item);

        // Get the root MenuProxy.
        MenuItemTester itemTester = MenuItemTester.getMenuItemTester();
        MenuProxy menu = itemTester.getRootMenu(item);
        if (menu == null)
            throw new ActionFailedException("no menu");

        // Bring up the pop-up MenuProxy and wait for it to become visible.
        MenuTester menuTester = MenuTester.getMenuTester();
        ShellProxy parent = (ShellProxy) menuTester.getParent(menu);
        if (!menuTester.isVisible(menu)) {
            click(parent, x, y, BUTTON_POPUP);
            menuTester.waitVisible(menu);
        }

        // Find and click the MenuItemProxy speified by the menuPath.
        menuTester.clickItem(menu, itemTester.getPath(item));
    }

    /**
     * Subclasses that have a pop-up menu should redefine this method. This default implementation
     * simply returns <code>null</code>.
     *
     * @see #clickMenuItem(WidgetProxy,ItemPath)
     * @see ControlTester#getMenu(WidgetProxy)
//     * @see TabItemTester#getMenu(WidgetProxy)
//     * @see TableItemTester#getMenu(WidgetProxy)
//     * @see ToolItemTester#getMenu(WidgetProxy)
//     * @see TreeColumnTester#getMenu(WidgetProxy)
     */
    protected MenuProxy getMenu(WidgetProxy widget) {
        return null;
    }

    /* Proxies */

    /**
     * @return <code>true</code> if the {@link WidgetProxy} is visible (showing), <code>false</code>
     *         otherwise
     * @throws UnsupportedOperationException -
     *                                       Subclasses that can be visible must redefine this method.
     */
    public boolean isVisible(WidgetProxy widget) {
        throw new UnsupportedOperationException();
    }

    public void waitVisible(final WidgetProxy widget) {
        checkWidget(widget);
        wait(new Condition() {
            public boolean test() {
                return isVisible(widget);
            }
        });
    }

    public void waitVisible(final WidgetProxy widget, final long timeout) {
        checkWidget(widget);
        checkTime(timeout);
        wait(new Condition() {
            public boolean test() {
                return isVisible(widget);
            }
        }, timeout);
    }

    /**
     * Proxy for {@link WidgetProxy#getData()}.
     */
    public Object getData(final WidgetProxy widget) {
        checkWidget(widget);
        return syncExec(new Result() {
            public Object result() {
                return widget.getData();
            }
        });
    }

    /**
     * Proxy for {@link WidgetProxy#getData(String)}.
     */
    public Object getData(final WidgetProxy widget, final String key) {
        checkWidget(widget);
        return syncExec(new Result() {
            public Object result() {
                return widget.getData(key);
            }
        });
    }

    /**
     * Proxy for {@link WidgetProxy#getDisplay()}.
     */
    public DisplayProxy getDisplay(final WidgetProxy widget) {
        checkWidget(widget);
        return widget.getDisplay();
    }

    /**
     * Proxy for {@link WidgetProxy#getStyle()}. <p/>
     *
     * @param widget the widget to obtain the style for.
     * @return the style.
     */
    public int getStyle(final WidgetProxy widget) {
        checkWidget(widget);
        return syncExec(new IntResult() {
            public int result() {
                return widget.getStyle();
            }
        });
    }

    /**
     * Proxy for {@link WidgetProxy#isDisposed()}. This method isn't really needed because
     * WidgetProxy.isDisposed() can be called on any Thread. It's here only for completeness.
     *
     * @param widget the WidgetProxy
     * @return true if the WidgetProxy has been disposed
     * @see WidgetProxy#isDisposed()
     */
    public boolean isDisposed(WidgetProxy widget) {
        checkWidget(widget);
        return widget.isDisposed();
    }

    /**
     * Wait for the specified WidgetProxy to be disposed. Times out after the default timeout.
     *
     * @param widget the WidgetProxy
     * @see TesterRobot#wait(Condition)
     */
    public void waitForDispose(final WidgetProxy widget) {
        checkWidget(widget);
        wait(new Condition() {
            public boolean test() {
                return widget.isDisposed();
            }
        });
    }

    /**
     * Wait for the specified WidgetProxy to be disposed. Times out after the specified timeout
     * (milliseconds).
     *
     * @param widget  the WidgetProxy
     * @param timeout the timeout in milliseconds
     * @see TesterRobot#wait(Condition,long)
     */
    public void waitForDispose(final WidgetProxy widget, long timeout) {
        checkWidget(widget);
        wait(new Condition() {
            public boolean test() {
                return widget.isDisposed();
            }
        }, timeout);
    }

    /**
     * Proxy for {@link WidgetProxy#isListening(int)}.
     *
     * @param widget the widget to ask.
     * @return true if the widget is listening for the given event.
     */
    public boolean isListening(final WidgetProxy widget, final int eventType) {
        checkWidget(widget);
        return syncExec(new BooleanResult() {
            public boolean result() {
                return widget.isListening(eventType);
            }
        });
    }

    /**
     * Proxy for {@link WidgetProxy#toString()}.
     *
     * @param widget the widget to obtain the toString from.
     * @return a {@link String} representation of the receiver
     */
    public String toString(final WidgetProxy widget) {
        checkWidget(widget);
        return syncExec(new StringResult() {
            public String result() {
                return widget.toString();
            }
        });
    }

    /**
     * Proxy for {@link WidgetProxy#addDisposeListener(DisposeListenerProxy)}. <p/>
     *
     * @param widget          the widget to add the listener to.
     * @param disposeListener the listener to add.
     */
    public void addDisposeListener(final WidgetProxy widget, final DisposeListenerProxy disposeListener) {
        checkWidget(widget);
        syncExec(new Runnable() {
            public void run() {
                widget.addDisposeListener(disposeListener);
            }
        });
    }

    /**
     * Proxy for {@link WidgetProxy#addListener(int,ListenerProxy)}. <p/>
     *
     * @param widget    the WidgetProxy to add the listener to.
     * @param eventType the eventType for which a listener to add.
     * @param listener  the listener to add.
     */
    public void addListener(final WidgetProxy widget, final int eventType, final ListenerProxy listener) {
        checkWidget(widget);
        syncExec(new Runnable() {
            public void run() {
                widget.addListener(eventType, listener);
            }
        });
    }

    /**
     * Proxy for {@link WidgetProxy#notifyListeners(int,EventProxy)}. <p/>
     *
     * @param widget    the widgets which listeners should be notified.
     * @param eventType the eventType to notify.
     * @param event     the event to issue.
     */
    public void notifyListeners(final WidgetProxy widget, final int eventType, final EventProxy event) {
        checkWidget(widget);
        syncExec(new Runnable() {
            public void run() {
                widget.notifyListeners(eventType, event);
            }
        });
    }

    /**
     * Proxy for {@link WidgetProxy#removeDisposeListener(DisposeListenerProxy)}. <p/>
     *
     * @param widget          the WidgetProxy from which to remove the DisposeListener.
     * @param disposeListener the listener to remove.
     */
    public void removeDisposeListener(final WidgetProxy widget, final DisposeListenerProxy disposeListener) {
        checkWidget(widget);
        syncExec(new Runnable() {
            public void run() {
                widget.removeDisposeListener(disposeListener);
            }
        });
    }

    /**
     * Proxy for {@link WidgetProxy#removeListener(int,ListenerProxy)}. <p/>
     *
     * @param widget    the WidgetProxy from which the listener to remove.
     * @param eventType the eventType being removed.
     * @param listener  the listener to remove.
     */
    public void removeListener(final WidgetProxy widget, final int eventType, final ListenerProxy listener) {
        syncExec(new Runnable() {
            public void run() {
                widget.removeListener(eventType, listener);
            }
        });
    }

    /**
     * Proxy for {@link WidgetProxy#setData(java.lang.Object)}. <p/>
     *
     * @param widget the WidgetProxy whichs data should be set.
     * @param data   the data to set.
     */
    public void setData(final WidgetProxy widget, final Object data) {
        checkWidget(widget);
        syncExec(new Runnable() {
            public void run() {
                widget.setData(data);
            }
        });
    }

    /**
     * Proxy for {@link WidgetProxy#setData(java.lang.String,java.lang.Object)}. <p/>
     *
     * @param widget the widget whichs data to set.
     * @param key    the key under shich the data should be stored.
     * @param data   the data to store.
     */
    public void setData(final WidgetProxy widget, final String key, final Object data) {
        checkWidget(widget);
        syncExec(new Runnable() {
            public void run() {
                widget.setData(key, data);
            }
        });
    }

    public interface Textable {

        String getText(WidgetProxy widget);

        boolean isTextEditable(WidgetProxy widget);
    }

    /**
     * TODO Is this the right place for this? Should there instead be a static
     * getTextableTester(WidgetProxy) factory method?
     */
    public String getWidgetText(final WidgetProxy widget) {
        WidgetTester tester = getTester(widget);
        if (tester instanceof Textable)
            return ((Textable) tester).getText(widget);
        return null;
    }

    /**
     * Get the location of the widget in global screen coordinates
     */
    public PointProxy getGlobalLocation(WidgetProxy widget) {
        return getGlobalLocation(widget, true);
    }

    /**
     * Get the location of the widget in global screen coordinates, optionally ignoring the
     * 'trimmings'.
     */
    public PointProxy getGlobalLocation(final WidgetProxy widget, final boolean ignoreBorder) {
        checkWidget(widget);
        return (PointProxy) syncExec(new Result() {
            public Object result() {
                return WidgetLocator.getLocation(widget, ignoreBorder);
            }
        });
    }

    /**
     * Get the bounding rectangle for the given WidgetProxy in global screen coordinates.
     */
    public RectangleProxy getGlobalBounds(WidgetProxy widget) {
        return getGlobalBounds(widget, true);
    }

    /**
     * Get the bounding rectangle for the given WidgetProxy in global screen coordinates, optionally
     * ignoring the 'trimmings'.
     */
    public RectangleProxy getGlobalBounds(final WidgetProxy widget, final boolean ignoreBorder) {
        checkWidget(widget);
        return (RectangleProxy) syncExec(new Result() {
            public Object result() {
                return WidgetLocator.getBounds(widget, ignoreBorder);
            }
        });
    }

    /**
     * Derive a tag from the given accessible context if possible, or return null.
     */
    protected String deriveAccessibleTag(AccessibleContext context) {
        String tag = deriveRawAccessibleTag(context);
        if (tag != null) {
            tag = tag.substring(tag.lastIndexOf("/") + 1);
            tag = tag.substring(tag.lastIndexOf("\\") + 1);
            return tag;
        }
        return null;
    }

    private String deriveRawAccessibleTag(AccessibleContext context) {
        if (context != null) {
            String name = context.getAccessibleName();
            if (name != null && name.length() > 0)
                return name;
            AccessibleIcon[] icons = context.getAccessibleIcon();
            if (icons != null && icons.length > 0) {
                String description = icons[0].getAccessibleIconDescription();
                if (description != null && description.length() > 0)
                    return description;
            }
        }
        return null;
    }

    /**
     * Return a reasonable identifier for the given widget.
     */
    public static String getTag(WidgetProxy widget) {
        return getTester(widget.getClass()).deriveTag(widget);
    }

    /**
     * Provide a String that is fairly distinct for the given widget. For a generic widget, attempt
     * to look up some common patterns such as a title or label. Derived classes should absolutely
     * override this method if such a String exists.
     * <p/>
     * Don't use widget names as tags.
     * <p/>
     */
    public String deriveTag(WidgetProxy widget) {
        checkWidget(widget);

        // Try text.
        WidgetTester tester = getTester(widget);
        if (tester instanceof Textable) {
            Textable textable = (Textable) tester;
            if (!textable.isTextEditable(widget)) {
                String text = textable.getText(widget);
                if (text != null && text.length() > 0)
                    return text;
            }
        }

        // Try data ("name").
        Object object = tester.getData(widget, "name");
        if (object instanceof String) {
            String name = (String) object;
            if (name.length() > 0)
                return name;
        }

        // In the absence of any other tag, try to derive one from something
        // recognizable on one of its ancestors.
        WidgetProxy parent = getHierarchy().getParent(widget);
        if (parent != null) {
            String parentTag = getTag(parent);
            if (parentTag != null && parentTag.length() > 0) {
                // Don't use the tag if it's simply the window title; that
                // doesn't provide any extra information.
                if (!parentTag.equals(getWidgetText(parent))) {
                    StringBuffer buffer = new StringBuffer(parentTag);
                    String under = " under ";
                    int underIndex = parentTag.indexOf(under);
                    if (underIndex != -1)
                        buffer = buffer.delete(0, underIndex + under.length());
                    buffer.insert(0, under);
                    buffer.insert(0, simpleClassName(widget.getClass()));
                    return buffer.toString();
                }
            }
        }

        return null;
    }

    /**
     * Wait for an idle AWT event queue. Will return when there are no more events on the event
     * queue.
     */
    public void actionWaitForIdle() {
        waitForIdle();
    }

    /**
     * Delay the given number of ms.
     */
    public void actionDelay(final long time) {
        checkThread();
        checkTime(time);

        syncExec(new Runnable() {
            public void run() {
                delay(time);
            }
        });
    }

    /**
     * Click on the center of the widget.
     */
    public void actionClick(WidgetProxy widget) {
        checkWidget(widget);
        click(widget);
        if (!widget.isDisposed())
            waitForIdle();
    }

    /**
     * Click on a widget.
     */
    public void actionClick(WidgetProxy widget, int mask) {
        checkWidget(widget);
        click(widget, mask);
        if (!widget.isDisposed())
            waitForIdle();
    }

    /**
     * Click on the widget at the given location.
     */
    public void actionClick(WidgetProxy widget, int x, int y) {
        checkWidget(widget);
        click(widget, x, y, SWTProxy.BUTTON1);
        if (!widget.isDisposed())
            waitForIdle();
    }

    /**
     * Click on the widget at the given location.
     */
    public void actionClick(WidgetProxy widget, int x, int y, int mask) {
        checkWidget(widget);
        click(widget, x, y, mask);
        if (!widget.isDisposed())
            waitForIdle();
    }

    /**
     * Click on the widget at the given location.
     */
    public void actionClick(WidgetProxy widget, int x, int y, int mask, int count) {
        checkWidget(widget);
        click(widget, x, y, mask, count);
        if (!widget.isDisposed())
            waitForIdle();
    }

    /**
     * Click on the widget at the given location. The buttons string should be the
     * org.eclipse.swt.SWTProxy field name for the mask.
     */
    public void actionClick(WidgetProxy widget, int x, int y, String buttons) {
        checkWidget(widget);
        click(widget, x, y, getModifiers(buttons));
        if (!widget.isDisposed())
            waitForIdle();
    }

    /**
     * Click on the widget at the given location. The buttons string should be the
     * org.eclipse.swt.SWTProxy field name for the mask. This variation provides for multiple clicks.
     */
    public void actionClick(WidgetProxy widget, int x, int y, String buttons, int count) {
        checkWidget(widget);
        click(widget, x, y, getModifiers(buttons), count);
        waitForIdle();
    }

    public void actionDoubleClick(WidgetProxy widget) {
        checkWidget(widget);
        doubleClick(widget);
    }

    public void actionDoubleClick(WidgetProxy widget, int mask) {
        checkWidget(widget);
        doubleClick(widget, mask);
    }

    /*
    * TODO implement Robot.getKeyCode(String) and change these keyMethods to take Strings as
    * parameters
    */

    /**
     * Press the keys contained in the given accelerator
     */
    public void actionKeyPress(int accelerator) {
        keyPress(accelerator);
        waitForIdle();
    }

    /**
     * Release the keys contained in the given accelerator
     */
    public void actionKeyRelease(int accelerator) {
        keyRelease(accelerator);
        waitForIdle();
    }

    /**
     * Press, and release, the keys contained in the given accelerator
     */
    public void actionKey(int accelerator) {
        key(accelerator);
        waitForIdle();
    }

    /**
     * Type the given character. Note that this sends the key to whatever component currently has
     * the focus.
     */
    public void actionKeyChar(char c) {
        key((int) c);
        waitForIdle();
    }

    /**
     * Type the given string.
     */
    public void actionKeyString(String string) {
        key(string);
        waitForIdle();
    }

    /**
     * Set the focus on to the given component.
     * <p/>
     * TODO MAY NEED TO CHECK THAT THE CONTROL DOES INDEED HAVE FOCUS
     */
    public void actionFocus(WidgetProxy widget) {
        checkWidget(widget);
        if (!(widget instanceof ControlProxy)) {
            WidgetHierarchy hierarchy = getHierarchy();
            widget = hierarchy.getParent(widget);
            while (!(widget instanceof ControlProxy))
                widget = hierarchy.getParent(widget);
        }
        focus((ControlProxy) widget);
        waitForIdle();
    }

    /**
     * Perform a drag action. Derived classes should provide more specific identifiers for what is
     * being dragged, e.g. actionDragTableCell or actionDragListElement.
     */
    public void actionDrag(WidgetProxy sourceWidget, int x, int y) {
        checkWidget(sourceWidget);
        drag(sourceWidget, x, y, SWTProxy.BUTTON1);
        waitForIdle();
    }

    /**
     * Perform a drag action. Derived classes should provide more specific identifiers for what is
     * being dragged, e.g. actionDragTableCell or actionDragListElement. The modifiers represents
     * the set of active modifiers when the drop is made.
     */
    public void actionDrag(WidgetProxy sourceWidget, int x, int y, int modifiers) {
        checkWidget(sourceWidget);
        drag(sourceWidget, x, y, modifiers);
        waitForIdle();
    }

    /**
     * Perform a basic drop action (implicitly causing a preceding mouse drag).
     */
    public void actionDrop(WidgetProxy targetWidget, int x, int y) {
        checkWidget(targetWidget);
        drop(targetWidget, x, y, SWTProxy.BUTTON1);
        waitForIdle();
    }

    /**
     * Perform a basic drop action (implicitly causing a preceding mouse drag). The modifiers
     * represents the set of active modifiers when the drop is made.
     */
    public void actionDrop(WidgetProxy targetWidget, int x, int y, int modifiers) {
        checkWidget(targetWidget);
        drop(targetWidget, x, y, modifiers);
        waitForIdle();
    }

    /**
     * Return whether the widget's contents matches the given image.
     */
//    public boolean assertImage(WidgetProxy widget, ImageProxy image, boolean ignoreBorder) {
//        checkWidget(widget);
//        ImageProxy widgetImage = capture(widget, ignoreBorder);
//        try {
//            return new ImageComparator().compare(image, widgetImage) == 0;
//        } finally {
//            widgetImage.dispose();
//        }
//    }

    /**
     * Returns whether Decorations corresponding to the given String is showing. The string may be a
     * plain String or regular expression and may match either the Decoration's title or name
     */
//    public boolean assertDecorationsShowing(String title) {
//        return assertDecorationsShowing(title, true);
//    }
//
//    public boolean assertDecorationsShowing(String title, boolean topOnly) {
//        int status = getShellStatus(title, topOnly);
//        switch (status) {
//            case STATUS_VISIBLE:
//                return true;
//            case STATUS_NOT_VISIBLE:
//            case STATUS_NOT_FOUND:
//                return false;
//            default:
//                throw new RuntimeException("invalid status: " + status);
//        }
//    }
//
//    private static final int STATUS_NOT_FOUND = 0;
//
//    private static final int STATUS_NOT_VISIBLE = 1;
//
//    private static final int STATUS_VISIBLE = 2;
//
//    private int getShellStatus(final String title, final boolean topOnly) {
//        final int[] status = new int[]{STATUS_NOT_FOUND};
//        syncExec(new Runnable() {
//            public void run() {
//                getHierarchy().accept(new Visitor<WidgetProxy>() {
//                    public Result visit(WidgetProxy widget) {
//                        if (!widget.isDisposed()) {
//                            if (widget instanceof ShellProxy) {
//                                ShellProxy shell = (ShellProxy) widget;
//                                if (shell.getText().equals(title)) {
//                                    status[0] = shell.isVisible() ? STATUS_VISIBLE
//                                            : STATUS_NOT_VISIBLE;
//                                    return Result.stop;
//                                }
//                                if (topOnly)
//                                    return Result.prune;
//                            }
//                            return Result.ok;
//                        }
//                        return Result.prune;
//                    }
//                });
//            }
//        });
//        return status[0];
//    }

//    /**
//     * <b>Note:</b> This method ignores the value of <code>topOnly</code> and always behaves as
//     * if it were <code>false</code>.
//     *
//     * @deprecated Use {@link ShellTester#waitVisible(String,ShellProxy,long)}.
//     */
//    public static void waitForShellShowing(final String title, final boolean topOnly,
//                                           final long delay) {
//        ShellTester.waitVisible(title, null, delay);
//    }
//
//    /**
//     * @deprecated Use {@link ShellTester#waitVisible(String,ShellProxy,long)}.
//     */
//    public static void waitForShellShowing(final String title, final long delay) {
//        ShellTester.waitVisible(title, null, delay);
//    }
//
//    /**
//     * <b>Note:</b> This method ignores the value of <code>topOnly</code> and always behaves as
//     * if it were <code>false</code>.
//     *
//     * @deprecated Use {@link ShellTester#waitVisible(String,ShellProxy)}.
//     */
//    public static void waitForShellShowing(final String title, final boolean topOnly) {
//        ShellTester.waitVisible(title, null);
//    }
//
//    /**
//     * @deprecated Use {@link ShellTester#waitVisible(String,ShellProxy)}.
//     */
//    public static void waitForShellShowing(final String title) {
//        ShellTester.waitVisible(title, null);
//    }

    /**
     * Return whether the WidgetProxy represented by the given WidgetReference is available.
     */
//    public boolean assertWidgetShowing(WidgetReference reference) {
//        try {
//            reference.getWidget();
//            return true;
//        } catch (Exception e) {
//            return false;
//        }
//
//    }

    /**
     * Wait for the WidgetProxy represented by the given WidgetReference to become available. The timeout
     * is affected by abbot.robot.component_delay, which defaults to 30s.
     *
     * @deprecated Use the new matcher API in abbot.finder.swt.
     */
//    public static void waitForWidgetShowing(final WidgetReference ref) {
//        getWidgetTester().wait(new Condition() {
//            public boolean test() {
//                return getTester(WidgetProxy.class).assertWidgetShowing(ref);
//            }
//
//            public String toString() {
//                return ref + " to show";
//            }
//        }, DEFAULT_WIDGET_TIMEOUT);
//    }

    /*
    * Cached method support. Currently unused. Would be used by ScriptEditor if we had one. Some
    * day we may, so don't delete this code. It's harmless because it doesn't really do anything
    * unless it's called, which it isn't. Currently.
    */

    /*
    * TODO No need to have a cache for each WidgetTester instance. A cache per class would suffice.
    */

    private Method[] cachedMethods = null;

    /**
     * Look up methods with the given prefix.
     */
    private synchronized Method[] getMethods(String prefix, Class returnType, boolean onWidget) {

        if (cachedMethods == null)
            cachedMethods = getClass().getMethods();

        List methods = new ArrayList();
        Set names = new HashSet();
        for (int i = 0; i < cachedMethods.length; i++) {
            String name = cachedMethods[i].getName();
            if (!names.contains(name)) {
                Class[] parameterTypes = cachedMethods[i].getParameterTypes();
                if (name.startsWith(prefix)
                        && (returnType == null || returnType.equals(cachedMethods[i]
                        .getReturnType()))
                        && ((parameterTypes.length == 0 && !onWidget) || (parameterTypes.length > 0 && (WidgetProxy.class
                        .isAssignableFrom(parameterTypes[0]) == onWidget)))) {
                    methods.add(cachedMethods[i]);
                    names.add(name);
                }
            }
        }
        return (Method[]) methods.toArray(new Method[methods.size()]);
    }

    private Method[] cachedActions = null;

    /**
     * Return a list of all actions defined by this class that don't depend on a widget argument.
     */
    public synchronized Method[] getActions() {
        if (cachedActions == null)
            cachedActions = getMethods("action", void.class, false);
        return cachedActions;
    }

    private Method[] cachedWidgetActions = null;

    /**
     * Return a list of all actions defined by this class that require a widget argument.
     */
    public synchronized Method[] getWidgetActions() {
        if (cachedWidgetActions == null)
            cachedWidgetActions = getMethods("action", void.class, true);
        return cachedWidgetActions;
    }

    private Method[] cachedPropertyMethods = null;

    /**
     * Return an array of all property check methods defined by this class. The first argument
     * <b>must</b> be a WidgetProxy.
     */
    public synchronized Method[] getPropertyMethods() {
        if (cachedPropertyMethods == null) {
            List methods = new ArrayList();
            Collections.addAll(methods, (Object[]) getMethods("is", boolean.class, true));
            Collections.addAll(methods, (Object[]) getMethods("get", null, true));
            // Remove getXXX or isXXX methods which aren't property checks
            Class[] parameterTypes = {WidgetProxy.class};
            try {
                methods.remove(getClass().getMethod("getTag", parameterTypes));
                methods.remove(getClass().getMethod("getTester", parameterTypes));
                methods.remove(getClass().getMethod("isOnPopup", parameterTypes));
            } catch (NoSuchMethodException e) {
            }
            cachedPropertyMethods = (Method[]) methods.toArray(new Method[methods.size()]);
        }
        return cachedPropertyMethods;
    }

    private Method[] cachedAssertMethods = null;

    /**
     * Return a list of all assertions defined by this class that don't depend on a widget argument.
     */
    public synchronized Method[] getAssertMethods() {
        if (cachedAssertMethods == null)
            cachedAssertMethods = getMethods("assert", boolean.class, false);
        return cachedAssertMethods;
    }

    private Method[] cachedWidgetAssertMethods = null;

    /**
     * Return a list of all assertions defined by this class that require a widget argument.
     */
    public synchronized Method[] getComponentAssertMethods() {
        if (cachedWidgetAssertMethods == null)
            cachedWidgetAssertMethods = getMethods("assert", boolean.class, true);
        return cachedWidgetAssertMethods;
    }

    /**
     * Quick and dirty strip raw text from html, for getting the basic text from html-formatted
     * labels and buttons. Behavior is undefined for badly formatted html.
     */
    public static String stripHTML(String str) {
        if (str != null && (str.startsWith("<html>") || str.startsWith("<HTML>"))) {
            while (str.startsWith("<")) {
                int right = str.indexOf(">");
                if (right == -1)
                    break;
                str = str.substring(right + 1);
            }
            while (str.endsWith(">")) {
                int right = str.lastIndexOf("<");
                if (right == -1)
                    break;
                str = str.substring(0, right);
            }
        }
        return str;
    }
}