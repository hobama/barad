package edu.utexas.barad.agent.swt.proxy.widgets;

import edu.utexas.barad.agent.swt.proxy.graphics.ColorProxy;
import edu.utexas.barad.agent.swt.proxy.graphics.FontProxy;
import edu.utexas.barad.agent.swt.proxy.graphics.ImageProxy;
import edu.utexas.barad.agent.swt.proxy.graphics.RectangleProxy;

/**
 * University of Texas at Austin
 * Barad Project, Jul 6, 2007
 *
 *
 */
public interface TableItemProxy extends ItemProxy {
    public ColorProxy getBackground();

    public ColorProxy getBackground(int index);

    public RectangleProxy getBounds();

    public RectangleProxy getBounds(int index);

    public boolean getChecked();

    public FontProxy getFont();

    public FontProxy getFont(int index);

    public ColorProxy getForeground();

    public ColorProxy getForeground(int index);

    public boolean getGrayed();

    public ImageProxy getImage();

    public ImageProxy getImage(int index);

    public RectangleProxy getImageBounds(int index);

    public int getImageIndent();

    public TableProxy getParent();

    public String getText();

    public String getText(int index);

    public RectangleProxy getTextBounds(int index);
}