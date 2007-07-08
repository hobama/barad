package edu.utexas.barad.agent.swt.proxy.widgets;

import edu.utexas.barad.agent.swt.proxy.graphics.FontProxy;
import edu.utexas.barad.agent.swt.proxy.graphics.ImageProxy;
import edu.utexas.barad.agent.swt.proxy.graphics.PointProxy;
import edu.utexas.barad.agent.swt.proxy.graphics.RectangleProxy;

/**
 * University of Texas at Austin
 * Barad Project, Jul 6, 2007
 */
public interface CaretProxy extends WidgetProxy {
    public RectangleProxy getBounds();

    public FontProxy getFont();

    public ImageProxy getImage();

    public PointProxy getLocation();

    public CanvasProxy getParent();

    public PointProxy getSize();

    public boolean getVisible();

    public boolean isVisible();
}