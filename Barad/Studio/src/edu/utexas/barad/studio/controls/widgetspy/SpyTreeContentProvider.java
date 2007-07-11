package edu.utexas.barad.studio.controls.widgetspy;

import edu.utexas.barad.common.swt.WidgetInfo;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;

/**
 * University of Texas at Austin
 * Barad Project, Jul 9, 2007
 */
public class SpyTreeContentProvider implements ITreeContentProvider {
    public Object[] getChildren(Object parentElement) {
        WidgetInfo parentInfo = (WidgetInfo) parentElement;
        int childCount = parentInfo.getChildCount();
        WidgetInfo[] children = new WidgetInfo[childCount];
        for (int i = 0; i < childCount; ++i) {
            children[i] = parentInfo.getChildAt(i);
        }
        return children;
    }

    public Object getParent(Object element) {
        WidgetInfo objectInfo = (WidgetInfo) element;
        return objectInfo.getParent();
    }

    public boolean hasChildren(Object element) {
        WidgetInfo objectInfo = (WidgetInfo) element;
        return objectInfo.getChildCount() > 0;
    }

    public Object[] getElements(Object inputElement) {
        return (WidgetInfo[])inputElement;
    }

    public void dispose() {
        // Empty.
    }

    public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
        // Empty.
    }
}