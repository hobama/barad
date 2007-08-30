package edu.utexas.barad.agent.testcase;

import edu.utexas.barad.agent.swt.WidgetHierarchy;

/**
 * University of Texas at Austin
 * Barad Project, Aug 25, 2007
 */
public interface InitialStateStrategy {
    public void resetToInitialState(final WidgetHierarchy widgetHierarchy);
}