package edu.utexas.barad.agent.swt.proxy.widgets;

/**
 * University of Texas at Austin
 * Barad Project, Jul 6, 2007
 *
 *
 */
public interface MenuProxy extends WidgetProxy {
    public MenuItemProxy getDefaultItem();

    public boolean getEnabled();

    public MenuItemProxy getItem(int index);

    public int getItemCount();

    public MenuItemProxy[] getItems();

    public MenuItemProxy getParentItem();

    public MenuProxy getParentMenu();

    public ShellProxy getShell();

    public boolean getVisible();

    public int indexOf(MenuItemProxy item);

    public boolean isEnabled();

    public boolean isVisible();
}