package edu.utexas.barad.agent.swt.proxy.graphics;

/**
 * University of Texas at Austin
 * Barad Project, Jul 6, 2007
 *
 *
 */
public interface DeviceProxy {
    public RectangleProxy getBounds();

    public RectangleProxy getClientArea();

    public int getDepth();

    public PointProxy getDPI();

    public FontDataProxy[] getFontList(String faceName, boolean scalable);

    public ColorProxy getSystemColor(int id);

    public FontProxy getSystemFont();

    public boolean isDisposed();
}