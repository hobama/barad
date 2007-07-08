package edu.utexas.barad.agent.swt.proxy.widgets;

import edu.utexas.barad.agent.swt.proxy.graphics.PointProxy;

/**
 * University of Texas at Austin
 * Barad Project, Jul 6, 2007
 *
 *
 */
public interface CompositeProxy extends ScrollableProxy {
    public PointProxy computeSize(int wHint, int hHint, boolean changed);

    public int getBackgroundMode();

    public ControlProxy[] getChildren();

    public LayoutProxy getLayout();

    public boolean getLayoutDeferred();

    public ControlProxy[] getTabList();

    public boolean isLayoutDeferred();
}