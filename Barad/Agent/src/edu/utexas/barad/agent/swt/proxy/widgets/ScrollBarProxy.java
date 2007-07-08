package edu.utexas.barad.agent.swt.proxy.widgets;

import edu.utexas.barad.agent.swt.proxy.graphics.PointProxy;

/**
 * University of Texas at Austin
 * Barad Project, Jul 6, 2007
 *
 *
 */
public interface ScrollBarProxy extends WidgetProxy {
    public boolean getEnabled();

    public int getIncrement();

    public int getMaximum();

    public int getMinimum();

    public int getPageIncrement();

    public ScrollableProxy getParent();

    public int getSelection();

    public PointProxy getSize();

    public int getThumb();

    public boolean getVisible();

    public boolean isEnabled();

    public boolean isVisible();
}