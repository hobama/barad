package edu.utexas.barad.agent.swt.proxy.graphics;

import edu.utexas.barad.agent.AgentUtils;
import edu.utexas.barad.agent.swt.proxy.SWTProxyFactory;
import edu.utexas.barad.agent.swt.proxy.SWTProxyMarker;

/**
 * University of Texas at Austin
 * Barad Project, Jul 6, 2007
 */
public interface RectangleProxy extends SWTProxyMarker {
    public int __fieldGetx();

    public void __fieldSetx(int x);

    public int __fieldGety();

    public void __fieldSety(int y);

    public int __fieldGetwidth();

    public void __fieldSetwidth(int width);

    public int __fieldGetheight();

    public void __fieldSetheight(int height);

    public boolean contains(int x, int y);

    public boolean contains(PointProxy pt);

    public static class Factory {
        public static RectangleProxy newRectangleProxy(int x, int y, int width, int height) {
            return (RectangleProxy) SWTProxyFactory.getInstance().newProxy(AgentUtils.swtClassForName(SWTProxyFactory.ORG_ECLIPSE_SWT_GRAPHICS_RECTANGLE), new Class[]{int.class, int.class, int.class, int.class}, new Object[]{x, y, width, height});
        }
    }
}