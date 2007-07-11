package edu.utexas.barad.studio.controls;

import edu.utexas.barad.studio.Images;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

/**
 * University of Texas at Austin
 * Barad Project, Jul 11, 2007
 */
public class AboutDialog extends Dialog {
    public AboutDialog(Shell parentShell) {
        super(parentShell);
    }

    @Override
    protected void configureShell(Shell shell) {
        shell.setText("About Barad Studio");
        shell.setImage(Images.BARAD_ICON.createImage());
    }

    @Override
    protected int getShellStyle() {
        return SWT.TITLE | SWT.APPLICATION_MODAL | SWT.CLOSE | getDefaultOrientation();
    }

    @Override
    protected Control createContents(Composite parent) {
        Label aboutImageLabel = new Label(parent, SWT.NONE);
        Image image = Images.ABOUT.createImage();
        aboutImageLabel.setImage(image);
        aboutImageLabel.setSize(image.getBounds().width, image.getBounds().height);
        return parent;
    }
}