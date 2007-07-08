package edu.utexas.barad.agent.swt.proxy.widgets;

/**
 * University of Texas at Austin
 * Barad Project, Jul 6, 2007
 *
 *
 */
public interface ListProxy extends ScrollableProxy {
    public int getFocusIndex();

    public String getItem(int index);

    public int getItemCount();

    public int getItemHeight();

    public String[] getItems();

    public String[] getSelection();

    public int getSelectionCount();

    public int getSelectionIndex();

    public int[] getSelectionIndices();

    public int getTopIndex();

    public int indexOf(String string);

    public int indexOf(String string, int start);

    public boolean isSelected(int index);
}