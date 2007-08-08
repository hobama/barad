package edu.utexas.barad.agent.swt.proxy.graphics;

import edu.utexas.barad.agent.swt.proxy.SWTProxyMarker;

/**
 * University of Texas at Austin
 * Barad Project, Jul 25, 2007
 */
public interface PaletteDataProxy extends SWTProxyMarker {
    public RGBProxy getRGB(int pixel);
}