package edu.utexas.barad.agent.swt.proxy.widgets;

import edu.utexas.barad.agent.swt.proxy.accessibility.AccessibleProxy;
import edu.utexas.barad.agent.swt.proxy.events.*;
import edu.utexas.barad.agent.swt.proxy.graphics.*;

/**
 * University of Texas at Austin
 * Barad Project, Jul 6, 2007
 */
public interface ControlProxy extends WidgetProxy {
    public int __fieldGethandle();

    public void addControlListener(ControlListenerProxy listener);

    public void addFocusListener(FocusListenerProxy listener);

    public void addHelpListener(HelpListenerProxy listener);

    public void addKeyListener(KeyListenerProxy listener);

    public void addMouseListener(MouseListenerProxy listener);

    public void addMouseMoveListener(MouseMoveListenerProxy listener);

    public void addMouseTrackListener(MouseTrackListenerProxy listener);

    public void addPaintListener(PaintListenerProxy listener);

    public void addTraverseListener(TraverseListenerProxy listener);

    public PointProxy computeSize(int wHint, int hHint);

    public PointProxy computeSize(int wHint, int hHint, boolean changed);

    public boolean forceFocus();

    public AccessibleProxy getAccessible();

    public ColorProxy getBackground();

    public ImageProxy getBackgroundImage();

    public int getBorderWidth();

    public RectangleProxy getBounds();

    public CursorProxy getCursor();

    public boolean getDragDetect();

    public boolean getEnabled();

    public FontProxy getFont();

    public ColorProxy getForeground();

    public Object getLayoutData();

    public PointProxy getLocation();

    public MenuProxy getMenu();

    public MonitorProxy getMonitor();

    public CompositeProxy getParent();

    public ShellProxy getShell();

    public PointProxy getSize();

    public String getToolTipText();

    public boolean getVisible();

    public boolean isActive();

    public boolean isEnabled();

    public boolean isFocusControl();

    public boolean isReparentable();

    public boolean isVisible();

    public void removeControlListener(ControlListenerProxy listener);

    public void removeFocusListener(FocusListenerProxy listener);

    public void removeHelpListener(HelpListenerProxy listener);

    public void removeKeyListener(KeyListenerProxy listener);

    public void removeMouseListener(MouseListenerProxy listener);

    public void removeMouseMoveListener(MouseMoveListenerProxy listener);

    public void removeMouseTrackListener(MouseTrackListenerProxy listener);

    public void removePaintListener(PaintListenerProxy listener);

    public void removeTraverseListener(TraverseListenerProxy listener);

    public void setBackground(ColorProxy color);

    public void setBounds(int x, int y, int width, int height);

    public void setBounds(RectangleProxy rect);

    public void setCapture(boolean capture);

    public void setCursor(CursorProxy cursor);

    public void setEnabled(boolean enabled);

    public void setFocus();

    public void setFont(FontProxy font);

    public void setForeground(ColorProxy color);

    public void setLayoutData(Object layoutData);

    public void setLocation(int x, int y);

    public void setLocation(PointProxy location);

    public void setMenu(MenuProxy menu);

    public boolean setParent(CompositeProxy parent);

    public void setRedraw(boolean redraw);

    public void setSize(int width, int height);

    public void setSize(PointProxy size);

    public void setToolTipText(String string);

    public void setVisible(boolean visible);

    public PointProxy toControl(int x, int y);

    public PointProxy toControl(PointProxy point);

    public PointProxy toDisplay(int x, int y);

    public PointProxy toDisplay(PointProxy point);

    public boolean traverse(int traversal);

    public void update();
}