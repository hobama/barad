package edu.utexas.barad.agent.swt.proxy.graphics;

import edu.utexas.barad.agent.AgentUtils;
import edu.utexas.barad.agent.swt.proxy.SWTProxyFactory;
import edu.utexas.barad.agent.swt.proxy.widgets.DisplayProxy;

/**
 * University of Texas at Austin
 * Barad Project, Jul 25, 2007
 */
public interface GCProxy extends ResourceProxy {
    public void copyArea(ImageProxy image, int x, int y);

    public void dispose();

    public static class Factory {
        public static GCProxy newGCProxy(DisplayProxy display) {
            Class drawableClass = AgentUtils.swtClassForName(SWTProxyFactory.ORG_ECLIPSE_SWT_GRAPHICS_DRAWABLE);
            return (GCProxy) SWTProxyFactory.getInstance().newProxy(AgentUtils.swtClassForName(SWTProxyFactory.ORG_ECLIPSE_SWT_GRAPHICS_GC), new Class[]{drawableClass}, new Object[]{display});
        }
    }
}