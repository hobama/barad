package edu.utexas.barad.agent.swt.proxy.widgets;

import edu.utexas.barad.agent.swt.proxy.graphics.RectangleProxy;

/**
 * University of Texas at Austin
 * Barad Project, Jul 6, 2007
 *
 *
 */
public interface MenuProxy extends WidgetProxy {
    public RectangleProxy getBounds();
    
    public MenuItemProxy getDefaultItem();

    public boolean getEnabled();

    public MenuItemProxy getItem(int index);

    public int getItemCount();

    public MenuItemProxy[] getItems();

    public DecorationsProxy getParent(); 

    public MenuItemProxy getParentItem();

    public MenuProxy getParentMenu();

    public ShellProxy getShell();

    public boolean getVisible();

    public int indexOf(MenuItemProxy item);

    public boolean isEnabled();

    public boolean isVisible();

    public void setVisible(boolean visible);
}