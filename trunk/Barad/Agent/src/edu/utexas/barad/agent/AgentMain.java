package edu.utexas.barad.agent;

import edu.utexas.barad.agent.exceptions.AgentRuntimeException;
import edu.utexas.barad.agent.exceptions.WidgetNotFoundException;
import edu.utexas.barad.agent.proxy.IProxyInvocationHandler;
import edu.utexas.barad.agent.swt.Displays;
import edu.utexas.barad.agent.swt.Hierarchy;
import edu.utexas.barad.agent.swt.WidgetValueBuilder;
import edu.utexas.barad.agent.swt.proxy.widgets.DisplayProxy;
import edu.utexas.barad.agent.swt.proxy.widgets.WidgetProxy;
import edu.utexas.barad.common.swt.GUID;
import edu.utexas.barad.common.swt.WidgetInfo;
import edu.utexas.barad.common.swt.WidgetValues;
import org.apache.log4j.Logger;

import java.lang.reflect.Proxy;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * University of Texas at Austin
 * Barad Project, Jul 1, 2007
 */
public class AgentMain extends UnicastRemoteObject implements IAgent {
    public static final int AGENT_RMI_PORT = 9000;
    public static final String AGENT_RMI_NAME = "BARAD-CLIENT";

    private static final Logger logger = Logger.getLogger(AgentMain.class);

    private Registry registry;
    private Map<GUID, Object> proxyObjectCache = new HashMap<GUID, Object>();
    private WidgetInfo[] cachedHierarchy;

    public AgentMain() throws RemoteException {
        startRegistry(AGENT_RMI_PORT);
        registry.rebind(AGENT_RMI_NAME, this);
    }

    public WidgetInfo[] getWidgetHierarchy(boolean rebuild) throws RemoteException {
        // Do we have a cached version?  If so, just return it.
        if (!rebuild && cachedHierarchy != null) {
            return cachedHierarchy;
        }

        // Clear the caches.
        proxyObjectCache.clear();
        cachedHierarchy = null;

        // Build the hierarchy.
        List<DisplayProxy> displays = Displays.getDisplays();
        List<WidgetInfo> roots = new ArrayList<WidgetInfo>();
        for (DisplayProxy display : displays) {
            Hierarchy hierarchy = new Hierarchy(display);
            WidgetInfo displayInfo = hierarchy.buildHierarchy(proxyObjectCache);
            proxyObjectCache.put(displayInfo.getGuid(), display);
            roots.add(displayInfo);
        }
        cachedHierarchy = roots.toArray(new WidgetInfo[0]);
        return cachedHierarchy;
    }

    public WidgetValues getWidgetValues(GUID guid) throws RemoteException {
        Object proxy = proxyObjectCache.get(guid);
        if (proxy == null) {
            throw new WidgetNotFoundException("Widget doesn't exist", guid);
        }
        if (!Proxy.isProxyClass(proxy.getClass())) {
            throw new AgentRuntimeException("Cached object is not a proxy.");
        }

        DisplayProxy display;
        if (proxy instanceof DisplayProxy) {
            display = (DisplayProxy) proxy;
        } else {
            WidgetProxy widgetProxy = (WidgetProxy) proxy;
            display = widgetProxy.getDisplay();
        }

        IProxyInvocationHandler invocationHandler = (IProxyInvocationHandler) Proxy.getInvocationHandler(proxy);
        Object actualInstance = invocationHandler.getActualInstance();
        if (actualInstance == null) {
            // Widget has been disposed.
            throw new WidgetNotFoundException("Widget no longer exists.", guid);
        }

        WidgetValues widgetValues = new WidgetValues();
        widgetValues.setPropertyValues(WidgetValueBuilder.buildPropertyValues(actualInstance, display));
        widgetValues.setFieldValues(WidgetValueBuilder.buildFieldValues(actualInstance, display));
        return widgetValues;
    }

    public static void main(String[] args) {
        try {
            new AgentMain();
        } catch (RemoteException e) {
            logger.error("Couldn't instantiate AgentMain object.", e);
            throw new AgentRuntimeException("Couldnt' instantiate AgentMain object.");
        }
        logger.info("Barad agent is running.");
    }

    private void startRegistry(int port) {
        try {
            registry = LocateRegistry.createRegistry(port);
        } catch (RemoteException e) {
            logger.debug("Couldn't create registry, port=" + port);
            try {
                registry = LocateRegistry.getRegistry(port);
            } catch (RemoteException e2) {
                logger.debug("Couldn't get existing registry, port=" + port);
            }
            if (registry == null) {
                logger.error("Couldn't start RMI registry, port=" + port);
                throw new AgentRuntimeException("Couldn't start RMI registry.");
            }
        }
    }
}