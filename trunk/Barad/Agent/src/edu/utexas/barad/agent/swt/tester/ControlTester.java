package edu.utexas.barad.agent.swt.tester;

import edu.utexas.barad.agent.swt.Displays.BooleanResult;
import edu.utexas.barad.agent.swt.Displays.IntResult;
import edu.utexas.barad.agent.swt.Displays.Result;
import edu.utexas.barad.agent.swt.proxy.accessibility.AccessibleProxy;
import edu.utexas.barad.agent.swt.proxy.events.*;
import edu.utexas.barad.agent.swt.proxy.graphics.*;
import edu.utexas.barad.agent.swt.proxy.widgets.*;
import edu.utexas.barad.agent.swt.Robot;

/**
 * University of Texas at Austin
 * Barad Project, Jul 26, 2007
 */
public class ControlTester extends WidgetTester {
    /**
     * Factory method.
     */
    public static ControlTester getControlTester() {
        return (ControlTester) getTester(ControlProxy.class);
    }

    /**
     * Constructs a new {@link ControlTester} associated with the specified {@link TesterRobot}.
     */
    public ControlTester(Robot swtRobot) {
        super(swtRobot);
    }

    /* Actions */

    /* Actions for invoking a menu item from a control's pop-up menu. */

    /**
     * @see WidgetTester#getMenu(WidgetProxy)
     */
    protected MenuProxy getMenu(WidgetProxy widget) {
        if (widget instanceof ControlProxy)
            return getMenu((ControlProxy) widget);
        return super.getMenu(widget);
    }

    /* Proxies */

    /**
     * Proxy for {@link ControlProxy#getAccessible()}.
     */
    public AccessibleProxy getAccessible(final ControlProxy control) {
        checkWidget(control);
        checkWidget(control);
        return (AccessibleProxy) syncExec(new Result() {
            public Object result() {
                return control.getAccessible();
            }
        });
    }

    /**
     * Proxy for {@link ControlProxy#getBackground()}. <p/>
     *
     * @param control the control under test.
     * @return the background color.
     */
    public ColorProxy getBackground(final ControlProxy control) {
        checkWidget(control);
        return (ColorProxy) syncExec(new Result() {
            public Object result() {
                return control.getBackground();
            }
        });
    }

    /**
     * Proxy for {@link ControlProxy#getBorderWidth()}. <p/>
     *
     * @param control the control under test.
     * @return the border width.
     */
    public int getBorderWidth(final ControlProxy control) {
        checkWidget(control);
        return syncExec(new IntResult() {
            public int result() {
                return control.getBorderWidth();
            }
        });
    }

    /**
     * Proxy for {@link ControlProxy#getBounds()}. <p/>
     *
     * @param control the control under test.
     * @return the bounds of the widget.
     */
    public RectangleProxy getBounds(final ControlProxy control) {
        checkWidget(control);
        return (RectangleProxy) syncExec(new Result() {
            public Object result() {
                return control.getBounds();
            }
        });
    }

    /**
     * Proxy for {@link ControlProxy#getEnabled()}. <p/>
     *
     * @param control the control under test.
     * @return true if the ControlProxy is enabled.
     */
    public boolean getEnabled(final ControlProxy control) {
        checkWidget(control);
        return syncExec(new BooleanResult() {
            public boolean result() {
                return control.getEnabled();
            }
        });
    }

    /**
     * Proxy for {@link ControlProxy#getFont()}. <p/>
     *
     * @param control the control under test.
     * @return the font associated with the control.
     */
    public FontProxy getFont(final ControlProxy control) {
        checkWidget(control);
        return (FontProxy) syncExec(new Result() {
            public Object result() {
                return control.getFont();
            }
        });
    }

    /**
     * Proxy for {@link ControlProxy#getForeground()}. <p/>
     *
     * @param control the control under test.
     * @return the foreground color.
     */
    public ColorProxy getForeground(final ControlProxy control) {
        checkWidget(control);
        return (ColorProxy) syncExec(new Result() {
            public Object result() {
                return control.getForeground();
            }
        });
    }

