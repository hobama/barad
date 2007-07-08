package edu.utexas.barad.agent.swt;

import edu.utexas.barad.agent.AgentUtils;
import edu.utexas.barad.agent.swt.proxy.SWTProxyFactory;
import edu.utexas.barad.agent.swt.proxy.widgets.DisplayProxy;
import edu.utexas.barad.agent.swt.proxy.widgets.ShellProxy;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * University of Texas at Austin
 * Barad Project, Jul 1, 2007
 */
public class Displays {
    /**
     * @param display The Display.
     * @return <code>true</code> if the caller is running the Display's {@link Thread},
     *         <code>false</code> otherwise.
     */
    public static boolean isOnDisplayThread(DisplayProxy display) {
        return display.getThread() == Thread.currentThread();
    }

    /**
     * Gets the default Display.
     *
     * @return The default Display.
     */
    private static DisplayProxy getDefault() {
        Class clazz = AgentUtils.swtClassForName(SWTProxyFactory.ORG_ECLIPSE_SWT_WIDGETS_DISPLAY);
        DisplayProxy displayProxyClass = (DisplayProxy) SWTProxyFactory.getInstance().newProxy(clazz);
        return displayProxyClass.getDefault();
    }

    /**
     * Gets all undisposed root Shells for the default Display.
     *
     * @return A list of Shells belonging to the default Display.
     */
    public static List<ShellProxy> getShells() {
        return getShells(getDefault());
    }

    /**
     * Gets all undisposed root Shells for the specified Display.
     *
     * @param display The Display.
     * @return A list of Shells belonging to the specified Display.
     */
    public static List<ShellProxy> getShells(DisplayProxy display) {
        ShellProxy[] shells = getShellsArray(display);
        if (shells.length > 0) {
            return Arrays.asList(shells);
        }
        return Collections.emptyList();
    }

    /**
     * Gets an array of all undisposed root Shells for the specified Display.
     *
     * @param display The Display.
     * @return An array of Shells belonging to the specified Display.
     * @see #getShells(DisplayProxy)
     */
    private static ShellProxy[] getShellsArray(final DisplayProxy display) {
        if (!display.isDisposed()) {
            // If we're the UI thread then we can call display.getShells() directly.
            if (display.getThread() == Thread.currentThread()) {
                return display.getShells();
            }
            // We're not the UI thread, so have the UI thread do the call for us.
            final Object[] result = new Object[1];
            display.syncExec(new Runnable() {
                public void run() {
                    result[0] = display.getShells();
                }
            });
            return (ShellProxy[]) result[0];
        }
        return new ShellProxy[0];
    }

    /*
     * The following methods are not specific to a particular Display.
     */

    /**
     * Gets all undisposed Displays.
     *
     * @return A list of undisposed displays.
     */
    public static List<DisplayProxy> getDisplays() {
        Class clazz = AgentUtils.swtClassForName(SWTProxyFactory.ORG_ECLIPSE_SWT_WIDGETS_DISPLAY);
        DisplayProxy displayProxyClass = (DisplayProxy) SWTProxyFactory.getInstance().newProxy(clazz);
        List<DisplayProxy> displays = new ArrayList<DisplayProxy>();
        for (Thread thread : Threads.all()) {
            DisplayProxy display = displayProxyClass.findDisplay(thread);
            if (display != null && !display.isDisposed()) {
                displays.add(display);
            }
        }
        return displays;
    }
}