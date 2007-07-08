package edu.utexas.barad.agent.swt.proxy.widgets;

import edu.utexas.barad.agent.swt.proxy.graphics.PointProxy;

/**
 * University of Texas at Austin
 * Barad Project, Jul 6, 2007
 *
 *
 */
public interface ToolBarProxy extends CompositeProxy {
    public ToolItemProxy getItem(int index);

    public ToolItemProxy getItem(PointProxy point);

    public int getItemCount();

    public ToolItemProxy[] getItems();

    public int getRowCount();

    public int indexOf(ToolItemProxy item);
}