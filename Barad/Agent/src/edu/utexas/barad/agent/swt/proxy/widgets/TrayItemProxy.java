package edu.utexas.barad.agent.swt.proxy.widgets;

/**
 * University of Texas at Austin
 * Barad Project, Jul 7, 2007
 *
 *
 */
public interface TrayItemProxy extends ItemProxy {
    public TrayProxy getParent();

    public String getToolTipText();

    public boolean getVisible();
}