    /**
     * Proxy for {@link ControlProxy#getLayoutData()}. <p/>
     *
     * @param control the control under test.
     * @return the layout data.
     */
    public Object getLayoutData(final ControlProxy control) {
        checkWidget(control);
        return syncExec(new Result() {
            public Object result() {
                return control.getLayoutData();
            }
        });
    }

    /**
     * Proxy for {@link ControlProxy#getLocation()}. <p/>
     *
     * @param control the control under test.
     * @return the location of the control.
     */
    public PointProxy getLocation(final ControlProxy control) {
        checkWidget(control);
        return (PointProxy) syncExec(new Result() {
            public Object result() {
                return control.getLocation();
            }
        });
    }

    /**
     * @see ControlProxy#getMenu()
     */
    public MenuProxy getMenu(final ControlProxy control) {
        checkWidget(control);
        return (MenuProxy) syncExec(new Result() {
            public Object result() {
                return control.getMenu();
            }
        });
    }

    /**
     * Proxy for {@link ControlProxy#getParent()}. <p/>
     *
     * @param control the control under test.
     * @return the control's parent.
     */
    public CompositeProxy getParent(final ControlProxy control) {
        checkWidget(control);
        return (CompositeProxy) syncExec(new Result() {
            public Object result() {
                return control.getParent();
            }
        });
    }

    /**
     * Proxy for {@link ControlProxy#getShell()}. <p/>
     *
     * @param control the control under test.
     * @return the control's shell.
     */
    public ShellProxy getShell(final ControlProxy control) {
        checkWidget(control);
        return (ShellProxy) syncExec(new Result() {
            public Object result() {
                return control.getShell();
            }
        });
    }

    /**
     * Proxy for {@link ControlProxy#getSize()}. <p/>
     *
     * @param control the control under test.
     * @return the size of the control.
     */
    public PointProxy getSize(final ControlProxy control) {
        checkWidget(control);
        return (PointProxy) syncExec(new Result() {
            public Object result() {
                return control.getSize();
            }
        });
    }

    /**
     * Proxy for {@link ControlProxy#getToolTipText()}. <p/>
     *
     * @param control the control under test.
     * @return the tool tip associated with the control.
     */
    public String getToolTipText(final ControlProxy control) {
        checkWidget(control);
        return (String) syncExec(new Result() {
            public Object result() {
                return control.getToolTipText();
            }
        });
    }

    /**
     * Proxy for {@link ControlProxy#getVisible()}. <p/>
     *
     * @param control the control under test.
     * @return true if this control is visible.
     */
    public boolean getVisible(final ControlProxy control) {
        checkWidget(control);
        return syncExec(new BooleanResult() {
            public boolean result() {
                return control.getVisible();
            }
        });
    }

    /**
     * Proxy for {@link ControlProxy#isVisible()}.
     *
     * @param control the control under test.
     * @return true if this control and all of its ancestor's are visible.
     */
    public boolean isVisible(final ControlProxy control) {
        checkWidget(control);
        return syncExec(new BooleanResult() {
            public boolean result() {
                return control.isVisible();
            }
        });
    }

    /**
     * @see WidgetTester#isVisible(WidgetProxy)
     */
    public boolean isVisible(WidgetProxy widget) {
        if (widget instanceof ControlProxy)
            return isVisible((ControlProxy) widget);
        return super.isVisible(widget);
    }

    /**
     * Proxy for {@link ControlProxy#isEnabled()}.
     */
    public boolean isEnabled(final ControlProxy control) {
        checkWidget(control);
        return syncExec(new BooleanResult() {
            public boolean result() {
                return control.isEnabled();
            }
        });
    }

    /**
     * Proxy for {@link ControlProxy#isFocusControl()}. <p/>
     *
     * @param control the control under test.
     * @return true if this control has focus.
     */
    public boolean isFocusControl(final ControlProxy control) {
        checkWidget(control);
        return syncExec(new BooleanResult() {
            public boolean result() {
                return control.isFocusControl();
            }
        });
    }

