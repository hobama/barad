package edu.utexas.barad.studio.actions;

import edu.utexas.barad.studio.StudioMain;
import org.eclipse.jface.action.Action;

/**
 * University of Texas at Austin
 * Barad Project, Jul 8, 2007
 *
 * @author Chip Killmar (ckillmar@mail.utexas.edu)
 */
public class ExitAction extends Action {
    public ExitAction(String text) {
        super(text);
    }

    @Override
    public void run() {
        StudioMain applicationWindow = StudioMain.getApplicationWindow();
        applicationWindow.close();
    }
}