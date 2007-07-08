package edu.utexas.barad.agent.swt.proxy.widgets;

import edu.utexas.barad.agent.swt.proxy.graphics.*;

/**
 * University of Texas at Austin
 * Barad Project, Jul 3, 2007
 *
 *
 */
public interface DisplayProxy extends DeviceProxy {
    public void asyncExec(Runnable runnable);

    public void disposeExec(Runnable runnable);

    public DisplayProxy findDisplay(Thread thread);

    public WidgetProxy findWidget(int handle);

    public WidgetProxy findWidget(int handle, int id);

    public WidgetProxy findWidget(WidgetProxy widget, int id);

    public ShellProxy getActiveShell();

    public RectangleProxy getBounds();

    public RectangleProxy getClientArea();

    public DisplayProxy getCurrent();

    public ControlProxy getCursorControl();

    public PointProxy getCursorLocation();

    public PointProxy[] getCursorSizes();

    public Object getData();

    public Object getData(String key);

    public DisplayProxy getDefault();

    public int getDismissalAlignment();

    public int getDoubleClickTime();

    public ControlProxy getFocusControl();

    public boolean getHighContrast();

    public int getIconDepth();

    public PointProxy[] getIconSizes();

    public MonitorProxy[] getMonitors();

    public MonitorProxy getPrimaryMonitor();

    public ShellProxy[] getShells();

    public Thread getSyncThread();

    public ColorProxy getSystemColor(int id);

    public CursorProxy getSystemCursor(int id);

    public FontProxy getSystemFont();

    public ImageProxy getSystemImage(int id);

    public TrayProxy getSystemTray();

    public Thread getThread();

    public boolean post(EventProxy event);

    public boolean readAndDispatch();

    public void setData(Object data);

    public void setData(String key, Object value);

    public boolean sleep();

    public void syncExec(Runnable runnable);

    public void timerExec(int milliseconds, Runnable runnable);

    public void wake();
}