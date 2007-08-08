package edu.utexas.barad.agent.swt;

import edu.utexas.barad.agent.swt.proxy.SWTProxy;
import edu.utexas.barad.agent.swt.proxy.custom.CTabItemProxy;
import edu.utexas.barad.agent.swt.proxy.graphics.PointProxy;
import edu.utexas.barad.agent.swt.proxy.graphics.RectangleProxy;
import edu.utexas.barad.agent.swt.proxy.widgets.*;
import org.apache.log4j.Logger;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * University of Texas at Austin
 * Barad Project, Jul 20, 2007
 */
public class SWTWorkarounds {
    private static final Logger logger = Logger.getLogger(SWTWorkarounds.class);

    /**
     * ************************ COMMON ****************************
     */

//    /**
//     * @param object
//     * @return
//     */
//    public static RectangleProxy getBounds(Object object) {
//        RectangleProxy result = null;
//        try {
//            Method method = object.getClass().getDeclaredMethod("getBounds", (Class[]) null);
//            method.setAccessible(true);
//            result = (RectangleProxy) method.invoke(object, (Object[]) null);
//        } catch (Throwable t) {
//            logger.debug(t);
//        }
//        return result;
//    }

    public static RectangleProxy getBounds(MenuItemProxy menuItem) {
        RectangleProxy itemRect = menuItem.getBounds();
        RectangleProxy menuRect = menuItem.getParent().getBounds();
        if ((menuItem.getParent().getStyle() & SWTProxy.RIGHT_TO_LEFT) != 0) {
            itemRect.__fieldSetx(menuRect.__fieldGetx() + menuRect.__fieldGetwidth() - itemRect.__fieldGetwidth() - itemRect.__fieldGetx());
        } else {
            itemRect.__fieldSetx(itemRect.__fieldGetx() + menuRect.__fieldGetx());
        }
        // https://bugs.eclipse.org/bugs/show_bug.cgi?id=38436#c143
        itemRect.__fieldSety(itemRect.__fieldGety() + menuRect.__fieldGety());
        return itemRect;
    }

    public static RectangleProxy getBounds(MenuProxy menu) {
        return menu.getBounds();
    }

    public static RectangleProxy getBounds(ScrollBarProxy scrollBar) {
        PointProxy size = scrollBar.getSize();
        RectangleProxy bounds = scrollBar.getParent().getBounds();
        if ((scrollBar.getParent().getStyle() & SWTProxy.RIGHT_TO_LEFT) != 0) {
            bounds.__fieldSetx(0);
            bounds.__fieldSetwidth(size.__fieldGetx());
        } else {
            bounds.__fieldSetx(bounds.__fieldGetwidth() - size.__fieldGetx());
        }
        bounds.__fieldSety(bounds.__fieldGetheight() - size.__fieldGety());
        // TODO - coordinate system may change when the API is added to SWT
        return scrollBar.getDisplay().map(scrollBar.getParent(), null, bounds);
    }

    /**
     * ************************ WIN32 ****************************
     */

    /**
     *
     * @param hWnd
     * @param Msg
     * @param wParam
     * @param lParam
     * @return
     */
    static int SendMessage(int hWnd, int Msg, int wParam, int[] lParam) {
        int result = 0;
        try {
            Class clazz = Class.forName("org.eclipse.swt.internal.win32.OS");
            Class[] params = new Class[]{
                    Integer.TYPE,
                    Integer.TYPE,
                    Integer.TYPE,
                    lParam.getClass(),
            };
            Method method = clazz.getMethod("SendMessage", params);
            Object[] args = new Object[]{
                    hWnd,
                    Msg,
                    wParam,
                    lParam,
            };
            result = (Integer) method.invoke(clazz, args);
        } catch (Throwable e) {
            // TODO - decide what should happen when the method is unavailable
        }
        return result;
    }

