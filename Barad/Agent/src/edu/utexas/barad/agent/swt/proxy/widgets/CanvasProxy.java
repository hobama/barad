package edu.utexas.barad.agent.swt.proxy.widgets;

/**
 * University of Texas at Austin
 * Barad Project, Jul 6, 2007
 */
public interface CanvasProxy extends CompositeProxy {
    public CaretProxy getCaret();

    public void scroll(int destX, int destY, int x, int y, int width, int height, boolean all);
}