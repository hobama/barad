package edu.utexas.barad.agent.swt.proxy.graphics;

import edu.utexas.barad.agent.AgentUtils;
import edu.utexas.barad.agent.proxy.IProxyInvocationHandler;
import edu.utexas.barad.agent.swt.proxy.SWTProxyFactory;

import java.lang.reflect.Proxy;

/**
 * University of Texas at Austin
 * Barad Project, Jul 6, 2007
 */
public interface ColorProxy extends ResourceProxy {
    public int getBlue();

    public int getGreen();

    public int getRed();

    public RGBProxy getRGB();

    public static class Factory {
        public static ColorProxy newColorProxy(DeviceProxy device, RGBProxy rgb) {
            Class deviceClass = AgentUtils.swtClassForName(SWTProxyFactory.ORG_ECLIPSE_SWT_GRAPHICS_DEVICE);
            Class rgbClass = AgentUtils.swtClassForName(SWTProxyFactory.ORG_ECLIPSE_SWT_GRAPHICS_RGB);
            Object deviceObject = ((IProxyInvocationHandler) Proxy.getInvocationHandler(device)).getActualInstance();
            Object rgbObject = ((IProxyInvocationHandler) Proxy.getInvocationHandler(rgb)).getActualInstance();
            return (ColorProxy) SWTProxyFactory.getInstance().newProxy(AgentUtils.swtClassForName(SWTProxyFactory.ORG_ECLIPSE_SWT_GRAPHICS_COLOR), new Class[]{deviceClass, rgbClass}, new Object[]{deviceObject, rgbObject});
        }
    }
}