    static RectangleProxy win32_getBounds(TabItemProxy tabItem) {
        TabFolderProxy parent = tabItem.getParent();
        int index = parent.indexOf(tabItem);
        if (index == -1) {
            return RectangleProxy.Factory.newRectangleProxy(0, 0, 0, 0);
        }
        int[] rect = new int[4];
        SendMessage(parent.__fieldGethandle(), /*TCM_GETITEMRECT*/ 0x130a, index, rect);
        int width = rect[2] - rect[0];
        int height = rect[3] - rect[1];
        RectangleProxy bounds = RectangleProxy.Factory.newRectangleProxy(rect[0], rect[1], width, height);
        return tabItem.getDisplay().map(tabItem.getParent(), null, bounds);
    }

    static RectangleProxy win32_getBounds(TableColumnProxy tableColumn) {
        TableProxy parent = tableColumn.getParent();
        int index = parent.indexOf(tableColumn);
        if (index == -1) {
            return RectangleProxy.Factory.newRectangleProxy(0, 0, 0, 0);
        }
        int hwndHeader = SendMessage(parent.__fieldGethandle(), /*LVM_GETHEADER*/ 0x101f, 0, new int[0]);
        int[] rect = new int[4];
        SendMessage(hwndHeader, /*HDM_GETITEMRECT*/ 0x1200 + 7, index, rect);
        int width = rect[2] - rect[0];
        int height = rect[3] - rect[1];
        RectangleProxy bounds = RectangleProxy.Factory.newRectangleProxy(rect[0], rect[1], width, height);
        // TODO - coordinate system may change when the API is added to SWT
        return tableColumn.getDisplay().map(parent, null, bounds);
    }

    /**
     * ************************ GTK ****************************
     */

    /**
     *
     * @param handle
     * @param bounds
     */
    static void gtk_getBounds(int handle, RectangleProxy bounds) {
        try {
            Class clazz = Class.forName("org.eclipse.swt.internal.gtk.OS");
            Class[] params = new Class[]{Integer.TYPE};
            Object[] args = new Object[]{handle};
            Method method = clazz.getMethod("GTK_WIDGET_X", params);
            bounds.__fieldSetx((Integer) method.invoke(clazz, args));
            method = clazz.getMethod("GTK_WIDGET_Y", params);
            bounds.__fieldSety((Integer) method.invoke(clazz, args));
            method = clazz.getMethod("GTK_WIDGET_WIDTH", params);
            bounds.__fieldSetwidth((Integer) method.invoke(clazz, args));
            method = clazz.getMethod("GTK_WIDGET_HEIGHT", params);
            bounds.__fieldSetheight((Integer) method.invoke(clazz, args));
        } catch (Throwable e) {
            // TODO - decide what should happen when the method is unavailable
        }
    }

    static RectangleProxy gtk_getBounds(TableColumnProxy tabColumn) {
        RectangleProxy bounds = RectangleProxy.Factory.newRectangleProxy(0, 0, 0, 0);
        try {
            Class c = tabColumn.getClass();
            Field f = c.getDeclaredField("buttonHandle");
            f.setAccessible(true);
            int handle = f.getInt(tabColumn);
            gtk_getBounds(handle, bounds);
        } catch (Throwable e) {
            // TODO - decide what should happen when the method is unavailable
        }
        return tabColumn.getDisplay().map(tabColumn.getParent(), null, bounds);
    }

    static RectangleProxy gtk_getBounds(TabItemProxy tabItem) {
        RectangleProxy bounds = RectangleProxy.Factory.newRectangleProxy(0, 0, 0, 0);
        try {
            Class c = Class.forName("org.eclipse.swt.widgets.Widget");
            Field f = c.getDeclaredField("handle");
            f.setAccessible(true);
            int handle = f.getInt(tabItem);
            gtk_getBounds(handle, bounds);
        } catch (Throwable e) {
            // TODO - decide what should happen when the method is unavailable
        }
        return tabItem.getDisplay().map(tabItem.getParent(), null, bounds);
    }

    /**
     * ************************ MOTIF  ****************************
     */

