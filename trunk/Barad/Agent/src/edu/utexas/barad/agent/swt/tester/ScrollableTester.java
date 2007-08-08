package edu.utexas.barad.agent.swt.tester;

import edu.utexas.barad.agent.swt.Displays.Result;
import edu.utexas.barad.agent.swt.proxy.graphics.RectangleProxy;
import edu.utexas.barad.agent.swt.proxy.widgets.ScrollBarProxy;
import edu.utexas.barad.agent.swt.proxy.widgets.ScrollableProxy;
import edu.utexas.barad.agent.swt.Robot;

/**
 * University of Texas at Austin
 * Barad Project, Jul 26, 2007
 */
public class ScrollableTester extends ControlTester {
    /**
     * Factory method.
     */
    public static ScrollableTester getScrollableTester() {
        return (ScrollableTester) getTester(ScrollableProxy.class);
    }

    /**
     * Constructs a new {@link ScrollableTester} associated with the specified
     * {@link TesterRobot}.
     */
    public ScrollableTester(Robot swtRobot) {
        super(swtRobot);
    }

    /**
     * Proxy for {@link ScrollableProxy#getClientArea()}. <p/>
     */
    public RectangleProxy getClientArea(final ScrollableProxy scrollable) {
        checkWidget(scrollable);
        return (RectangleProxy) syncExec(new Result() {
            public Object result() {
                return scrollable.getClientArea();
            }
        });
    }

    /**
     * Proxy for {@link ScrollableProxy#getHorizontalBar()}. <p/>
     *
     * @param scrollable the scrollable under test.
     * @return the horizontal bar.
     */
    public ScrollBarProxy getHorizontalBar(final ScrollableProxy scrollable) {
        checkWidget(scrollable);
        return (ScrollBarProxy) syncExec(new Result() {
            public Object result() {
                return scrollable.getHorizontalBar();
            }
        });
    }

    /**
     * Proxy for {@link ScrollableProxy#getVerticalBar()}. <p/>
     *
     * @param scrollable the scrollable under test.
     * @return the vertical bar.
     */
    public ScrollBarProxy getVerticalBar(final ScrollableProxy scrollable) {
        checkWidget(scrollable);
        return (ScrollBarProxy) syncExec(new Result() {
            public Object result() {
                return scrollable.getVerticalBar();
            }
        });
    }
}