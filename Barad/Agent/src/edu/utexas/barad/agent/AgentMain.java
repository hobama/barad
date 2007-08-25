package edu.utexas.barad.agent;

import edu.utexas.barad.agent.exceptions.AgentRuntimeException;
import edu.utexas.barad.agent.testcases.GenerateTestCases;
import edu.utexas.barad.agent.testcases.WidgetInfoPredicate;
import edu.utexas.barad.agent.swt.WidgetHierarchy;
import edu.utexas.barad.agent.swt.WidgetValueBuilder;
import edu.utexas.barad.common.swt.GUID;
import edu.utexas.barad.common.swt.WidgetInfo;
import edu.utexas.barad.common.swt.WidgetValues;
import edu.utexas.barad.common.testcase.TestCase;
import org.apache.log4j.Logger;

import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

/**
 * University of Texas at Austin
 * Barad Project, Jul 1, 2007
 */
public class AgentMain extends UnicastRemoteObject implements IAgent {
    public static final int AGENT_RMI_PORT = 9000;
    public static final String AGENT_RMI_NAME = "BARAD-AGENT";

    private static final Logger logger = Logger.getLogger(AgentMain.class);

    private String processCommandLine;
    private int processID;
    private Registry registry;
    private WidgetHierarchy widgetHierarchy = new WidgetHierarchy();

    public AgentMain(String processCommandLine, int processID) throws RemoteException {
        this.processCommandLine = processCommandLine;
        this.processID = processID;

        startRegistry(AGENT_RMI_PORT);

        GUID guid = new GUID();
        final String agentID = AGENT_RMI_NAME + "-" + guid.toString();

        Runtime.getRuntime().addShutdownHook(new Thread("BaradShutdownHookThread") {
            @Override
            public void run() {
                if (registry != null) {
                    try {
                        registry.unbind(agentID);
                        logger.info("Barad agent unbound from RMI registry.");
                    } catch (Exception e) {
                        logger.error("Couldn't unbind agent from RMI registry, ID=" + agentID, e);
                    }
                }
            }
        });

        try {
            registry.bind(agentID, this);
        } catch (AlreadyBoundException e) {
            throw new AgentRuntimeException("Agent ID already exists in RMI registry, ID=" + agentID, e);
        }
    }

    public WidgetInfo getWidgetHierarchy(boolean rebuild) throws RemoteException {
        return widgetHierarchy.getWidgetHierarchy(rebuild);
    }

    public WidgetValues getWidgetValues(WidgetInfo widgetInfo) throws RemoteException {
        return WidgetValueBuilder.getWidgetValues(widgetInfo, widgetHierarchy);
    }

    public TestCase[] generateTestCases(WidgetInfoPredicate[] predicates) throws RemoteException {
        return new GenerateTestCases(predicates).generate();
    }

    public static void main(String[] args) {
        String processCommandLine = args.length >= 1 ? args[0] : "Unknown";
        String processIDString = args.length >= 2 ? args[1] : "-1";

        int processID = -1;
        try {
            if (processIDString != null) {
                processID = Integer.parseInt(processIDString);
            }
        } catch (NumberFormatException e) {
            logger.error("Couldn't parse process ID, string=" + processIDString);
        }

        try {
            new AgentMain(processCommandLine, processID);
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

    public String getProcessCommandLine() throws RemoteException {
        return processCommandLine;
    }

    public int getProcessID() throws RemoteException {
        return processID;
    }
}