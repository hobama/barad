package edu.utexas.barad.agent.swt.tester;

import edu.utexas.barad.agent.swt.Displays.Result;
import edu.utexas.barad.agent.swt.proxy.widgets.CompositeProxy;
import edu.utexas.barad.agent.swt.proxy.widgets.ControlProxy;
import edu.utexas.barad.agent.swt.proxy.widgets.LayoutProxy;
import edu.utexas.barad.agent.swt.Robot;

/**
 * University of Texas at Austin
 * Barad Project, Jul 26, 2007
 */
public class CompositeTester extends ScrollableTester {
    /**
     * Factory method.
     */
    public static CompositeTester getCompositeTester() {
        return (CompositeTester) getTester(CompositeProxy.class);
    }

    /**
     * Constructs a new {@link CompositeTester} associated with the specified
     * {@link TesterRobot}.
     */
    public CompositeTester(Robot swtRobot) {
        super(swtRobot);
    }

    /**
     * Proxy for {@link CompositeProxy#getChildren()}. <p/>
     *
     * @param composite the control under test.
     * @return the children of the composite.
     */
    public ControlProxy[] getChildren(final CompositeProxy composite) {
        checkWidget(composite);
        return (ControlProxy[]) syncExec(new Result() {
            public Object result() {
                return composite.getChildren();
            }
        });
    }

    /**
     * Proxy for {@link CompositeProxy#getLayout()}. <p/>
     *
     * @param composite the control under test.
     * @return the layout of the composite.
     */
    public LayoutProxy getLayout(final CompositeProxy composite) {
        checkWidget(composite);
        return (LayoutProxy) syncExec(new Result() {
            public Object result() {
                return composite.getLayout();
            }
        });
    }

    /**
     * Proxy for {@link CompositeProxy#getTabList()}. <p/>
     *
     * @param composite the control under test.
     * @return the tab list of the composite.
     */
    public ControlProxy[] getTabList(final CompositeProxy composite) {
        checkWidget(composite);
        return (ControlProxy[]) syncExec(new Result() {
            public Object result() {
                return composite.getTabList();
            }
        });
    }

    /**
     * Proxy for {@link CompositeProxy#setFocus()}
     */
    public void setFocus(final CompositeProxy composite) {
        checkWidget(composite);
        syncExec(new Runnable() {
            public void run() {
                composite.setFocus();
            }
        });
    }
}