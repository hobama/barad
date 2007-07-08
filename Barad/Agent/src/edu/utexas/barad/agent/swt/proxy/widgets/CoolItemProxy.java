package edu.utexas.barad.agent.swt.proxy.widgets;

import edu.utexas.barad.agent.swt.proxy.graphics.PointProxy;
import edu.utexas.barad.agent.swt.proxy.graphics.RectangleProxy;

/**
 * University of Texas at Austin
 * Barad Project, Jul 6, 2007
 *
 *
 */
public interface CoolItemProxy extends ItemProxy {
    public RectangleProxy getBounds();

    public ControlProxy getControl();

    public PointProxy getMinimumSize();

    public CoolBarProxy getParent();

    public PointProxy getPreferredSize();

    public PointProxy getSize();
}