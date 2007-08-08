package edu.utexas.barad.agent.swt.proxy.widgets;

import edu.utexas.barad.agent.swt.proxy.graphics.ImageProxy;

/**
 * University of Texas at Austin
 * Barad Project, Jul 6, 2007
 *
 *
 */
public interface ItemProxy extends WidgetProxy {
    public ImageProxy getImage();

    public String getText();

    public void setImage(ImageProxy image);

    public void setText(String string);
}