package edu.utexas.barad.agent.swt.proxy.widgets;

/**
 * University of Texas at Austin
 * Barad Project, Jul 4, 2007
 *
 *
 */
public interface WidgetProxy {
    public Object getData(String key);

    public DisplayProxy getDisplay();

    public int getStyle();
    
    public boolean isDisposed();

    public void setData(String key, Object value);
}