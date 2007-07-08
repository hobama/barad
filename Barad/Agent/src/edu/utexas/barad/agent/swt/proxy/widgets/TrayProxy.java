package edu.utexas.barad.agent.swt.proxy.widgets;

/**
 * University of Texas at Austin
 * Barad Project, Jul 7, 2007
 *
 *
 */
public interface TrayProxy extends WidgetProxy {
    public TrayItemProxy getItem(int index);

    public int getItemCount();

    public TrayItemProxy[] getItems();
}