package edu.utexas.barad.agent.swt.proxy.graphics;

/**
 * University of Texas at Austin
 * Barad Project, Jul 6, 2007
 */
public interface RegionProxy extends ResourceProxy {
    public boolean contains(int x, int y);

    public boolean contains(PointProxy pt);

    public RectangleProxy getBounds();

    public boolean intersects(int x, int y, int width, int height);

    public boolean intersects(RectangleProxy rect);

    public boolean isDisposed();

    public boolean isEmpty();
}