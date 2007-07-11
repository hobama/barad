package edu.utexas.barad.studio.controls.widgetspy;

import edu.utexas.barad.common.swt.WidgetValues;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.Viewer;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * University of Texas at Austin
 * Barad Project, Jul 10, 2007
 */
public class SpyTableContentProvider implements IStructuredContentProvider {
    public Object[] getElements(Object inputElement) {
        List<Object> elements = new ArrayList<Object>();
        WidgetValues widgetValues = (WidgetValues) inputElement;
        
        Set<Map.Entry<String, String>> entrySet = widgetValues.getPropertyValues().entrySet();
        for (Map.Entry<String, String> entry : entrySet) {
            elements.add(new String[]{entry.getKey(), entry.getValue()});
        }

        entrySet = widgetValues.getFieldValues().entrySet();
        for (Map.Entry<String, String> entry : entrySet) {
            elements.add(new String[]{entry.getKey(), entry.getValue()});
        }
        
        return elements.toArray(new Object[0]);
    }

    public void dispose() {
        // Empty.
    }

    public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
        // Empty.
    }
}