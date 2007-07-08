package edu.utexas.barad.agent.swt.proxy.widgets;

/**
 * University of Texas at Austin
 * Barad Project, Jul 6, 2007
 *
 *
 */
public interface TreeColumnProxy extends ItemProxy {
    public int getAlignment();

    public boolean getMoveable();

    public TreeProxy getParent();

    public boolean getResizable();

    public String getToolTipText();

    public int getWidth();
}