package edu.utexas.barad.agent;

import edu.utexas.barad.agent.exceptions.AgentRuntimeException;
import edu.utexas.barad.agent.swt.Displays;
import edu.utexas.barad.agent.swt.Hierarchy;
import edu.utexas.barad.agent.swt.proxy.widgets.DisplayProxy;
import edu.utexas.barad.common.swt.ObjectInfo;
import org.apache.log4j.Logger;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

/**
 * University of Texas at Austin
 * Barad Project, Jul 1, 2007 
 */
public class AgentMain extends UnicastRemoteObject implements IAgent {
    public static final int AGENT_RMI_PORT = 9000;
    public static final String AGENT_RMI_NAME = "BARAD-CLIENT";

    private static final Logger logger = Logger.getLogger(AgentMain.class);

    private Registry registry;

    public AgentMain() throws RemoteException {
        startRegistry(AGENT_RMI_PORT);
        registry.rebind(AGENT_RMI_NAME, this);
    }

    public ObjectInfo[] getWidgetHierarchy() throws RemoteException {
        List<DisplayProxy> displays = Displays.getDisplays();
        List<ObjectInfo> roots = new ArrayList<ObjectInfo>();
        for (DisplayProxy display : displays) {
            Hierarchy hierarchy = new Hierarchy(display);
            ObjectInfo displayInfo = hierarchy.buildHierarchy();
            roots.add(displayInfo);
        }
        return roots.toArray(new ObjectInfo[0]);
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