    /**
     *
     * @param tabItem
     * @return
     */
    static RectangleProxy motif_getBounds(TabItemProxy tabItem) {
        RectangleProxy bounds = RectangleProxy.Factory.newRectangleProxy(0, 0, 0, 0);
        try {
            Class c = tabItem.getClass();
            Method m = c.getDeclaredMethod("getBounds", (Class[]) null);
            m.setAccessible(true);
            bounds = (RectangleProxy) m.invoke(tabItem, (Object[]) null);
            int margin = 2;
            bounds.__fieldSetx(bounds.__fieldGetx() + margin);
            bounds.__fieldSety(bounds.__fieldGety() + margin);
            bounds.__fieldSetwidth(bounds.__fieldGetwidth() - 2 * margin);
            bounds.__fieldSetheight(bounds.__fieldGetheight() - margin);
        } catch (Throwable e) {
            // TODO - decide what should happen when the method is unavailable
        }
        return tabItem.getDisplay().map(tabItem.getParent(), null, bounds);
    }

    static RectangleProxy motif_getBounds(TableColumnProxy tableColumn) {
        RectangleProxy bounds = RectangleProxy.Factory.newRectangleProxy(0, 0, 0, 0);
        try {
            Class c = tableColumn.getClass();
            Method m = c.getDeclaredMethod("getX", (Class[]) null);
            m.setAccessible(true);
            bounds.__fieldSetx((Integer) m.invoke(tableColumn, (Object[]) null));
            bounds.__fieldSetwidth(tableColumn.getWidth() - 2);
            bounds.__fieldSetheight(tableColumn.getParent().getHeaderHeight() - 2);
        } catch (Throwable e) {
            // TODO - decide what should happen when the method is unavailable
        }
        return tableColumn.getDisplay().map(tableColumn.getParent(), null, bounds);
    }

    /**
     * ************************ CARBON  ****************************
     */

    /**
     * 
     * @param tabItem
     * @return
     */
    static RectangleProxy carbon_getBounds(TabItemProxy tabItem) {
        return null;
    }

    static RectangleProxy carbon_getBounds(TableColumnProxy tableColumn) {
        return null;
    }

    public static RectangleProxy getBounds(TabItemProxy tabItem) {
        if (SWTProxy.getPlatform().equals("win32")) {
            return win32_getBounds(tabItem);
        }
        if (SWTProxy.getPlatform().equals("gtk")) {
            return gtk_getBounds(tabItem);
        }
        if (SWTProxy.getPlatform().equals("motif")) {
            return motif_getBounds(tabItem);
        }
        if (SWTProxy.getPlatform().equals("carbon")) {
            return carbon_getBounds(tabItem);
        }
        return null;
    }

    public static RectangleProxy getBounds(TableColumnProxy tableColumn) {
        if (SWTProxy.getPlatform().equals("win32")) {
            return win32_getBounds(tableColumn);
        }
        if (SWTProxy.getPlatform().equals("gtk")) {
            return gtk_getBounds(tableColumn);
        }
        if (SWTProxy.getPlatform().equals("motif")) {
            return motif_getBounds(tableColumn);
        }
        if (SWTProxy.getPlatform().equals("carbon")) {
            return carbon_getBounds(tableColumn);
        }
        return null;
    }

    public static RectangleProxy getBounds(TableItemProxy item) {
        return item.getDisplay().map(item.getParent(), null, item.getBounds(0));
    }

    public static RectangleProxy getBounds(TreeItemProxy item) {
        return item.getDisplay().map(item.getParent(), null, item.getBounds());
    }

    public static RectangleProxy getBounds(CTabItemProxy item) {
        return item.getDisplay().map(item.getParent(), null, item.getBounds());
    }

    public static RectangleProxy getBounds(ToolItemProxy item) {
        return item.getDisplay().map(item.getParent(), null, item.getBounds());
    }

    public static RectangleProxy getBounds(CoolItemProxy item) {
        return item.getDisplay().map(item.getParent(), null, item.getBounds());
    }
}