package edu.utexas.barad.agent.swt.proxy.graphics;

import edu.utexas.barad.agent.AgentUtils;
import edu.utexas.barad.agent.proxy.IProxyInvocationHandler;
import edu.utexas.barad.agent.swt.proxy.SWTProxyFactory;

import java.lang.reflect.Proxy;

/**
 * University of Texas at Austin
 * Barad Project, Jul 6, 2007
 */
public interface ImageProxy extends ResourceProxy {
    public void dispose(); 

    public ColorProxy getBackground();

    public RectangleProxy getBounds();

    public ImageDataProxy getImageData();

    public static class Factory {
        public static ImageProxy newImageProxy(DeviceProxy device, int width, int height) {
            Class deviceClass = AgentUtils.swtClassForName(SWTProxyFactory.ORG_ECLIPSE_SWT_GRAPHICS_DEVICE);
            Object actualInstance = ((IProxyInvocationHandler) Proxy.getInvocationHandler(device)).getActualInstance();
            return (ImageProxy) SWTProxyFactory.getInstance().newProxy(AgentUtils.swtClassForName(SWTProxyFactory.ORG_ECLIPSE_SWT_GRAPHICS_IMAGE), new Class[]{deviceClass, Integer.class, Integer.class}, new Object[]{actualInstance, width, height});
        }
    }
}