package edu.utexas.barad.agent.swt.proxy.graphics;

import edu.utexas.barad.agent.AgentUtils;
import edu.utexas.barad.agent.swt.proxy.SWTProxyFactory;
import edu.utexas.barad.agent.swt.proxy.SWTProxyMarker;

/**
 * University of Texas at Austin
 * Barad Project, Jul 6, 2007
 */
public interface PointProxy extends SWTProxyMarker {
    public int __fieldGetx();

    public void __fieldSetx(int x);

    public int __fieldGety();

    public void __fieldSety(int y);

    public static class Factory {
        public static PointProxy newPointProxy(int x, int y) {
            return (PointProxy) SWTProxyFactory.getInstance().newProxy(AgentUtils.swtClassForName(SWTProxyFactory.ORG_ECLIPSE_SWT_GRAPHICS_POINT), new Class[]{int.class, int.class}, new Object[]{x, y});
        }
    }
}