package edu.utexas.barad.agent.testcase;

import edu.utexas.barad.agent.swt.WidgetHierarchy;
import edu.utexas.barad.common.swt.WidgetInfo;
import edu.utexas.barad.common.Visitor;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * University of Texas at Austin
 * Barad Project, Aug 25, 2007
 */
public class WidgetFilterVisitor implements Visitor {
    private WidgetFilterStrategy widgetFilterStrategy;
    private WidgetHierarchy widgetHierarchy;
    private Set<WidgetInfo> widgetSet = new LinkedHashSet<WidgetInfo>();

    public WidgetFilterVisitor(WidgetFilterStrategy widgetFilterStrategy, WidgetHierarchy widgetHierarchy) {
        this.widgetFilterStrategy = widgetFilterStrategy;
        this.widgetHierarchy = widgetHierarchy;
    }

    public void visit(Object object) {
        WidgetInfo widgetInfo = (WidgetInfo) object;
        if (widgetFilterStrategy.include(widgetInfo, widgetHierarchy)) {
            widgetSet.add(widgetInfo);
        }
    }

    public Set<WidgetInfo> getFilteredSet() {
        return widgetSet;
    }
}