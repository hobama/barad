package edu.utexas.barad.agent.swt.proxy.widgets;

/**
 * University of Texas at Austin
 * Barad Project, Jul 7, 2007
 *
 *
 */
public interface ProgressBarProxy extends ControlProxy {
    public int getMaximum();

    public int getMinimum();

    public int getSelection();
}