package edu.utexas.barad.agent.swt.proxy.graphics;

import edu.utexas.barad.agent.swt.proxy.SWTProxyMarker;

/**
 * University of Texas at Austin
 * Barad Project, Jul 6, 2007
 */
public interface FontDataProxy extends SWTProxyMarker {
    public int getHeight();

    public String getLocale();

    public String getName();

    public int getStyle();
}