package edu.utexas.barad.studio.actions;

import edu.utexas.barad.agent.AgentMain;
import edu.utexas.barad.agent.IAgent;
import edu.utexas.barad.studio.Images;
import edu.utexas.barad.studio.StudioMain;
import org.apache.log4j.Logger;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.StatusLineManager;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.resource.ImageDescriptor;

import java.lang.reflect.InvocationTargetException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 * University of Texas at Austin
 * Barad Project, Jul 8, 2007
 */
public class ConnectAction extends Action {
    private static final Logger logger = Logger.getLogger(ConnectAction.class);

    public ConnectAction(String text) {
        this(text, null);
    }

    public ConnectAction(String text, ImageDescriptor image) {
        super(text, image);
    }

    @Override
    public void run() {
        final StudioMain applicationWindow = StudioMain.getApplicationWindow();
        StatusLineManager statusLineManager = applicationWindow.getStatusLineManager();
        statusLineManager.setErrorMessage(null);

        final String hostname = null;
        final int port = AgentMain.AGENT_RMI_PORT;

        try {
            applicationWindow.run(true, false, new IRunnableWithProgress() {
                public void run(IProgressMonitor monitor) throws InvocationTargetException, InterruptedException {
                    monitor.beginTask("Connecting to agent...", IProgressMonitor.UNKNOWN);
                    monitor.worked(1);
                    try {
                        Registry registry = LocateRegistry.getRegistry(hostname, port);
                        IAgent agent = (IAgent) registry.lookup(AgentMain.AGENT_RMI_NAME);
                        applicationWindow.setAgent(agent);
                    } catch (Exception e) {
                        logger.error("Couldn't connect to agent, hostname=" + hostname + ", port=" + port, e);
                        throw new InvocationTargetException(e);
                    } finally {
                        monitor.done();
                    }
                }
            });

            applicationWindow.getShell().setText(StudioMain.SHELL_TITLE + " - localhost:" + port);
            statusLineManager.setMessage(Images.STATUS_OK.createImage(), "Connected to agent.");
            notifyResult(true);
        } catch (InvocationTargetException e) {
            logger.error("An exception occurred connecting to agent.", e);
            statusLineManager.setErrorMessage(Images.STATUS_ERROR.createImage(), "Error connecting to agent.");
            notifyResult(false);
        } catch (InterruptedException e) {
            statusLineManager.setMessage(Images.STATUS_OK.createImage(), "Operation canceled.");
            notifyResult(false);
        }

        applicationWindow.updateActions();
    }
}