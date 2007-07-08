package edu.utexas.barad.agent.swt.proxy.widgets;

import edu.utexas.barad.agent.swt.proxy.graphics.PointProxy;
import edu.utexas.barad.agent.swt.proxy.graphics.RectangleProxy;
import edu.utexas.barad.agent.swt.proxy.graphics.RegionProxy;

/**
 * University of Texas at Austin
 * Barad Project, Jul 4, 2007
 *
 *
 */
public interface ShellProxy extends DecorationsProxy {
    public void forceActive();

    public RectangleProxy getBounds();

    public boolean getEnabled();

    public PointProxy getLocation();

    public PointProxy getMinimumSize();

    public RegionProxy getRegion();

    public ShellProxy getShell();

    public ShellProxy[] getShells();

    public PointProxy getSize();

    public boolean isEnabled();

    boolean isVisible();
}