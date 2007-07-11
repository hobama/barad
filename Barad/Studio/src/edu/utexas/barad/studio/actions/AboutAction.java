package edu.utexas.barad.studio.actions;

import edu.utexas.barad.studio.StudioMain;
import edu.utexas.barad.studio.controls.AboutDialog;
import org.eclipse.jface.action.Action;
import org.eclipse.swt.widgets.Shell;

/**
 * University of Texas at Austin
 * Barad Project, Jul 11, 2007
 */
public class AboutAction extends Action {
    public AboutAction(String text) {
        super(text);
    }

    @Override
    public void run() {
        StudioMain applicationWindow = StudioMain.getApplicationWindow();
        Shell shell = applicationWindow.getShell();
        AboutDialog aboutDialog = new AboutDialog(shell);
        aboutDialog.setBlockOnOpen(true);
        aboutDialog.open();
    }
}