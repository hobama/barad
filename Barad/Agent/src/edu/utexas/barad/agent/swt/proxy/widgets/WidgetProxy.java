package edu.utexas.barad.agent.swt.proxy.widgets;

import edu.utexas.barad.agent.swt.proxy.events.DisposeListenerProxy;
import edu.utexas.barad.agent.swt.proxy.SWTProxyMarker;

/**
 * University of Texas at Austin
 * Barad Project, Jul 4, 2007
 */
public interface WidgetProxy extends SWTProxyMarker {
    public void addDisposeListener(DisposeListenerProxy listener);

    public void addListener(int eventType, ListenerProxy listener);

    public Object getData();
    
    public Object getData(String key);

    public DisplayProxy getDisplay();

    public int getStyle();
    
    public boolean isDisposed();

    public boolean isListening(int eventType);

    public void notifyListeners(int eventType, EventProxy event);

    public void removeDisposeListener(DisposeListenerProxy listener); 

    public void removeListener(int eventType, ListenerProxy listener);

    public void setData(Object data);
    
    public void setData(String key, Object value);
}