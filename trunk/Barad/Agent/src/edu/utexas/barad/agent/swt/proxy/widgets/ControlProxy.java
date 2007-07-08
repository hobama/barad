package edu.utexas.barad.agent.swt.proxy.widgets;

import edu.utexas.barad.agent.swt.proxy.graphics.*;

/**
 * University of Texas at Austin
 * Barad Project, Jul 6, 2007
 *
 *
 */
public interface ControlProxy extends WidgetProxy {
    public PointProxy computeSize(int wHint, int hHint);

    public PointProxy computeSize(int wHint, int hHint, boolean changed);

    public boolean forceFocus();

    public ColorProxy getBackground();

    public ImageProxy getBackgroundImage();

    public int getBorderWidth();

    public RectangleProxy getBounds();

    public CursorProxy getCursor();

    public boolean getDragDetect();

    public boolean getEnabled();

    public FontProxy getFont();

    public ColorProxy getForeground();

    public PointProxy getLocation();

    public MenuProxy getMenu();

    public MonitorProxy getMonitor();

    public CompositeProxy getParent();

    public ShellProxy getShell();

    public PointProxy getSize();

    public String getToolTipText();

    public boolean getVisible();

    public boolean isEnabled();

    public boolean isFocusControl();

    public boolean isReparentable();

    public boolean isVisible();

    public void update();
}