package edu.utexas.barad.agent.swt.proxy.graphics;

import edu.utexas.barad.agent.swt.proxy.SWTProxyMarker;

/**
 * University of Texas at Austin
 * Barad Project, Jul 6, 2007
 */
public interface ImageDataProxy extends SWTProxyMarker {
    public PaletteDataProxy __fieldGetpalette();
    
    public int getAlpha(int x, int y);

    public void getAlphas(int x, int y, int getWidth, byte[] alphas, int startIndex);

    public int getPixel(int x, int y);

    public void getPixels(int x, int y, int getWidth, byte[] pixels, int startIndex);

    public void getPixels(int x, int y, int getWidth, int[] pixels, int startIndex);

    public RGBProxy[] getRGBs();

    public ImageDataProxy getTransparencyMask();

    public int getTransparencyType();

    public ImageDataProxy scaledTo(int width, int height);

    public void setAlpha(int x, int y, int alpha);

    public void setAlphas(int x, int y, int putWidth, byte[] alphas, int startIndex);

    public void setPixel(int x, int y, int pixelValue);

    public void setPixels(int x, int y, int putWidth, byte[] pixels, int startIndex);

    public void setPixels(int x, int y, int putWidth, int[] pixels, int startIndex);

    public ImageDataProxy clone() throws CloneNotSupportedException;
}