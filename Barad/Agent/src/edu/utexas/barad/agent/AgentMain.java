package edu.utexas.barad.agent;

import edu.utexas.barad.agent.exceptions.AgentRuntimeException;
import edu.utexas.barad.agent.swt.WidgetHierarchy;
import edu.utexas.barad.agent.swt.WidgetValueBuilder;
import edu.utexas.barad.agent.testcase.*;
import edu.utexas.barad.common.swt.GUID;
import edu.utexas.barad.common.swt.WidgetInfo;
import edu.utexas.barad.common.swt.WidgetValues;
import edu.utexas.barad.common.testcase.ExecutionState;
import edu.utexas.barad.common.testcase.TestCase;
import org.apache.log4j.Logger;

import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

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
    private GenerateTestCases generateTestCases = new GenerateTestCases();

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

    public void startGenerateTestCases(String widgetFilterStrategyClassName, String initialStateStrategyClassName) throws RemoteException {
        Class clazz;
        WidgetFilterStrategy widgetFilterStrategy;
        try {
            clazz = Class.forName(widgetFilterStrategyClassName);
            widgetFilterStrategy = (WidgetFilterStrategy) clazz.getConstructor().newInstance();
        } catch (Exception e) {
            logger.debug("Exception in Class.forName().", e);
            logger.info("Couldn't create instance of filter strategy class, className=" + widgetFilterStrategyClassName);
            logger.info("Default widget filter strategy will be used: " + DefaultWidgetFilterStrategy.class.getName());
            widgetFilterStrategy = new DefaultWidgetFilterStrategy();
        }

        InitialStateStrategy initialStateStrategy;
        try {
            clazz = Class.forName(initialStateStrategyClassName);
            initialStateStrategy = (InitialStateStrategy) clazz.getConstructor().newInstance();
        } catch (Exception e) {
            logger.debug("Exception in Class.forName().", e);
            logger.info("Couldn't create instance of initial state strategy class, className=" + initialStateStrategyClassName);
            logger.info("Default initial state strategy will be used: " + DefaultInitialStateStrategy.class.getName());
            initialStateStrategy = new DefaultInitialStateStrategy();
        }

        generateTestCases.start(widgetFilterStrategy, initialStateStrategy);
    }

    public void stopGenerateTestCases() throws RemoteException {
        generateTestCases.stop();
    }

    public void pauseGenerateTestCases() throws RemoteException {
        generateTestCases.pause();
    }

    public void continueGenerateTestCases() throws RemoteException {
        generateTestCases.continue_();
    }

    public ExecutionState getExecutionState() throws RemoteException {
        return generateTestCases.getExecutionState();
    }

    public TestCase[] getGeneratedTestCases() throws RemoteException {
        List<TestCase> testCases = generateTestCases.getUniqueTestCases();
        return testCases != null ? testCases.toArray(new TestCase[0]) : new TestCase[0];
    }

    public TestCase[] getExecutingTestCases() throws RemoteException {
        List<TestCase> testCases = generateTestCases.getCandidateTestCases();
        return testCases != null ? testCases.toArray(new TestCase[0]) : new TestCase[0];
    }

    public TestCase getCurrentTestCase() throws RemoteException {
        return generateTestCases.getCurrentTestCase();
    }

    public String getThrowableStackTrace() throws RemoteException {
        Throwable throwable = generateTestCases.getThrowable();
        if (throwable != null) {
            return AgentUtils.getStackTrace(throwable);
        }
        return null;
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
            throw new AgentRuntimeException("Couldn't instantiate AgentMain object.");
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