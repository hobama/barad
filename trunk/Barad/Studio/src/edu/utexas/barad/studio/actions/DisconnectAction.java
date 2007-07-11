package edu.utexas.barad.studio.actions;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.StatusLineManager;
import org.eclipse.jface.resource.ImageDescriptor;
import edu.utexas.barad.studio.StudioMain;
import edu.utexas.barad.studio.Images;

/**
 * University of Texas at Austin
 * Barad Project, Jul 8, 2007
 *
 * @author Chip Killmar (ckillmar@mail.utexas.edu)
 */
public class DisconnectAction extends Action {
    public DisconnectAction(String text) {
        super(text);
    }

    public DisconnectAction(String text, ImageDescriptor image) {
        super(text, image);
    }

    @Override
    public void run() {
        StudioMain applicationWindow = StudioMain.getApplicationWindow();
        applicationWindow.setAgent(null);
        applicationWindow.getShell().setText(StudioMain.SHELL_TITLE);

        StatusLineManager statusLineManager = applicationWindow.getStatusLineManager();
        statusLineManager.setMessage(Images.STATUS_OK.createImage(), "Disconnected.");
        applicationWindow.updateActions();

        notifyResult(true);
    }
}