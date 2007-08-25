package edu.utexas.barad.agent.testcases;

import edu.utexas.barad.common.swt.WidgetInfo;
import edu.utexas.barad.agent.swt.WidgetHierarchy;

public class CompoundPredicate implements WidgetInfoPredicate {
    private WidgetInfoPredicate[] predicates;

    public CompoundPredicate(WidgetInfoPredicate[] predicates) {
        this.predicates = predicates;
    }

    public boolean evaluate(WidgetInfo widgetInfo, final WidgetHierarchy widgetHierarchy) {
        if (predicates == null) {
            return false;
        }
        for (WidgetInfoPredicate predicate : predicates) {
            if (!predicate.evaluate(widgetInfo, widgetHierarchy)) {
                return false;
            }
        }
        return true;
    }
}