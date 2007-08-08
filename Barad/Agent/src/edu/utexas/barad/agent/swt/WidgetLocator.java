package edu.utexas.barad.agent.swt;

import edu.utexas.barad.agent.swt.proxy.custom.CTabItemProxy;
import edu.utexas.barad.agent.swt.proxy.graphics.PointProxy;
import edu.utexas.barad.agent.swt.proxy.graphics.RectangleProxy;
import edu.utexas.barad.agent.swt.proxy.widgets.*;

/**
 * University of Texas at Austin
 * Barad Project, Jul 20, 2007
 */
public class WidgetLocator {
    /**
     * Returns the location of the top left-hand corner of a the client area of a given Widget in
     * display space.
     *
     * @param w the Widget to be found
     * @return the location of Widget w
     */
    public static PointProxy getLocation(WidgetProxy w) {
        return getLocation(w, true);
    }

    /**
     * Returns the location of the top left-hand corner of a given Widget in display space,
     * optionally ignoring the Widget's trimmings. For example, to obtain the location of the area
     * of the Widget that can recieve mouse actions, use <code>ignoreBorder==true</code>.
     *
     * @param w            the Widget to be found
     * @param ignoreBorder should the border be considered?
     * @return the location of Widget w
     */
    public static PointProxy getLocation(WidgetProxy w, boolean ignoreBorder) {
        RectangleProxy bounds;
        try {
            bounds = getBounds(w, ignoreBorder);
        } catch (NullPointerException npe) {
            return null;
        }
        return PointProxy.Factory.newPointProxy(bounds.__fieldGetx(), bounds.__fieldGety());
    }

    /**
     * Finds the display-space rectangle that bounds the given Widget's client area.
     *
     * @param w the Widget to be found
     * @return the bounds of the widget's client area
     */
    public static RectangleProxy getBounds(WidgetProxy w) {
        return getBounds(w, true);
    }