    /**
     * Proxy for {@link ControlProxy#isReparentable()}. <p/>
     *
     * @param control the control under test.
     * @return true if this ControlProxy is reparentable.
     */
    public boolean isReparentable(final ControlProxy control) {
        checkWidget(control);
        return syncExec(new BooleanResult() {
            public boolean result() {
                return control.isReparentable();
            }
        });
    }

    /**
     * Proxy for {@link ControlProxy#toControl(int,int)}. <p/>
     *
     * @param control the control under test.
     * @param x       the x coordinate to be translated.
     * @param y       the y coordinate to be translated.
     * @return the translated coordinates.
     */
    public PointProxy toControl(final ControlProxy control, final int x, final int y) {
        checkWidget(control);
        return (PointProxy) syncExec(new Result() {
            public Object result() {
                return control.toControl(x, y);
            }
        });
    }

    /**
     * Proxy for {@link ControlProxy#toControl(PointProxy)}. <p/>
     *
     * @param control the control under test.
     * @param point   the point to be translated.
     * @return the translated coordinates.
     */
    public PointProxy toControl(final ControlProxy control, final PointProxy point) {
        checkWidget(control);
        return (PointProxy) syncExec(new Result() {
            public Object result() {
                return control.toControl(point);
            }
        });
    }

    /**
     * Proxy for {@link ControlProxy#toDisplay(int,int)}. <p/>
     *
     * @param control the control under test.
     * @param x       the x coordinate to be translated.
     * @param y       the y coordinate to be translated.
     * @return the translated coordinates.
     */
    public PointProxy toDisplay(final ControlProxy control, final int x, final int y) {
        checkWidget(control);
        return (PointProxy) syncExec(new Result() {
            public Object result() {
                return control.toDisplay(x, y);
            }
        });
    }

    /**
     * Proxy for {@link ControlProxy#toDisplay(PointProxy)}. <p/>
     *
     * @param control the control under test.
     * @param point   the point to be translated.
     * @return the translated coordinates.
     */
    public PointProxy toDisplay(final ControlProxy control, final PointProxy point) {
        checkWidget(control);
        return (PointProxy) syncExec(new Result() {
            public Object result() {
                return control.toDisplay(point);
            }
        });
    }

    /* End getters */
    /*
     * Add and remove listeners. This is mainly intended for adding listeners to enable JUnit checks
     * that certain events are issued.
     */
    /**
     * Proxy for {@link ControlProxy#addControlListener(ControlListenerProxy)}. <p/>
     *
     * @param control  the control to add the listener to.
     * @param listener the listener to add.
     */
    public void addControlListener(final ControlProxy control, final ControlListenerProxy listener) {
        checkWidget(control);
        syncExec(new Runnable() {
            public void run() {
                control.addControlListener(listener);
            }
        });
    }

    /**
     * Proxy for {@link ControlProxy#removeControlListener(ControlListenerProxy)}.
     * <p/>
     *
     * @param control  the control to remove the listener from.
     * @param listener the listener to remove
     */
    public void removeControlListener(final ControlProxy control, final ControlListenerProxy listener) {
        checkWidget(control);
        syncExec(new Runnable() {
            public void run() {
                control.removeControlListener(listener);
            }
        });
    }

    /**
     * Proxy for {@link ControlProxy#addFocusListener(FocusListenerProxy)}. <p/>
     *
     * @param control  the control to add the listener to.
     * @param listener the listener to add.
     */
    public void addFocusListener(final ControlProxy control, final FocusListenerProxy listener) {
        checkWidget(control);
        syncExec(new Runnable() {
            public void run() {
                control.addFocusListener(listener);
            }
        });
    }

    /**
     * Proxy for {@link ControlProxy#removeFocusListener(FocusListenerProxy)}. <p/>
     *
     * @param control  the control to remove the listener from.
     * @param listener the listener to remove
     */
    public void removeFocusListener(final ControlProxy control, final FocusListenerProxy listener) {
        checkWidget(control);
        syncExec(new Runnable() {
            public void run() {
                control.removeFocusListener(listener);
            }
        });
    }

