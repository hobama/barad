package edu.utexas.barad.agent.swt.proxy.widgets;

import edu.utexas.barad.agent.swt.proxy.graphics.RectangleProxy;

/**
 * University of Texas at Austin
 * Barad Project, Jul 6, 2007
 *
 *
 */
public interface TabFolderProxy extends CompositeProxy {
    public RectangleProxy getClientArea();

    public TabItemProxy getItem(int index);

    public int getItemCount();

    public TabItemProxy[] getItems();

    public TabItemProxy[] getSelection();

    public int getSelectionIndex();

    public int indexOf(TabItemProxy item);
}