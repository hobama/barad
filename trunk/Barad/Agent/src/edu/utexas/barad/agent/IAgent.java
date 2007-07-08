package edu.utexas.barad.agent;

import edu.utexas.barad.common.swt.ObjectInfo;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * University of Texas at Austin
 * Barad Project, Jul 1, 2007
 */
public interface IAgent extends Remote {
    public ObjectInfo[] getWidgetHierarchy() throws RemoteException;
}