    /**
     * Proxy for {@link ControlProxy#addHelpListener(HelpListenerProxy)}. <p/>
     *
     * @param control  the control to add the listener to.
     * @param listener the listener to add.
     */
    public void addHelpListener(final ControlProxy control, final HelpListenerProxy listener) {
        checkWidget(control);
        syncExec(new Runnable() {
            public void run() {
                control.addHelpListener(listener);
            }
        });
    }

    /**
     * Proxy for {@link ControlProxy#removeHelpListener(HelpListenerProxy)}. <p/>
     *
     * @param control  the control to remove the listener from.
     * @param listener the listener to remove
     */
    public void removeHelpListener(final ControlProxy control, final HelpListenerProxy listener) {
        checkWidget(control);
        syncExec(new Runnable() {
            public void run() {
                control.removeHelpListener(listener);
            }
        });
    }

    /**
     * Proxy for {@link ControlProxy#addKeyListener(KeyListenerProxy)}. <p/>
     *
     * @param control  the control to add the listener to.
     * @param listener the listener to add.
     */
    public void addKeyListener(final ControlProxy control, final KeyListenerProxy listener) {
        checkWidget(control);
        syncExec(new Runnable() {
            public void run() {
                control.addKeyListener(listener);
            }
        });
    }

    /**
     * Proxy for {@link ControlProxy#removeKeyListener(KeyListenerProxy)}. <p/>
     *
     * @param control  the control to remove the listener from.
     * @param listener the listener to remove
     */
    public void removeKeyListener(final ControlProxy control, final KeyListenerProxy listener) {
        checkWidget(control);
        syncExec(new Runnable() {
            public void run() {
                control.removeKeyListener(listener);
            }
        });
    }

    /**
     * Proxy for {@link ControlProxy#addMouseListener(MouseListenerProxy)}. <p/>
     *
     * @param control  the control to add the listener to.
     * @param listener the listener to add.
     */
    public void addMouseListener(final ControlProxy control, final MouseListenerProxy listener) {
        checkWidget(control);
        syncExec(new Runnable() {
            public void run() {
                control.addMouseListener(listener);
            }
        });
    }

    /**
     * Proxy for {@link ControlProxy#removeMouseListener(MouseListenerProxy)}. <p/>
     *
     * @param control  the control to remove the listener from.
     * @param listener the listener to remove
     */
    public void removeMouseListener(final ControlProxy control, final MouseListenerProxy listener) {
        checkWidget(control);
        syncExec(new Runnable() {
            public void run() {
                control.removeMouseListener(listener);
            }
        });
    }

    /**
     * Proxy for {@link ControlProxy#addMouseMoveListener(MouseMoveListenerProxy)}.
     * <p/>
     *
     * @param control  the control to add the listener to.
     * @param listener the listener to add.
     */
    public void addMouseMoveListener(final ControlProxy control, final MouseMoveListenerProxy listener) {
        checkWidget(control);
        syncExec(new Runnable() {
            public void run() {
                control.addMouseMoveListener(listener);
            }
        });
    }

    /**
     * Proxy for {@link ControlProxy#removeMouseMoveListener(MouseMoveListenerProxy)}.
     * <p/>
     *
     * @param control  the control to remove the listener from.
     * @param listener the listener to remove
     */
    public void removeMouseMoveListener(final ControlProxy control, final MouseMoveListenerProxy listener) {
        checkWidget(control);
        syncExec(new Runnable() {
            public void run() {
                control.removeMouseMoveListener(listener);
            }
        });
    }

    /**
     * Proxy for {@link ControlProxy#addMouseTrackListener(MouseTrackListenerProxy)}.
     * <p/>
     *
     * @param control  the control to add the listener to.
     * @param listener the listener to add.
     */
    public void addMouseTrackListener(final ControlProxy control, final MouseTrackListenerProxy listener) {
        checkWidget(control);
        syncExec(new Runnable() {
            public void run() {
                control.addMouseTrackListener(listener);
            }
        });
    }

