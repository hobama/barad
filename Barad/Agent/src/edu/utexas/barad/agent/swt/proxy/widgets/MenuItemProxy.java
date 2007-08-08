package edu.utexas.barad.agent.swt.proxy.widgets;

import edu.utexas.barad.agent.swt.proxy.graphics.RectangleProxy;

/**
 * University of Texas at Austin
 * Barad Project, Jul 6, 2007
 */
public interface MenuItemProxy extends ItemProxy {
    public int getAccelerator();

    public RectangleProxy getBounds();

    public boolean getEnabled();

    public MenuProxy getMenu();

    public MenuProxy getParent();

    public boolean getSelection();

    public boolean isEnabled();
}