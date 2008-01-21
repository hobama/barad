package edu.utexas.barad.agent.swt.proxy.widgets;

import edu.utexas.barad.agent.swt.proxy.graphics.ImageProxy;

/**
 * University of Texas at Austin
 * Barad Project, Jul 6, 2007
 */
public interface ButtonProxy extends ControlProxy {
    public int getAlignment();

    public ImageProxy getImage();

    public boolean getSelection();

    public String getText();

    public void click();
}