    /**
     * Proxy for {@link ControlProxy#removeMouseTrackListener(MouseTrackListenerProxy)}.
     * <p/>
     *
     * @param control  the control to remove the listener from.
     * @param listener the listener to remove
     */
    public void removeMouseTrackListener(final ControlProxy control, final MouseTrackListenerProxy listener) {
        checkWidget(control);
        syncExec(new Runnable() {
            public void run() {
                control.removeMouseTrackListener(listener);
            }
        });
    }

    /**
     * Proxy for {@link ControlProxy#addPaintListener(PaintListenerProxy)}. <p/>
     *
     * @param control  the control to add the listener to.
     * @param listener the listener to add.
     */
    public void addPaintListener(final ControlProxy control, final PaintListenerProxy listener) {
        checkWidget(control);
        syncExec(new Runnable() {
            public void run() {
                control.addPaintListener(listener);
            }
        });
    }

    /**
     * Proxy for {@link ControlProxy#removePaintListener(PaintListenerProxy)}. <p/>
     *
     * @param control  the control to remove the listener from.
     * @param listener the listener to remove
     */
    public void removePaintListener(final ControlProxy control, final PaintListenerProxy listener) {
        checkWidget(control);
        syncExec(new Runnable() {
            public void run() {
                control.removePaintListener(listener);
            }
        });
    }

    /**
     * Proxy for {@link ControlProxy#addTraverseListener(TraverseListenerProxy)}. <p/>
     *
     * @param control  the control to add the listener to.
     * @param listener the listener to add.
     */
    public void addTraverseListener(final ControlProxy control, final TraverseListenerProxy listener) {
        checkWidget(control);
        syncExec(new Runnable() {
            public void run() {
                control.addTraverseListener(listener);
            }
        });
    }

    /**
     * Proxy for {@link ControlProxy#removeTraverseListener(TraverseListenerProxy)}.
     * <p/>
     *
     * @param control  the control to remove the listener from.
     * @param listener the listener to remove.
     */
    public void removeTraverseListener(final ControlProxy control, final TraverseListenerProxy listener) {
        checkWidget(control);
        syncExec(new Runnable() {
            public void run() {
                control.removeTraverseListener(listener);
            }
        });
    }

    /*
     * End add and remove listeners.
     */

    /**
     * Proxy for {@link ControlProxy#setBackground(ColorProxy)}.
     */
    public void setBackground(final ControlProxy control, final ColorProxy color) {
        checkWidget(control);
        syncExec(new Runnable() {
            public void run() {
                control.setBackground(color);
            }
        });
    }

    /**
     * Proxy for {@link ControlProxy#setBounds(RectangleProxy)}.
     */
    public void setBounds(final ControlProxy control, final RectangleProxy bounds) {
        checkWidget(control);
        syncExec(new Runnable() {
            public void run() {
                control.setBounds(bounds);
            }
        });
    }

    /**
     * Proxy for {@link ControlProxy#setBounds(int,int,int,int)}.
     */
    public void setBounds(final ControlProxy control, final int x, final int y, final int width,
                          final int height) {
        checkWidget(control);
        syncExec(new Runnable() {
            public void run() {
                control.setBounds(x, y, width, height);
            }
        });
    }

    /**
     * Proxy for {@link ControlProxy#setCursor(CursorProxy)}.
     */
    public void setCursor(final ControlProxy control, final CursorProxy cursor) {
        checkWidget(control);
        syncExec(new Runnable() {
            public void run() {
                control.setCursor(cursor);
            }
        });
    }

    /**
     * Proxy for {@link ControlProxy#setCapture(boolean)}.
     */
    public void setCapture(final ControlProxy control, final boolean capture) {
        checkWidget(control);
        syncExec(new Runnable() {
            public void run() {
                control.setCapture(capture);
            }
        });
    }

    /**
     * Proxy for {@link ControlProxy#setEnabled(boolean)}.
     */
    public void setEnabled(final ControlProxy control, final boolean enabled) {
        checkWidget(control);
        syncExec(new Runnable() {
            public void run() {
                control.setEnabled(enabled);
            }
        });
    }

    /**
     * Proxy for {@link ControlProxy#setFocus()}.
     */
    public void setFocus(final ControlProxy control) {
        checkWidget(control);
        syncExec(new Runnable() {
            public void run() {
                control.setFocus();
            }
        });
    }

