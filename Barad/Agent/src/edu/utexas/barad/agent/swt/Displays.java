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
     * @param display The DisplayProxy.
     * @return <code>true</code> if the caller is running the DisplayProxy's {@link Thread},
     *         <code>false</code> otherwise.
     */
    public static boolean isOnDisplayThread(DisplayProxy display) {
        return display.getThread() == Thread.currentThread();
    }

    /**
     * Gets the default {@link DisplayProxy}.
     */
    private static DisplayProxy getDisplay() {
        return Robot.getDefault().getDisplay();
    }

    /**
     * Gets the default DisplayProxy.
     *
     * @return The default DisplayProxy.
     */
    private static DisplayProxy getDefault() {
        return DisplayProxy.Factory.getDefault();
    }

    /**
     * Gets all undisposed root Shells for the default DisplayProxy.
     *
     * @return A list of Shells belonging to the default DisplayProxy.
     */
    public static List<ShellProxy> getShells() {
        return getShells(getDefault());
    }

    /**
     * Gets all undisposed root Shells for the specified DisplayProxy.
     *
     * @param display The DisplayProxy.
     * @return A list of Shells belonging to the specified DisplayProxy.
     */
    public static List<ShellProxy> getShells(DisplayProxy display) {
        ShellProxy[] shells = getShellsArray(display);
        if (shells.length > 0) {
            return Arrays.asList(shells);
        }
        return Collections.emptyList();
    }

    /**
     * Gets an array of all undisposed root Shells for the specified DisplayProxy.
     *
     * @param display The DisplayProxy.
     * @return An array of Shells belonging to the specified DisplayProxy.
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
     * The following methods are not specific to a particular DisplayProxy.
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

    public static void syncExec(DisplayProxy display, Runnable runnable) {
        display.syncExec(runnable);
    }

    public static void syncExec(Runnable runnable) {
        syncExec(getDisplay(), runnable);
    }

    public static interface Result<T> {
        T result();
    }

    public static interface BooleanResult {
        boolean result();
    }

    public static interface IntResult {
        int result();
    }

    public static interface CharResult {
        char result();
    }

    public static interface StringResult {
        String result();
    }

    public static Object syncExec(DisplayProxy display, final Result result) {
        final Object[] wrapper = new Object[1];
        display.syncExec(new Runnable() {
            public void run() {
                wrapper[0] = result.result();
            }
        });
        return wrapper[0];
    }

    public static boolean syncExec(DisplayProxy display, final BooleanResult result) {
        final boolean[] wrapper = new boolean[1];
        display.syncExec(new Runnable() {
            public void run() {
                wrapper[0] = result.result();
            }
        });
        return wrapper[0];
    }

    public static int syncExec(DisplayProxy display, final IntResult result) {
        final int[] wrapper = new int[1];
        display.syncExec(new Runnable() {
            public void run() {
                wrapper[0] = result.result();
            }
        });
        return wrapper[0];
    }

    public static char syncExec(DisplayProxy display, final CharResult result) {
        final char[] wrapper = new char[1];
        display.syncExec(new Runnable() {
            public void run() {
                wrapper[0] = result.result();
            }
        });
        return wrapper[0];
    }

    public static String syncExec(DisplayProxy display, final StringResult result) {
        final String[] wrapper = new String[1];
        display.syncExec(new Runnable() {
            public void run() {
                wrapper[0] = result.result();
            }
        });
        return wrapper[0];
    }
}