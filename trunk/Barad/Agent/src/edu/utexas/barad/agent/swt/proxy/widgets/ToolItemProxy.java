package edu.utexas.barad.agent.swt.proxy.widgets;

import edu.utexas.barad.agent.swt.proxy.graphics.ImageProxy;
import edu.utexas.barad.agent.swt.proxy.graphics.RectangleProxy;

/**
 * University of Texas at Austin
 * Barad Project, Jul 6, 2007
 *
 *
 */
public interface ToolItemProxy extends ItemProxy {
    public RectangleProxy getBounds();

    public ControlProxy getControl();

    public ImageProxy getDisabledImage();

    public boolean getEnabled();

    public ImageProxy getHotImage();

    public ToolBarProxy getParent();

    public boolean getSelection();

    public String getToolTipText();

    public int getWidth();

    public boolean isEnabled();
}