    /**
     * Proxy for {@link ControlProxy#setFont(FontProxy)}.
     */
    public void setFont(final ControlProxy control, final FontProxy font) {
        checkWidget(control);
        syncExec(new Runnable() {
            public void run() {
                control.setFont(font);
            }
        });
    }

    /**
     * Proxy for {@link ControlProxy#setForeground(ColorProxy)}.
     */
    public void setForeground(final ControlProxy control, final ColorProxy color) {
        checkWidget(control);
        syncExec(new Runnable() {
            public void run() {
                control.setForeground(color);
            }
        });
    }

    /**
     * Proxy for {@link ControlProxy#setLayoutData(Object)}.
     */
    public void setLayoutData(final ControlProxy control, final Object layoutData) {
        checkWidget(control);
        syncExec(new Runnable() {
            public void run() {
                control.setLayoutData(layoutData);
            }
        });
    }

    /**
     * Proxy for {@link ControlProxy#setLocation(PointProxy)}.
     */
    public void setLocation(final ControlProxy control, final PointProxy location) {
        checkWidget(control);
        syncExec(new Runnable() {
            public void run() {
                control.setLocation(location);
            }
        });
    }

    /**
     * Proxy for {@link ControlProxy#setLocation(int,int)}.
     */
    public void setLocation(final ControlProxy control, final int x, final int y) {
        checkWidget(control);
        syncExec(new Runnable() {
            public void run() {
                control.setLocation(x, y);
            }
        });
    }

    /**
     * Proxy for {@link ControlProxy#setMenu(MenuProxy)}.
     */
    public void setMenu(final ControlProxy control, final MenuProxy menu) {
        checkWidget(control);
        syncExec(new Runnable() {
            public void run() {
                control.setMenu(menu);
            }
        });
    }

    /**
     * Proxy for {@link ControlProxy#setParent(CompositeProxy)}.
     */
    public void setParent(final ControlProxy control, final CompositeProxy composite) {
        checkWidget(control);
        syncExec(new Runnable() {
            public void run() {
                control.setParent(composite);
            }
        });
    }

    /**
     * Proxy for {@link ControlProxy#setRedraw(boolean)}.
     */
    public void setRedraw(final ControlProxy control, final boolean redraw) {
        checkWidget(control);
        syncExec(new Runnable() {
            public void run() {
                control.setRedraw(redraw);
            }
        });
    }

    /**
     * Proxy for {@link ControlProxy#setSize(PointProxy)}.
     */
    public void setSize(final ControlProxy control, final PointProxy size) {
        checkWidget(control);
        syncExec(new Runnable() {
            public void run() {
                control.setSize(size);
            }
        });
    }

    /**
     * Proxy for {@link ControlProxy#setSize(int,int)}.
     */
    public void setSize(final ControlProxy control, final int x, final int y) {
        checkWidget(control);
        syncExec(new Runnable() {
            public void run() {
                control.setSize(x, y);
            }
        });
    }

    /**
     * Proxy for {@link ControlProxy#setToolTipText(String)}.
     */
    public void setToolTipText(final ControlProxy control, final String string) {
        checkWidget(control);
        syncExec(new Runnable() {
            public void run() {
                control.setToolTipText(string);
            }
        });
    }

    /**
     * Proxy for {@link ControlProxy#setVisible(boolean)}.
     */
    public void setVisible(final ControlProxy control, final boolean visible) {
        checkWidget(control);
        syncExec(new Runnable() {
            public void run() {
                control.setVisible(visible);
            }
        });
    }

    /**
     * Proxy for {@link ControlProxy#traverse(int)}.
     */
    public void traverse(final ControlProxy control, final int traversal) {
        checkWidget(control);
        syncExec(new Runnable() {
            public void run() {
                control.traverse(traversal);
            }
        });
    }

    /**
     * Proxy for {@link ControlProxy#update()}.
     */
    public void update(final ControlProxy control) {
        checkWidget(control);
        syncExec(new Runnable() {
            public void run() {
                control.update();
            }
        });
    }
}