    /**
     * Finds the display space rectangle that bounds the given Widget, optionally ignoring the
     * Widget's trimmings.
     *
     * @param w            the Widget to be found
     * @param ignoreBorder should the border be considered?
     * @return the bounding rectangle
     */
    public static RectangleProxy getBounds(WidgetProxy w, boolean ignoreBorder) {
        if (w instanceof ControlProxy) {
            if (ignoreBorder && w instanceof DecorationsProxy) {
                RectangleProxy bounds = ((DecorationsProxy) w).getBounds();
                RectangleProxy clientArea = ((DecorationsProxy) w).getClientArea();
                RectangleProxy calced = ((DecorationsProxy) w).computeTrim(
                        bounds.__fieldGetx(),
                        bounds.__fieldGety(),
                        clientArea.__fieldGetwidth(),
                        clientArea.__fieldGetheight());
                RectangleProxy correct = RectangleProxy.Factory.newRectangleProxy(2 * bounds.__fieldGetx() - calced.__fieldGetx(), 2 * bounds.__fieldGety() - calced.__fieldGety(),
                        clientArea.__fieldGetwidth() - 1, // bug workaround
                        clientArea.__fieldGetheight() - 1);
                return toDisplay(correct, ((DecorationsProxy) w));
            }
            if (ignoreBorder && w instanceof ButtonProxy) {
                RectangleProxy bounds = ((ButtonProxy) w).getBounds();
                bounds.__fieldSetwidth(bounds.__fieldGetwidth() - 1);
                bounds.__fieldSetheight(bounds.__fieldGetheight() - 1);
                return toDisplay(bounds, (ControlProxy) w);
            } else {
                return toDisplay(((ControlProxy) w).getBounds(), ((ControlProxy) w));
            }
        }
        // The following block exists to workaround problems with Widget.getBounds()
        if (w instanceof MenuProxy)
            return SWTWorkarounds.getBounds((MenuProxy) w);
        if (w instanceof CTabItemProxy) {
            return SWTWorkarounds.getBounds((CTabItemProxy) w);
        }
        if (w instanceof MenuItemProxy) {
            // MenuItem item = (MenuItem)w;
            // RectangleProxy bounds = SWTWorkarounds.getBounds(item);
            // PointProxy p = item.getParent().getParent().toDisplay(bounds.x,bounds.y);
            // return new RectangleProxy(p.x,p.y,bounds.width,bounds.height);
            // https://bugs.eclipse.org/bugs/show_bug.cgi?id=38436#c113
            // thanks Veronika Irvine
            return SWTWorkarounds.getBounds((MenuItemProxy) w);
        }
        if (w instanceof TabItemProxy) {
            // moved to SWTWorkarounds.getBounds(TabItem)
            // TabItem item = (TabItem)w;
            // RectangleProxy bounds = SWTWorkarounds.getBounds(item);
            // PointProxy p = item.getParent().toDisplay(bounds.x,bounds.y);
            // return new RectangleProxy(p.x,p.y,bounds.width,bounds.height);
            return SWTWorkarounds.getBounds((TabItemProxy) w);
        }
        if (w instanceof TableColumnProxy) {
            // moved to SWTWorkarounds.getBounds(TableColumn)
            // TableColumn col = (TableColumn)w;
            // RectangleProxy bounds = SWTWorkarounds.getBounds(col);
            // PointProxy p = col.getParent().toDisplay(bounds.x,bounds.y);
            // return new RectangleProxy(p.x,p.y,bounds.width,bounds.height);
            return SWTWorkarounds.getBounds((TableColumnProxy) w);
        }
//        if (w instanceof TreeColumnProxy) {
//            return SWTWorkarounds.getBounds((TreeColumnProxy) w);
//        }
        if (w instanceof ScrollBarProxy) {
            return SWTWorkarounds.getBounds((ScrollBarProxy) w);
            // ScrollBar bar = (ScrollBar)w;
            // RectangleProxy bounds = bar.getBounds();
            // PointProxy p = bar.getParent().toDisplay(bounds.x,bounds.y);
            // return new RectangleProxy(p.x,p.y,bounds.width,bounds.height);
        }
        if (w instanceof ToolItemProxy) {
            // moved to SWTWorkarounds.getBounds(ToolItem)
            // ToolItem item = (ToolItem)w;
            // RectangleProxy bounds = item.getBounds();
            // PointProxy p = item.getParent().toDisplay(bounds.x,bounds.y);
            // return new RectangleProxy(p.x,p.y,bounds.width,bounds.height);
            return SWTWorkarounds.getBounds((ToolItemProxy) w);
        }
        if (w instanceof CoolItemProxy) {
            // moved to SWTWorkarounds.getBounds(CoolItem)
            // CoolItem item = (CoolItem)w;
            // RectangleProxy bounds = item.getBounds();
            // PointProxy p = item.getParent().toDisplay(bounds.x,bounds.y);
            // return new RectangleProxy(p.x,p.y,bounds.width,bounds.height);
            return SWTWorkarounds.getBounds((CoolItemProxy) w);
        }
        if (w instanceof TreeItemProxy) {
            // moved to SWTWorkarounds.getBounds(TreeItem)
            // TreeItem item = (TreeItem)w;
            // RectangleProxy bounds = item.getBounds();
            // PointProxy p = item.getParent().toDisplay(bounds.x,bounds.y);
            // return new RectangleProxy(p.x,p.y,bounds.width,bounds.height);
            return SWTWorkarounds.getBounds((TreeItemProxy) w);
        }
        // Tao Weng 04/25/2005 10:39:05 AM: thanks!
        if (w instanceof TableItemProxy) {
            // moved to SWTWorkarounds.getBounds(TreeItem)
            // TableItem item = (TableItem)w;
            // RectangleProxy bounds = SWTWorkarounds.getBounds(item);
            // PointProxy p = item.getParent().toDisplay(bounds.x,bounds.y);
            // return new RectangleProxy(p.x,p.y,bounds.width,bounds.height);
            return SWTWorkarounds.getBounds((TableItemProxy) w);
        }

        // TODO: look for ALL items that have a getBounds() method in 3.0:
        // TODO: (cont'd) ToolItem, CoolItem, TableItem, TreeItem, etc
        // System.err.println(
        // "Unable to find coordinates of \""+w.getClass()+"\"; returning NULL");
        return null;
    }

    /**
     * Helper method to convert a Rectangle, given in the coordinate system of the given Control's
     * parent, to display-cordinates.
     */
    public static RectangleProxy toDisplay(RectangleProxy bounds, ControlProxy control) {

        // If it's a Shell or has no parent then the bounds are already display-relative.
        if (control instanceof ShellProxy || control.getParent() == null)
            return bounds;

        // Convert from parent-relative to display-relative.
        PointProxy point = control.getParent().toDisplay(bounds.__fieldGetx(), bounds.__fieldGety());
        return RectangleProxy.Factory.newRectangleProxy(point.__fieldGetx(), point.__fieldGety(), bounds.__fieldGetwidth(), bounds.__fieldGetheight());
    }
}