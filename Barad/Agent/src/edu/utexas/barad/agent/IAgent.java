package edu.utexas.barad.agent;

import edu.utexas.barad.agent.testcase.InitialStateStrategy;
import edu.utexas.barad.agent.testcase.WidgetFilterStrategy;
import edu.utexas.barad.common.swt.WidgetInfo;
import edu.utexas.barad.common.swt.WidgetValues;
import edu.utexas.barad.common.testcase.TestCase;
import edu.utexas.barad.common.testcase.ExecutionState;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * University of Texas at Austin
 * Barad Project, Jul 1, 2007
 */
public interface IAgent extends Remote {
    public WidgetInfo getWidgetHierarchy(boolean rebuild) throws RemoteException;

    public WidgetValues getWidgetValues(WidgetInfo widgetInfo) throws RemoteException;

    public String getProcessCommandLine() throws RemoteException;

    public int getProcessID() throws RemoteException;

    public void startGenerateTestCases(String widgetFilterStrategyClassName, String initialStateStrategyClassName) throws RemoteException;

    public void stopGenerateTestCases() throws RemoteException;

    public void pauseGenerateTestCases() throws RemoteException;

    public void continueGenerateTestCases() throws RemoteException;

    public ExecutionState getExecutionState() throws RemoteException;

    public TestCase[] getGeneratedTestCases() throws RemoteException;

    public TestCase[] getExecutingTestCases() throws RemoteException;

    public TestCase getCurrentTestCase() throws RemoteException;

    public String getThrowableStackTrace() throws RemoteException;
}