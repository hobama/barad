package edu.utexas.barad.agent.swt.proxy.widgets;

import edu.utexas.barad.agent.AgentUtils;
import edu.utexas.barad.agent.proxy.IProxyInvocationHandler;
import edu.utexas.barad.agent.swt.proxy.SWTProxyFactory;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * University of Texas at Austin
 * Barad Project, Jul 26, 2007
 */
public interface ListenerProxy {
    public interface Impl {
        public void handleEvent(EventProxy event);
    }

    public static class Factory {
        public static ListenerProxy newListenerProxy(final ListenerProxy.Impl listener) {
            Class listenerInterface = AgentUtils.swtClassForName(SWTProxyFactory.ORG_ECLIPSE_SWT_WIDGETS_LISTENER);
            Object actualInstance = Proxy.newProxyInstance(AgentUtils.getSWTClassLoader(), new Class[]{listenerInterface}, new InvocationHandler() {
                public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                    EventProxy eventProxy = (EventProxy) SWTProxyFactory.getInstance().newProxy(args[0]);
                    listener.handleEvent(eventProxy);
                    return null;
                }
            });
            ListenerProxy proxy = (ListenerProxy) SWTProxyFactory.getInstance().newProxy(listenerInterface);
            IProxyInvocationHandler invocationHandler = (IProxyInvocationHandler) Proxy.getInvocationHandler(proxy);
            invocationHandler.setActualInstance(actualInstance);
            return proxy;
        }
    }
}