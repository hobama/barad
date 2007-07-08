package edu.utexas.barad.agent.swt.proxy.widgets;

import edu.utexas.barad.agent.swt.proxy.graphics.PointProxy;

/**
 * University of Texas at Austin
 * Barad Project, Jul 6, 2007
 *
 *
 */
public interface CoolBarProxy extends CompositeProxy {
    public CoolItemProxy getItem(int index);

    public int getItemCount();

    public int[] getItemOrder();

    public CoolItemProxy[] getItems();

    public PointProxy[] getItemSizes();

    public boolean getLocked();

    public int[] getWrapIndices();

    public int indexOf(CoolItemProxy item);
}