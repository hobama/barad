package edu.utexas.barad.agent.swt.proxy.widgets;

import edu.utexas.barad.agent.swt.proxy.graphics.FontProxy;

/**
 * University of Texas at Austin
 * Barad Project, Jul 6, 2007
 */
public interface CanvasProxy extends CompositeProxy {
    public CaretProxy getCaret();

    public void scroll(int destX, int destY, int x, int y, int width, int height, boolean all);

    public void setCaret(CaretProxy caret);

    public void setFont(FontProxy font);
}