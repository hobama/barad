package edu.utexas.barad.agent.swt.proxy.graphics;

import edu.utexas.barad.agent.swt.proxy.SWTProxyMarker;

/**
 * University of Texas at Austin
 * Barad Project, Jul 6, 2007
 */
public interface ResourceProxy extends SWTProxyMarker {
    public boolean isDisposed();
    
    public DeviceProxy getDevice();
}