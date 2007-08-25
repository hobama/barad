package edu.utexas.barad.agent.testcases;

import edu.utexas.barad.common.swt.WidgetInfo;
import edu.utexas.barad.agent.swt.WidgetHierarchy;

/**
 * University of Texas at Austin
 * Barad Project, August 21, 2007
 */
public interface WidgetInfoPredicate {
    public boolean evaluate(WidgetInfo widgetInfo, final WidgetHierarchy widgetHierarchy);
}