package edu.utexas.barad.studio.controls.widgetspy;

import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.swt.graphics.Image;

/**
 * University of Texas at Austin
 * Barad Project, Jul 10, 2007
 */
public class SpyTableLabelProvider implements ITableLabelProvider {
    public Image getColumnImage(Object element, int columnIndex) {
        return null;
    }

    public String getColumnText(Object element, int columnIndex) {
        String[] nameValuePair = (String[]) element;
        return nameValuePair[columnIndex];
    }

    public void addListener(ILabelProviderListener listener) {
        // Empty.
    }

    public void dispose() {
        // Empty.
    }

    public boolean isLabelProperty(Object element, String property) {
        return false;
    }

    public void removeListener(ILabelProviderListener listener) {
        // Empty.
    }
}