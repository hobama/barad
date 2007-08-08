package edu.utexas.barad.agent.swt.proxy.widgets;

import edu.utexas.barad.agent.swt.proxy.graphics.ImageProxy;
import edu.utexas.barad.agent.swt.proxy.graphics.PointProxy;
import edu.utexas.barad.agent.swt.proxy.graphics.RectangleProxy;

/**
 * University of Texas at Austin
 * Barad Project, Jul 6, 2007
 *
 *
 */
public interface DecorationsProxy extends CanvasProxy {
    public RectangleProxy computeTrim(int x, int y, int width, int height);

    public RectangleProxy getBounds();

    public RectangleProxy getClientArea();

    public ButtonProxy getDefaultButton();

    public ImageProxy getImage();

    public ImageProxy[] getImages();

    public PointProxy getLocation();

    public boolean getMaximized();

    public MenuProxy getMenuBar();

    public boolean getMinimized();

    public PointProxy getSize();

    public String getText();

    public boolean isReparentable();

    public void setMinimized(boolean minimized);

    public void setMaximized(boolean maximized);
}