package edu.utexas.barad.agent.swt.proxy.widgets;

import edu.utexas.barad.agent.swt.proxy.graphics.RectangleProxy;

/**
 * University of Texas at Austin
 * Barad Project, Jul 6, 2007
 *
 *
 */
public interface ScrollableProxy extends ControlProxy {
    public RectangleProxy computeTrim(int x, int y, int width, int height);

    public RectangleProxy getClientArea();

    public ScrollBarProxy getHorizontalBar();

    public ScrollBarProxy getVerticalBar();
}