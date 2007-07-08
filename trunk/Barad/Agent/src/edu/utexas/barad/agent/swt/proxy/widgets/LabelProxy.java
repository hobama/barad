package edu.utexas.barad.agent.swt.proxy.widgets;

import edu.utexas.barad.agent.swt.proxy.graphics.ImageProxy;

/**
 * University of Texas at Austin
 * Barad Project, Jul 6, 2007
 *
 *
 */
public interface LabelProxy extends ControlProxy {
    public int getAlignment();

    public ImageProxy getImage();

    public String getText();
}