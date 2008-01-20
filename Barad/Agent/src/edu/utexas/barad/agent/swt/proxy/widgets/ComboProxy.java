package edu.utexas.barad.agent.swt.proxy.widgets;

import edu.utexas.barad.agent.swt.proxy.graphics.PointProxy;

/**
 * University of Texas at Austin
 * Barad Project, Jul 6, 2007
 *
 *
 */
public interface ComboProxy extends CompositeProxy {
    public String getItem(int index);

    public int getItemCount();

    public int getItemHeight();

    public String[] getItems();

    public int getOrientation();

    public PointProxy getSelection();

    public int getSelectionIndex();

    public String getText();

    public int getTextHeight();

    public int getTextLimit();

    public int getVisibleItemCount();

    public int indexOf(String string);

    public int indexOf(String string, int start);

    public void setText(String text);
}