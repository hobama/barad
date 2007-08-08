package edu.utexas.barad.agent.swt.tester;

import edu.utexas.barad.agent.script.Condition;
import edu.utexas.barad.agent.swt.Displays.BooleanResult;
import edu.utexas.barad.agent.swt.Displays.IntResult;
import edu.utexas.barad.agent.swt.Displays.Result;
import edu.utexas.barad.agent.swt.Robot;
import edu.utexas.barad.agent.swt.proxy.SWTProxy;
import edu.utexas.barad.agent.swt.proxy.widgets.ShellProxy;
import edu.utexas.barad.agent.exceptions.ActionFailedException;

/**
 * University of Texas at Austin
 * Barad Project, Jul 25, 2007
 */
public class ShellTester extends DecorationsTester {

    /**
     * Waits for a {@link ShellProxy} to become visible.
     *
     * @param title the title of the {@link ShellProxy}
     * @return the visible {@link ShellProxy}
     */
//    public static ShellProxy waitVisible(String title) {
//        return waitVisible(title, null, DEFAULT_WAIT_TIMEOUT);
//    }
//
//    /**
//     * Waits for a {@link ShellProxy} to become visible.
//     *
//     * @param title   the title of the {@link ShellProxy}
//     * @param timeout the number of milliseconds to wait
//     * @return the visible {@link ShellProxy}
//     */
//    public static ShellProxy waitVisible(final String title, long timeout) {
//        return waitVisible(title, null, timeout);
//    }
//
//    /**
//     * Waits for a {@link ShellProxy} to become visible.
//     *
//     * @param title  the title of the {@link ShellProxy}
//     * @param parent the ShellProxy whose children will be searched (recursively). If <code>null</code>
//     *               then the entire hierarchy will be searched.
//     * @return the visible {@link ShellProxy}
//     */
//    public static ShellProxy waitVisible(String title, ShellProxy parent) {
//        return waitVisible(title, parent, DEFAULT_WAIT_TIMEOUT);
//    }

    /**
     * Waits for a {@link ShellProxy} to become visible.
     *
     * @param title   the title of the {@link ShellProxy}
     * @param parent  the ShellProxy whose children will be searched (recursively). If <code>null</code>
     *                then the entire hierarchy will be searched.
     * @param timeout the number of milliseconds to wait
     * @return the visible {@link ShellProxy}
     */
//    public static ShellProxy waitVisible(final String title, final ShellProxy parent, long timeout) {
//        final ShellProxy[] shell = new ShellProxy[1];
//        final WidgetFinder finder = WidgetFinderImpl.getDefault();
//        final WidgetMatcher matcher = new WidgetTextMatcher(title, ShellProxy.class, true);
//        Condition condition = new Condition() {
//            public boolean test() {
//                try {
//                    shell[0] = (ShellProxy) (parent == null ? finder.find(matcher) : finder.find(
//                            parent,
//                            matcher));
//                    return shell[0] != null;
//                } catch (NotFoundException exception) {
//                    return false;
//                } catch (MultipleFoundException e) {
//                    throw new ActionFailedException(e);
//                }
//            }
//        };
//        Robot.getDefault().wait(condition, timeout);
//        return shell[0];
//    }

    /**
     * Factory method.
     */
    public static ShellTester getShellTester() {
        return (ShellTester) WidgetTester.getTester(ShellProxy.class);
    }

    /**
     * Constructs a new {@link ShellTester} associated with the specified {@link TesterRobot}.
     */
    public ShellTester(Robot swtRobot) {
        super(swtRobot);
    }

    /**
     * Proxy for {@link ShellProxy#getImeInputMode()}. <p/>
     *
     * @param shell the shell under test.
     * @return the input mode.
     */
    public int getImeInputMode(final ShellProxy shell) {
        checkWidget(shell);
        return syncExec(new IntResult() {
            public int result() {
                return shell.getImeInputMode();
            }
        });
    }

    /**
     * Proxy for {@link ShellProxy#getShell()}. <p/>
     *
     * @param shell the shell under test.
     * @return the parent shell.
     */
    public ShellProxy getShell(final ShellProxy shell) {
        checkWidget(shell);
        return (ShellProxy) syncExec(new Result() {
            public Object result() {
                return shell.getShell();
            }
        });
    }

    /**
     * Proxy for {@link ShellProxy#getShells()}. <p/>
     *
     * @param shell the shell under test.
     * @return the child shells.
     */
    public ShellProxy[] getShells(final ShellProxy shell) {
        checkWidget(shell);
        return (ShellProxy[]) syncExec(new Result() {
            public Object result() {
                return shell.getShells();
            }
        });
    }

    /**
     * This method will see if a ShellProxy has a modal style. SWTProxy.SYSTEM_MODAL | SWTProxy.APPLICATION_MODAL |
     * SWTProxy.PRIMARY_MODAL
     *
     * @param shell
     * @return true if the ShellProxy has a modal style.
     */
    public boolean isModal(final ShellProxy shell) {
        checkWidget(shell);
        return syncExec(new BooleanResult() {
            public boolean result() {
                int style = shell.getStyle();
                if (style <= 0)
                    return false;
                int bitmask = SWTProxy.SYSTEM_MODAL | SWTProxy.APPLICATION_MODAL | SWTProxy.PRIMARY_MODAL;
                if ((style & bitmask) > 0)
                    return true;
                return false;
            }
        });
    }
}