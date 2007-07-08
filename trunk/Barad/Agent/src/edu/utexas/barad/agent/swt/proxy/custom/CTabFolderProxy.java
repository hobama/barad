package edu.utexas.barad.agent.swt.proxy.custom;

import edu.utexas.barad.agent.swt.proxy.graphics.ColorProxy;
import edu.utexas.barad.agent.swt.proxy.graphics.PointProxy;
import edu.utexas.barad.agent.swt.proxy.graphics.RectangleProxy;
import edu.utexas.barad.agent.swt.proxy.widgets.CompositeProxy;
import edu.utexas.barad.agent.swt.proxy.widgets.ControlProxy;

/**
 * University of Texas at Austin
 * Barad Project, Jul 6, 2007
 *
 *
 */
public interface CTabFolderProxy extends CompositeProxy {
    public boolean getBorderVisible();

    public RectangleProxy getClientArea();

    public CTabItemProxy getItem(int index);

    public CTabItemProxy getItem(PointProxy pt);

    public int getItemCount();

    public CTabItemProxy[] getItems();

    public boolean getMaximized();

    public boolean getMaximizeVisible();

    public boolean getMinimized();

    public boolean getMinimizeVisible();

    public int getMinimumCharacters();

    public boolean getMRUVisible();

    public CTabItemProxy getSelection();

    public ColorProxy getSelectionBackground();

    public ColorProxy getSelectionForeground();

    public int getSelectionIndex();

    public boolean getSimple();

    public boolean getSingle();

    public int getStyle();

    public int getTabHeight();

    public int getTabPosition();

    public ControlProxy getTopRight();

    public boolean getUnselectedCloseVisible();

    public boolean getUnselectedImageVisible();

    public int indexOf(CTabItemProxy item);
}