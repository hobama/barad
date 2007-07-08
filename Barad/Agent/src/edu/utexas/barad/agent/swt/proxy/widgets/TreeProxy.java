package edu.utexas.barad.agent.swt.proxy.widgets;

import edu.utexas.barad.agent.swt.proxy.graphics.PointProxy;

/**
 * University of Texas at Austin
 * Barad Project, Jul 6, 2007
 *
 *
 */
public interface TreeProxy extends CompositeProxy {
    public PointProxy computeSize(int wHint, int hHint, boolean changed);

    public TreeColumnProxy getColumn(int index);

    public int getColumnCount();

    public int[] getColumnOrder();

    public TreeColumnProxy[] getColumns();

    public int getGridLineWidth();

    public int getHeaderHeight();

    public boolean getHeaderVisible();

    public TreeItemProxy getItem(int index);

    public TreeItemProxy getItem(PointProxy point);

    public int getItemCount();

    public int getItemHeight();

    public TreeItemProxy[] getItems();

    public boolean getLinesVisible();

    public TreeItemProxy getParentItem();

    public TreeItemProxy[] getSelection();

    public int getSelectionCount();

    public TreeColumnProxy getSortColumn();

    public int getSortDirection();

    public TreeItemProxy getTopItem();

    public int indexOf(TreeColumnProxy column);

    public int indexOf(TreeItemProxy item);
}