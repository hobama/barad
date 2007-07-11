package edu.utexas.barad.studio.actions;

import edu.utexas.barad.studio.StudioMain;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.StatusLineManager;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;

/**
 * University of Texas at Austin
 * Barad Project, Jul 11, 2007
 */
public class RefreshAction extends Action implements SelectionListener {
    public RefreshAction(String text) {
        this(text, null);
    }

    public RefreshAction(String text, ImageDescriptor image) {
        super(text, image);
    }

    @Override
    public void run() {
        StudioMain applicationWindow = StudioMain.getApplicationWindow();
        StatusLineManager statusLineManager = applicationWindow.getStatusLineManager();

        statusLineManager.setErrorMessage(null);
        applicationWindow.refresh();
    }

    public void widgetSelected(SelectionEvent event) {
        StudioMain applicationWindow = StudioMain.getApplicationWindow();
        applicationWindow.updateActions();
    }

    public void widgetDefaultSelected(SelectionEvent event) {
        StudioMain applicationWindow = StudioMain.getApplicationWindow();
        applicationWindow.updateActions();
    }
}