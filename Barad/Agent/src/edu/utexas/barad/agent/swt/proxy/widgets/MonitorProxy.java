package edu.utexas.barad.agent.swt.proxy.widgets;

import edu.utexas.barad.agent.swt.proxy.graphics.RectangleProxy;
import edu.utexas.barad.agent.swt.proxy.SWTProxyMarker;

/**
 * University of Texas at Austin
 * Barad Project, Jul 6, 2007
 */
public interface MonitorProxy extends SWTProxyMarker {
    public RectangleProxy getBounds();

    public RectangleProxy getClientArea();
}