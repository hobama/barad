package edu.utexas.barad.agent.swt.proxy.widgets;

/**
 * University of Texas at Austin
 * Barad Project, Jul 6, 2007
 *
 *
 */
public interface TabItemProxy extends ItemProxy {
    public ControlProxy getControl();

    public TabFolderProxy getParent();

    public String getToolTipText();
}