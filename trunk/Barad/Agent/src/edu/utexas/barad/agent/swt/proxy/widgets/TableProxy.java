package edu.utexas.barad.agent.swt.proxy.widgets;

import edu.utexas.barad.agent.swt.proxy.graphics.PointProxy;

/**
 * University of Texas at Austin
 * Barad Project, Jul 6, 2007
 *
 *
 */
public interface TableProxy extends CompositeProxy {
    public TableColumnProxy getColumn(int index);

    public int getColumnCount();

    public int[] getColumnOrder();

    public TableColumnProxy[] getColumns();

    public int getGridLineWidth();

    public int getHeaderHeight();

    public boolean getHeaderVisible();

    public TableItemProxy getItem(int index);

    public TableItemProxy getItem(PointProxy point);

    public int getItemCount();

    public int getItemHeight();

    public TableItemProxy[] getItems();

    public boolean getLinesVisible();

    public TableItemProxy[] getSelection();

    public int getSelectionCount();

    public int getSelectionIndex();

    public int[] getSelectionIndices();

    public TableColumnProxy getSortColumn();

    public int getSortDirection();

    public int getTopIndex();

    public int indexOf(TableColumnProxy column);

    public int indexOf(TableItemProxy item);

    public boolean isSelected(int index);
}