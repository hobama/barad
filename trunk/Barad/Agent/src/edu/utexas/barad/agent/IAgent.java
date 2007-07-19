package edu.utexas.barad.agent;

import edu.utexas.barad.common.swt.WidgetInfo;
import edu.utexas.barad.common.swt.WidgetValues;
import edu.utexas.barad.common.swt.GUID;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * University of Texas at Austin
 * Barad Project, Jul 1, 2007
 */
public interface IAgent extends Remote {
    public WidgetInfo[] getWidgetHierarchy(boolean rebuild) throws RemoteException;

    public WidgetValues getWidgetValues(GUID guid) throws RemoteException;

    public String getProcessCommandLine() throws RemoteException;

    public int getProcessID() throws RemoteException;
}