package edu.utexas.barad.agent.swt.proxy.graphics;

/**
 * University of Texas at Austin
 * Barad Project, Jul 6, 2007
 *
 *
 */
public interface ColorProxy extends ResourceProxy {
    public int getBlue();

    public int getGreen();
    
    public int getRed();

    public RGBProxy getRGB();
}