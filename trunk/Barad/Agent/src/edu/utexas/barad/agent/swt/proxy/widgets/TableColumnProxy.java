package edu.utexas.barad.agent.swt.proxy.widgets;

/**
 * University of Texas at Austin
 * Barad Project, Jul 6, 2007
 *
 *
 */
public interface TableColumnProxy extends ItemProxy {
    public int getAlignment();

    public boolean getMoveable();

    public TableProxy getParent();

    public boolean getResizable();

    public String getToolTipText();

    public int getWidth();
}