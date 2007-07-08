package edu.utexas.barad.agent.swt.proxy.custom;

import edu.utexas.barad.agent.swt.proxy.graphics.FontProxy;
import edu.utexas.barad.agent.swt.proxy.graphics.ImageProxy;
import edu.utexas.barad.agent.swt.proxy.graphics.RectangleProxy;
import edu.utexas.barad.agent.swt.proxy.widgets.ControlProxy;
import edu.utexas.barad.agent.swt.proxy.widgets.ItemProxy;

/**
 * University of Texas at Austin
 * Barad Project, Jul 6, 2007
 *
 *
 */
public interface CTabItemProxy extends ItemProxy {
    public RectangleProxy getBounds();

    public ControlProxy getControl();

    public ImageProxy getDisabledImage();

    public FontProxy getFont();

    public CTabFolderProxy getParent();

    public String getToolTipText();

    public boolean isShowing();
}