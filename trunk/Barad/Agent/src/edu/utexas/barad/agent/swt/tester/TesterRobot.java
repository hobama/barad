package edu.utexas.barad.agent.swt.tester;

import edu.utexas.barad.agent.exceptions.ActionFailedException;
import edu.utexas.barad.agent.exceptions.ActionItemNotFoundException;
import edu.utexas.barad.agent.exceptions.WaitTimedOutException;
import edu.utexas.barad.agent.script.Condition;
import edu.utexas.barad.agent.swt.Displays;
import edu.utexas.barad.agent.swt.Displays.IntResult;
import edu.utexas.barad.agent.swt.Displays.Result;
import edu.utexas.barad.agent.swt.Robot;
import edu.utexas.barad.agent.swt.WidgetLocator;
import edu.utexas.barad.agent.swt.proxy.SWTProxy;
import static edu.utexas.barad.agent.swt.proxy.SWTProxy.*;
import edu.utexas.barad.agent.swt.proxy.graphics.ColorProxy;
import edu.utexas.barad.agent.swt.proxy.graphics.ImageProxy;
import edu.utexas.barad.agent.swt.proxy.graphics.PointProxy;
import edu.utexas.barad.agent.swt.proxy.graphics.RectangleProxy;
import edu.utexas.barad.agent.swt.proxy.widgets.*;
import edu.utexas.barad.common.Platform;
import edu.utexas.barad.common.Properties;
import edu.utexas.barad.common.ReflectionUtils;
import org.apache.log4j.Logger;

import java.util.Collection;
import java.util.Iterator;
import java.util.StringTokenizer;

/**
 * University of Texas at Austin
 * Barad Project, Jul 25, 2007
 */
public class TesterRobot {
    private static final Logger logger = Logger.getLogger(TesterRobot.class);

    /**
     * The default {@link Robot} which will be associated with a {@link Robot} on the
     * default {@link DisplayProxy}.
     *
     * @see #getDefault()
     * @see Robot#getDefault()
     */
    private static TesterRobot Default;

    /**
     * A mutex for getting the default {@link Robot}.
     *
     * @see #getDefault()
     */
    private static final Object DefaultLock = new Object();

    /**
     * A factory method which gets the default {@link Robot}, creating it if necessary.
     *
     * @see Robot#getDefault()
     */
    public static TesterRobot getDefault() {
        synchronized (DefaultLock) {
            if (Default == null)
                Default = new TesterRobot(Robot.getDefault());
            return Default;
        }
    }

    /*
    * Button-related constants.
    */

    /**
     * The mouse button to use to bring up a popup menu. Usually this will be {@link SWTProxy#BUTTON3}.
     * Using {@link SWTProxy#BUTTON2} seems to be Mac-specific.
     */
    public static final int BUTTON_POPUP = Platform.isMacintosh() ? BUTTON2 : BUTTON3;

    /**
     * TODO Understand & document.
     */
    public static final String POPUP_MODIFIER = BUTTON_POPUP == BUTTON2 ? "BUTTON2_MASK"
            : "BUTTON3_MASK";

    /**
     * TODO Understand & document.
     */
    public static final boolean POPUP_ON_PRESS = !Platform.isWindows();

    /**
     * TODO Understand & document.
     */
    public static final int TERTIARY_MASK = BUTTON_POPUP == BUTTON2 ? BUTTON3 : BUTTON2;

    /**
     * TODO Understand & document.
     */
    public static final String TERTIARY_MODIFIER = BUTTON_POPUP == BUTTON2 ? "BUTTON3_MASK"
            : "BUTTON2_MASK";

    /**
     * Return whether this is the tertiary button, considering primary to be button1 and secondary
     * to be the popup trigger button.
     *
     * @deprecated Nothing seems to use this so maybe it should go away.
     */
    public static boolean isTertiaryButton(int mods) {
        return ((mods & BUTTON_MASK) != BUTTON1) && ((mods & BUTTON_POPUP) == 0);
    }

    /*
    * End of button-related constants.
    */

    /*
    * Delay-related constants.
    */

    /**
     * A suitable delay for most cases. Tests have been run safely at this value. The value should
     * definitely be less than the double-click threshold. The idea is to adjust this value down to
     * as close to 0 as possible for each platform.
     * <p/>
     * <strong>Note:</strong> The value 0 causes about half of the tests to fail on Linux.
     * <p/>
     * FIXME Need to find a value between 0 and 100 (100 is kinda slow). 30 works (almost) for
     * w32/linux, but OSX 10.1.5 text input lags (50 is minimum).
     * <p/>
     * Not sure it's worth tracking down all the robot bugs and working around them.
     * <p/>
     * The value is set in a static initializer in order to prevent the compiler from inlining the
     * value.
     */
    private static final long DEFAULT_DELAY;

    static {
        DEFAULT_DELAY = Platform.isOSX() || Platform.isLinux() || Platform.isWindows() ? 0L : 50L;
    }

    /**
     * The default number of milliseconds to automatically delay after generating an input event.
     * <p/>
     * The value is set in a static initializer in order to prevent the compiler from inlining the
     * value.
     */
    private static final long DEFAULT_AUTO_DELAY;

    static {
        DEFAULT_AUTO_DELAY = Properties.getProperty(
                "abbot.robot.auto_delay",
                0L,
                60000L,
                DEFAULT_DELAY);
    }

    /**
     * The default maximum number of milliseconds to wait for a {@link Condition} to become
     * <code>true</code>.
     *
     * @see #wait(Condition)
     */
    public static final long DEFAULT_WAIT_TIMEOUT = Properties.getProperty(
            "abbot.robot.default_wait_timeout",
            0L,
            60000L,
            30000L);

    /**
     * The default number of milliseconds to delay between {@link Condition} checks.
     *
     * @see #wait(Condition)
     * @see #wait(Condition,long)
     */
    private static final long DEFAULT_WAIT_INTERVAL = 10L;

    /**
     * The default maximum number of milliseconds to tolerate before failing to find a
     * {@link WidgetProxy} that should be visible.
     *
     * @see WidgetTester#waitForShellShowing(String,boolean)
     * @see WidgetTester#waitForShellShowing(String)
     */
    protected static final long DEFAULT_WIDGET_TIMEOUT = Properties.getProperty(
            "abbot.robot.widget_timeout",
            0L,
            60000L,
            DEFAULT_WAIT_TIMEOUT);

    /*
    * End of delay-related constants.
    */

    /*
    * Instance variables
    */

    /**
     * The lower-level robot used to generate user input events.
     */
    protected final Robot swtRobot;

    /**
     * Should we automatically wait for an idle event loop after generating a user input event.
     */
    protected boolean isAutoWaitForIdle;

    /**
     * The number of milliseconds ti delay after generating a user input event.
     */
    protected long autoDelay = DEFAULT_AUTO_DELAY;

    /**
     * @see DisplayProxy#getDoubleClickTime()
     */
    private long doubleClickTime;

    /**
     * If we've started a drag, this is the source.
     */
    protected RectangleProxy dragSource;

    /**
     * The system time (in milliseconds) of the last mouse button press. Used to avoid unintentional
     * double-clicks.
     *
     * @see #mousePressPrim(int,boolean)
     */
    private long clickstamp;

    /* End of instance variables */

    /**
     * Constructs a new {@link Robot} associated with a specified {@link Robot}).
     */
    public TesterRobot(final Robot swtRobot) {
        if (swtRobot == null)
            throw new IllegalArgumentException("swtRobot is null");
        this.swtRobot = swtRobot;

        // Record all MouseDown timestamps.
//        syncExec(new Runnable() {
//            public void run() {
//                getDisplay().addListener(MouseDown, ListenerProxy.Factory.newListenerProxy(new ListenerProxy.Impl() {
//                    public void handleEvent(EventProxy event) {
//                        long time = event.__fieldGettime() == 0 ? System.currentTimeMillis()
//                                : (long) event.__fieldGettime() & 0xffffffffL;
//                        setStamp(time);
//                    }
//                }));
//            }
//        });
    }

    private synchronized void setStamp(long time) {
        clickstamp = time;
    }

    private synchronized long getStamp() {
        return clickstamp;
    }

    public Robot getRobot() {
        return swtRobot;
    }

    public DisplayProxy getDisplay() {
        return swtRobot.getDisplay();
    }

    public long getAutoDelay() {
        return autoDelay;
    }

    public long setAutoDelay(long autoDelay) {
        long oldAutoDelay = this.autoDelay;
        this.autoDelay = autoDelay;
        return oldAutoDelay;
    }

    public boolean isAutoWaitForIdle() {
        return isAutoWaitForIdle;
    }

    public boolean setAutoWaitForIdle(boolean isAutoWaitForIdle) {
        boolean oldIsAutoWaitForIdle = this.isAutoWaitForIdle;
        this.isAutoWaitForIdle = isAutoWaitForIdle;
        return oldIsAutoWaitForIdle;
    }

    /**
     * Gets this {@link Robot}'s double-click time.
     *
     * @return the double-click time in milliseconds
     * @see DisplayProxy#getDoubleClickTime()
     */
    public synchronized long getDoubleClickTime() {
        if (doubleClickTime == 0L) {
            final long[] time = new long[1];
            syncExec(new Runnable() {
                public void run() {
                    time[0] = getDisplay().getDoubleClickTime();
                }
            });
            doubleClickTime = time[0];
        }
        return doubleClickTime;
    }

    protected void afterEvent() {

        if (isAutoWaitForIdle)
            waitForIdle();

        if (autoDelay > 0)
            sleepPrim(autoDelay);
    }

    /*
    * Utility syncExec()-like methods.
    */

    protected void syncExec(Runnable runnable) {
        Displays.syncExec(getDisplay(), runnable);
    }

    protected Object syncExec(Displays.Result result) {
        return Displays.syncExec(getDisplay(), result);
    }

    protected int syncExec(Displays.IntResult result) {
        return Displays.syncExec(getDisplay(), result);
    }

    protected boolean syncExec(Displays.BooleanResult result) {
        return Displays.syncExec(getDisplay(), result);
    }

    protected char syncExec(Displays.CharResult result) {
        return Displays.syncExec(getDisplay(), result);
    }

    protected String syncExec(Displays.StringResult result) {
        return Displays.syncExec(getDisplay(), result);
    }

    /*
    * End of utility syncExec()-like methods.
    */

    /*
    * Utility wait methods.
    */

    /**
     * Wait for a {@link Condition} to become true.
     *
     * @throws WaitTimedOutException if the default timeout is exceeded.
     */
    public void wait(Condition condition) {
        wait(condition, DEFAULT_WAIT_TIMEOUT, DEFAULT_WAIT_INTERVAL);
    }

    /**
     * Wait for a {@link Condition} to become true.
     *
     * @throws WaitTimedOutException if the timeout is exceeded.
     */
    public void wait(Condition condition, long timeout) {
        wait(condition, timeout, DEFAULT_WAIT_INTERVAL);
    }

    /**
     * Wait for the specified Condition to become true.
     *
     * @throws WaitTimedOutException if the timeout is exceeded.
     */
    public void wait(Condition condition, long timeout, long interval) {
        checkThread();
        checkTime(timeout);
        checkTime(interval);
        long limit = System.currentTimeMillis() + timeout;
        while (!condition.test()) {
            sleepPrim(interval);
            if (System.currentTimeMillis() > limit)
                throw new WaitTimedOutException();
        }
    }

    /**
     * Sleep the given duration of time (in milliseconds).
     */
    public void sleep(long time) {
        checkThread();
        sleepPrim(time);
    }

    private void sleepPrim(long time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException exception) {
            // Empty block intended.
        }
    }

    /**
     * Delay the given duration of time (in milliseconds).
     */
    protected void delay(long time) {
        if (!isOnDisplayThread())
            throw new RuntimeException("invalid thread");
        checkTime(time);

        // Have done[0] set to true when the time has elapsed.
        final boolean[] done = new boolean[1];
        DisplayProxy display = getDisplay();
        display.timerExec((int) time, new Runnable() {
            public void run() {
                done[0] = true;
            }
        });

        while (!done[0]) {
            if (!display.readAndDispatch())
                display.sleep();
        }
    }

    /*
    * End: Utility wait methods.
    */

    /*
    * Begin: Mouse movement support.
    */

    /**
     * Generates a mouse move input event to move the mouse cursor to a specified display location.
     * <p/>
     * <b>Note:</b> In robot mode, if you intend to subsequently click at the target location then
     * you may need to invoke this method twice, including a little jitter in the first invocation.
     * There are some conditions where a single mouse move will not actually generate the necessary
     * enter event on a component (typically a dialog with an OK button) before a mousePress.
     *
     * @see #mouseMove(RectangleProxy,int,int)
     */
    public void mouseMove(int x, int y) {
        swtRobot.mouseMove(x, y);
    }

    public void mouseMove(RectangleProxy bounds, int x, int y) {
        checkBounds(bounds);
        checkLocation(bounds, x, y);

        // Center x & y, if requested.
        if (x == -1 && y == -1) {
            x = bounds.__fieldGetwidth() / 2;
            y = bounds.__fieldGetheight() / 2;
        }

        // Jitter first, if necessary.
        if (hasRobotMotionBug()) {
            PointProxy p = getJitterPoint(bounds, x, y);
            if (p == null) {
                logger.warn(String.format("could not jitter(%s,%d,%d)\n", bounds, x, y));
            } else {
                mouseMove(bounds.__fieldGetx() + p.__fieldGetx(), bounds.__fieldGety() + p.__fieldGety());
            }
        }

        // Move
        mouseMove(bounds.__fieldGetx() + x, bounds.__fieldGety() + y);
    }

    private PointProxy getJitterPoint(RectangleProxy bounds, int x, int y) {
        if (x > 0)
            return PointProxy.Factory.newPointProxy(x - 1, y);
        if (y > 0)
            return PointProxy.Factory.newPointProxy(x, y - 1);
        if (x < bounds.__fieldGetwidth() - 1)
            return PointProxy.Factory.newPointProxy(x + 1, y);
        if (y < bounds.__fieldGetheight() - 1)
            return PointProxy.Factory.newPointProxy(x, y + 1);
        return null;
    }

    public void mouseMove(WidgetProxy widget, int x, int y) {
        checkWidget(widget);
        mouseMove(getBounds(widget), x, y);
    }

    public void mouseMove(final WidgetProxy widget) {
        mouseMove(widget, -1, -1);
    }

    /*
    * End: Mouse movement support.
    */

    /*
    * Begin: Mouse press & release support.
    */

    /**
     * Generates a mouse press input event for specified mouse buttons after (optionally) delaying
     * long enough to avoid it being a multi-click due to the previous mouse press.
     * <p/>
     * <b>Note: <em>All</em></b> mouse press events must be sent through this method in order to
     * avoid inadvertant multi-clicks (e.g., double-clicks). Also, this method should be the only
     * one that calls {@link Robot#mousePress(int)}.
     *
     * @param buttons    the buttons to press (one or more of {@link SWTProxy#BUTTON_MASK}).
     * @param avoidMulti true if multi-clicks should be avoided, false otherwise.
     */
    private void mousePressPrim(int buttons, boolean avoidMulti) {

        // Avoid multi-click if necessary.
        if (avoidMulti) {
            long now = System.currentTimeMillis();
            long prev = getStamp();
            if (prev == 0L)
                prev = now;
            long next = prev + getDoubleClickTime() * 2;
            long remaining = next - now;
            while (remaining > 0L) {
                sleep(remaining);
                now = System.currentTimeMillis();
                remaining = next - now;
            }
        }

        // Do the mouse press.
        swtRobot.mousePress(buttons);
    }

    /**
     * Generate a button release event.
     */
    public void mouseReleasePrim(int buttons) {
        swtRobot.mouseRelease(buttons);
    }

    /**
     * Generates a mouse press input event for specified mouse buttons.
     */
    public void mousePress(int buttons) {
        mousePressPrim(buttons, true);
        afterEvent();
    }

    /**
     * Generates a mouse press input event for mouse button #1.
     */
    public void mousePress() {
        mousePress(BUTTON1);
    }

    /**
     * Generate a button release event.
     */
    public void mouseRelease(int buttons) {
        mouseReleasePrim(buttons);
        afterEvent();
    }

    /**
     * Generates a mouse release input event for mouse button #1.
     */
    public void mouseRelease() {
        mouseRelease(BUTTON1);
    }

    /*
    * End: Mouse press & release support.
    */

    /*
    * Begin: Mouse click support.
    */

    /**
     * Generate a single-click user input event.
     * <p/>
     * <b>Note:<b> This method automatically avoids generating a double-click event due to a recent
     * prior MouseDown event. Therefore, you cannot use two calls in a row to this method for
     * generating a double-click. Use {@link #doubleClick(int)} instead.
     */
    public void click(int mask) {

        int modifiers = mask & MODIFIER_MASK;
        int buttons = mask & BUTTON_MASK;

        try {
            if (modifiers != 0)
                keyPress(modifiers);
            mousePressPrim(buttons, true);
            mouseReleasePrim(buttons);
        } finally {
            if (modifiers != 0)
                keyRelease(modifiers);
        }

        afterEvent();
    }

    public void click(int x, int y, int mask) {
        mouseMove(x, y);
        click(mask);
    }

    public void click(RectangleProxy bounds, int x, int y, int mask, int clicks) {

        if (clicks == 1) {
            click(bounds, x, y, mask);
            return;
        }

        if (clicks == 2) {
            doubleClick(bounds, x, y, mask);
            return;
        }

        int modifiers = mask & MODIFIER_MASK;
        int buttons = mask & BUTTON_MASK;

        if (modifiers != 0)
            keyPressPrim(modifiers);

        try {

            // For the first one only, we need to avoid causing a multi-click from a previous click.
            mousePressPrim(buttons, true);
            mouseReleasePrim(buttons);

            for (int i = 1; i < clicks; i++) {
                mousePressPrim(buttons, false);
                mouseReleasePrim(buttons);
            }

        } finally {
            if (modifiers != 0)
                keyReleasePrim(modifiers);
        }

        afterEvent();

    }

    public void click(RectangleProxy bounds, int x, int y, int mask) {
        click(bounds.__fieldGetx() + x, bounds.__fieldGety() + y, mask);
    }

    public void click(RectangleProxy bounds, int mask) {
        click(bounds, bounds.__fieldGetwidth() / 2, bounds.__fieldGetheight() / 2, mask);
    }

    public void click() {
        click(BUTTON1);
    }

    public void click(int x, int y) {
        click(x, y, BUTTON1);
    }

    public void click(RectangleProxy bounds, int x, int y) {
        click(bounds, x, y, BUTTON1);
    }

    public void click(RectangleProxy bounds) {
        click(bounds, BUTTON1);
    }

    /* Widget clicking. */

    public void click(WidgetProxy widget) {
        click(widget, BUTTON1);
    }

    public void click(WidgetProxy widget, int mask) {
        PointProxy p = getClickPoint(widget);
        click(widget, p.__fieldGetx(), p.__fieldGety(), mask);
    }

    /**
     * Returns the default point within (and relative to) a {@link WidgetProxy} at which it should be
     * clicked.<br>
     * Subclasses may redefine as needed.
     *
     * @param widget a {@link WidgetProxy}
     * @return a {@link PointProxy} relative to the {@link WidgetProxy}
     */
    protected PointProxy getClickPoint(WidgetProxy widget) {
        RectangleProxy bounds = getBounds(widget);
        return PointProxy.Factory.newPointProxy(bounds.__fieldGetwidth() / 2, bounds.__fieldGetheight() / 2);
    }

    public void click(WidgetProxy widget, int x, int y) {
        click(widget, x, y, BUTTON1);
    }

    public void click(WidgetProxy widget, int x, int y, int mask) {
        click(getBounds(widget), x, y, mask);
    }

    public void click(WidgetProxy widget, int x, int y, int mask, int clicks) {
        click(getBounds(widget), x, y, mask, clicks);
    }

    /*
    * End: Mouse click support.
    */

    /*
    * Mouse double-click support.
    */

    public void doubleClick(int mask) {

        int modifiers = mask & MODIFIER_MASK;
        int buttons = mask & BUTTON_MASK;

        try {
            if (modifiers != 0)
                keyPressPrim(modifiers);
            mousePressPrim(buttons, true);
            mouseReleasePrim(buttons);
            mousePressPrim(buttons, false);
            mouseReleasePrim(buttons);
        } finally {
            if (modifiers != 0)
                keyReleasePrim(modifiers);
        }

        afterEvent();
    }

    public void doubleClick(int x, int y, int mask) {
        mouseMove(x, y);
        doubleClick(mask);
    }

    public void doubleClick(RectangleProxy bounds, int x, int y, int mask) {
        doubleClick(bounds.__fieldGetx() + x, bounds.__fieldGety() + y, mask);
    }

    public void doubleClick(RectangleProxy bounds, int mask) {
        doubleClick(bounds, bounds.__fieldGetwidth() / 2, bounds.__fieldGetheight() / 2, mask);
    }

    public void doubleClick() {
        doubleClick(BUTTON1);
    }

    public void doubleClick(int x, int y) {
        doubleClick(x, y, BUTTON1);
    }

    public void doubleClick(RectangleProxy bounds, int x, int y) {
        doubleClick(bounds.__fieldGetx() + x, bounds.__fieldGety() + y, BUTTON1);
    }

    public void doubleClick(RectangleProxy bounds) {
        doubleClick(bounds, BUTTON1);
    }

    /* Widget double-clicking. */

    public void doubleClick(WidgetProxy widget) {
        doubleClick(widget, BUTTON1);
    }

    public void doubleClick(WidgetProxy widget, int mask) {
        PointProxy p = getClickPoint(widget);
        doubleClick(widget, p.__fieldGetx(), p.__fieldGety(), mask);
    }

    public void doubleClick(WidgetProxy widget, int x, int y) {
        doubleClick(widget, x, y, BUTTON1);
    }

    public void doubleClick(WidgetProxy widget, int x, int y, int mask) {
        doubleClick(getBounds(widget), x, y, mask);
    }

    /*
    * End of mouse double-click support.
    */

    /*
    * Begin: Keystroke support.
    */

    /**
     * Generate a key press event.
     */
    public void keyPressPrim(int keycode) {
        swtRobot.keyPress(keycode);
    }

    /**
     * Generate a key release event.
     */
    public void keyReleasePrim(int keycode) {
        swtRobot.keyRelease(keycode);
    }

    /**
     * Generate a key press event.
     */
    public void keyPress(int keycode) {
        keyPressPrim(keycode);
        afterEvent();
    }

    /**
     * Generate a key release event.
     */
    public void keyRelease(int keycode) {
        keyReleasePrim(keycode);
        afterEvent();
    }

    /**
     * Type (press and release) all of the keys contained in an accelerator.
     * <p/>
     * <b>Note:</b> Uppercase characters will be typed as uppercase, which requires that the shift
     * key be depressed.
     *
     * @see Robot
     */
    public void key(int accelerator) {
        keyPress(accelerator);
        keyRelease(accelerator);
    }

    /**
     * Type (press and release) a charachter key.
     */
    public void key(char c) {
        key((int) c);
    }

    /**
     * Type all of the characters in a {@link CharSequence} (e.g., a {@link String}).
     */
    public void key(CharSequence charSequence) {
        for (int i = 0; i < charSequence.length(); i++) {
            key(charSequence.charAt(i));
        }
    }

    /*
    * End: Keystroke support.
    */

    /*
    * Begin: Argument checking utilities.
    */

    protected void checkLocation(RectangleProxy bounds, int x, int y) {
        if (x == -1 && y == -1)
            return;
        if (x < 0 || y < 0 || x >= bounds.__fieldGetwidth() - 1 || y >= bounds.__fieldGetheight() - 1)
            throw new IllegalArgumentException(String.format(
                    "bad location: (%d,%d), bounds: %s\n",
                    x,
                    y,
                    bounds));
    }

    protected void checkBounds(RectangleProxy bounds) {
        if (bounds.__fieldGetx() < 0 || bounds.__fieldGety() < 0)
            throw new IllegalArgumentException("negative bounds: " + bounds);
    }

    protected void checkTime(long time) {
        if (time < 0L)
            throw new IllegalArgumentException("time is negative");
    }

    /*
    * End: Argument checking utilities.
    */

    public void waitForIdle() {
        swtRobot.waitForIdle();
    }

    public void activate(final ShellProxy shell) {
        checkWidget(shell);
        syncExec(new Runnable() {
            public void run() {
                shell.forceActive();
            }
        });
    }

    /**
     * @deprecated Use {@link #findFocusOwner()} instead
     */
    public WidgetProxy findFocusOwner(final DisplayProxy display) {
        checkDisplay(display);
        return findFocusOwner();
    }

    public WidgetProxy findFocusOwner() {
        return (ControlProxy) syncExec(new Result() {
            public Object result() {
                return getDisplay().getFocusControl();
            }
        });
    }

    /**
     * Move keyboard focus to the given component.
     */
    public void focus(final ControlProxy control) {
        checkWidget(control);
        syncExec(new Runnable() {
            public void run() {
                control.forceFocus();
            }
        });
        mouseMove(control);
        // Robot.syncExec(display,this, new Runnable(){
        // public void run(){
        // controlT = display.getFocusControl();
        // shellT = c.getShell();
        // if(controlT!=c){
        // Log.debug("ROBOT: Focus change");
        // activate(shellT);
        // c.forceFocus();
        // mouseMove(c);
        // }
        // }
        // });
        // if(controlT!=c){
        // Log.debug("ROBOT: Focus change");
        // activate(shellT);
        // Robot.syncExec(display,this, new Runnable(){
        // public void run(){
        // c.forceFocus();
        // }
        // });
        // mouseMove(c);
        // }
    }

    // /** Sleep for a little bit, measured in UI time. */
    // public void pollingDelay() {
    // delay(DEFAULT_POLLING_DELAY);
    // }

    //
    // ------------------------- TestSet1----------------------------------------
    // --------------------------------------------------------------------------

    /**
     * Sample the color at the given point on the screen.
     */
    public ColorProxy sample(int x, int y) {
        return swtRobot.getPixelColor(x, y);
    }

    /**
     * Capture the contents of the given rectangle.
     */
    /*
    * NOTE: Text components (and maybe others with a custom cursor) will capture the cursor. May
    * want to move the cursor out of the component bounds, although this might cause issues where
    * the component is responding visually to mouse movement. Is this an OSX bug?
    */
    public ImageProxy capture(RectangleProxy bounds) {
        return swtRobot.createScreenCapture(bounds);
    }

    /**
     * Capture the contents of the given Widget, sans any border or insets. [FROM abbot.tester.awt's
     * implementation]: This should only be used on components that do not use a LAF UI, or the
     * results will not be consistent across platforms.
     */
    public ImageProxy capture(final WidgetProxy widget) {
        return capture(widget, false);
    }

    /**
     * Capture the contents of the given Widget, optionally including the border and/or insets.
     * [FROM abbot.tester.awt's implementation]: This should only be used on components that do not
     * use a LAF UI, or the results will not be consistent across platforms.
     */
    public ImageProxy capture(final WidgetProxy widget, final boolean ignoreBorder) {
        RectangleProxy bounds = getBounds(widget, ignoreBorder);
        return capture(bounds);
    }

    // -------------------------------------------------------------

    // /**
    // * Run the given action on the event dispatch thread.
    // */
    // public static void invokeAction(DisplayProxy display, Runnable action) {
    // display.asyncExec(action);
    // }

    // /** Run the given action on the event dispatch thread, but don't return
    // until it's been run.
    // */
    // public static void invokeAndWait(DisplayProxy display, Runnable action) {
    // display.syncExec(action);
    // }

    // private static final Runnable EMPTY_RUNNABLE =
    // new Runnable() { public void run() { } };

    // Bug workaround support
    // Bug workaround support
    // private void jitter(int x, int y) {
    // mouseMove((x > 0 ? x - 1 : x + 1), y);
    // }

    /**
     * Move the mouse appropriately to get from the source to the destination. Enter/exit events
     * will be generated where appropriate.
     */
    public void dragOver(WidgetProxy target, int x, int y) {
        dragOver(getBounds(target), x, y);
    }

    public void dragOver(RectangleProxy target, int x, int y) {
        mouseMove(target, (x > 1) ? x - 1 : x + 1, y);
        mouseMove(target, x, y);
    }

    public void dragOver(int x, int y) {
        mouseMove(x > 0 ? x - 1 : x + 1, y);
        mouseMove(x, y);
    }

    /**
     * Begin a drag operation.
     */
    public void drag(WidgetProxy source, int x, int y, int modifiers) {
        drag(getBounds(source), x, y, modifiers);
    }

    /**
     * Begin a drag operation.
     */
    public void drag(RectangleProxy source, int x, int y, int modifiers) {
        // FIXME make sure it's sufficient to be recognized as a drag
        // by the default drag gesture recognizer
        mouseMove(source, x, y);
        getRobot().mouseDrag(modifiers);
        // mousePress(modifiers);
        // mouseMove(source, x > 0 ? x - 1 : x + 1, y);
        dragSource = source;
        // dragLocation = new org.eclipse.swt.graphics.PointProxy(sx, sy);
        // inDragSource = true;
    }

    /**
     * End a drag operation, releasing the mouse button over the given target location.
     */
    public void drop(WidgetProxy target, int x, int y, int modifiers) {
        drop(getBounds(target), x, y, modifiers);
    }

    public void drop(RectangleProxy target, int x, int y, int modifiers) {
        // All motion events are relative to the drag source
        if (dragSource == null)
            throw new ActionFailedException("no drag source");
        mouseMove(target, x, y);
        getRobot().mouseDrop(modifiers);
        // inDropTarget = dragSource == target;
        // dragOver(target, x, y);
        // mouseRelease(modifiers);
        dragSource = null;
        // dragLocation = null;
        // inDragSource = inDropTarget = false;
    }

    public void drop(int x, int y, int modifiers) {
        if (dragSource == null)
            throw new ActionFailedException("no drag source");
        mouseMove(x, y);
        getRobot().mouseDrop(modifiers);
        // dragOver(x, y);
        // mouseRelease(modifiers);
        dragSource = null;
    }

    protected RectangleProxy getBounds(final WidgetProxy widget, final boolean ignoreBorder) {
        checkWidget(widget);
        return (RectangleProxy) syncExec(new Result() {
            public Object result() {
                return WidgetLocator.getBounds(widget, ignoreBorder);
            }
        });
    }

    protected RectangleProxy getBounds(final WidgetProxy widget) {
        return getBounds(widget, true);
    }

    protected PointProxy getDisplayLocation(final WidgetProxy widget) {
        checkWidget(widget);
        return (PointProxy) syncExec(new Result() {
            public Object result() {
                return WidgetLocator.getLocation(widget);
            }
        });
    }

    /**
     * @deprecated Cheater!
     */
    public void selectPopupMenuItem(final MenuItemProxy item, final int x, final int y) {
        checkWidget(item);

        MenuItemTester menuItemTester = MenuItemTester.getMenuItemTester();
        ItemPath path = menuItemTester.getPath(item);
        final MenuProxy menu = menuItemTester.getRootMenu(item);

        MenuTester menuTester = MenuTester.getMenuTester();
        if (menuTester.isPopUp(menu)) {
            if (!menuTester.isVisible(menu)) {
                syncExec(new Runnable() {
                    public void run() {
                        // FIXME Cheater!
                        menu.setVisible(true);
                    }
                });
                menuTester.waitVisible(menu);
            }
        }

        menuTester.clickItem(menu, path);

    }

    /**
     * Find and select a {@link MenuItemProxy} based on its "name" property.
     *
     * @param parent a Composite container that contains the MenuItem
     * @param name   the text of the MenuItem (and its name)
     * @deprecated Use a {@link MenuTester}.
     */
    public void selectMenuItemByText(MenuProxy parent, String name) {
        checkWidget(parent);
        MenuTester menuTester = MenuTester.getMenuTester();
        MenuItemProxy item = menuTester.findItem(parent, name);
        if (item == null)
            throw new ActionItemNotFoundException(name);
        WidgetTester.getWidgetTester().actionSelectMenuItem(item);
    }

    /**
     * Select a {@link MenuItemProxy}.
     *
     * @param item The {@link MenuItemProxy} to be selected
     * @deprecated Use a {@link MenuTester} or a {@link MenuItemTester}.
     */
    public void selectMenuItem(final MenuItemProxy item) {
        checkWidget(item);
        WidgetTester.getWidgetTester().actionSelectMenuItem(item);
    }

    /**
     * Identify the coordinates of the iconify button where we can, returning null if we can't.
     */
    public PointProxy getIconifyLocation(final ShellProxy shell) {
        checkWidget(shell);
        return (PointProxy) syncExec(new Result() {
            public Object result() {
                int style = shell.getStyle();
                if ((style & MIN) == MIN) {
                    if (Platform.isWindows()) {
                        int xOffset = 50 + shell.getBorderWidth();
                        int yOffset = 12 + shell.getBorderWidth();
                        RectangleProxy bounds = WidgetLocator.getBounds(shell);
                        return PointProxy.Factory.newPointProxy(bounds.__fieldGetwidth() - xOffset, yOffset);
                    }
                }
                return null;
            }
        });
    }

    /**
     * Identify the coordinates of the maximize button where possible, returning null if not.
     */
    public synchronized PointProxy getMaximizeLocation(final ShellProxy shell) {
        checkWidget(shell);
        PointProxy loc = getIconifyLocation(shell);
        if (loc != null) {
            int style = syncExec(new IntResult() {
                public int result() {
                    return shell.getStyle();
                }
            });
            if ((style & MAX) == MAX) {
                if (Platform.isWindows()) {
                    return PointProxy.Factory.newPointProxy(loc.__fieldGetx() + 17, loc.__fieldGety());
                }
            }
        }
        return null;
    }

    /**
     * Iconify the given Shell. Don't support iconification of Dialogs at this point (although maybe
     * should).
     */
    public void iconify(final ShellProxy shell) {
        checkWidget(shell);
        PointProxy loc = getIconifyLocation(shell);
        if (loc != null)
            mouseMove(shell, loc.__fieldGetx(), loc.__fieldGety());
        syncExec(new Runnable() {
            public void run() {
                shell.setMinimized(true);
            }
        });
    }

    public void deiconify(ShellProxy shell) {
        normalize(shell);
    }

    public void normalize(final ShellProxy shell) {
        checkWidget(shell);
        syncExec(new Runnable() {
            public void run() {
                shell.setMinimized(false);
                shell.setMaximized(false);
            }
        });
    }

    /**
     * Make the window full size
     */
    public void maximize(final ShellProxy shell) {
        checkWidget(shell);
        PointProxy loc = getMaximizeLocation(shell);
        if (loc != null)
            mouseMove(shell, loc.__fieldGetx(), loc.__fieldGety());
        syncExec(new Runnable() {
            public void run() {
                shell.setMaximized(true);

                // if maximizing failed and we can resize, resize to fit the screen
                if (!shell.getMaximized() && (shell.getStyle() & SWTProxy.RESIZE) == SWTProxy.RESIZE) {
                    RectangleProxy screen = shell.getDisplay().getBounds();
                    shell.setLocation(screen.__fieldGetx(), screen.__fieldGety());
                    shell.setSize(screen.__fieldGetwidth(), screen.__fieldGetheight());
                }
            }
        });
    }

    public static Class getCanonicalClass(Class refClass) {
        // Don't use classnames from anonymous inner classes...
        // Don't use classnames from platform LAF classes...
        while (refClass.getName().indexOf("$") != -1
                || refClass.getName().startsWith("javax.swing.plaf")
                || refClass.getName().startsWith("com.apple.mrj"))
            refClass = refClass.getSuperclass();
        return refClass;
    }

    /**
     * TODO add toString methods that use the widget name property to provide a more concise
     * representation of the Widget.
     */

    /**
     * Return the numeric event ID corresponding to the given string.
     */
    public static int getEventID(Class cls, String id) {
        return (Integer) ReflectionUtils.getField(cls, id);
    }

    /**
     * TODO MAYBE add the following methods that are useful to testers: - getModifiers(String) OK -
     * getModifiers(int,boolean,boolean) OK - getKeyModifiers(int) OK - getMouseModifiers(int) OK -
     * getModifiers(Event) X - getKeyCode(int) X - getKeyCode(String) X
     */

    /**
     * Convert the string representation into the actual modifier mask. NOTE: this ignores any
     * character stored as a unicode char
     */
    /**
     * TODO Fix this so that it will parse out chars as well
     */
    public static int getModifiers(String mods) {
        int value = 0;
        if (mods != null && !mods.equals("")) {
            StringTokenizer st = new StringTokenizer(mods, "| ");
            while (st.hasMoreTokens()) {
                String flag = st.nextToken();
                if (POPUP_MODIFIER.equals(flag))
                    value |= BUTTON_POPUP;
                else if (TERTIARY_MODIFIER.equals(flag))
                    value |= TERTIARY_MASK;
                else if (!flag.equals("0") && flag.indexOf('\'') == -1)
                    value |= (Integer) ReflectionUtils.getField(SWTProxy.class, flag);
            }
        }
        return value;
    }

    /**
     * Provides a String representation of the mouse modifiers in the given accelerator.
     */
    public String getAcceleratorMouseString(int accelerator) {
        return getAcceleratorString(accelerator, false, true);
    }

    /**
     * Provides a String representation of a given accelerator.
     */
    public String getAcceleratorString(int accelerator, boolean key, boolean mouse) {
        String res = "{ ";
        int count = 0;

        if (mouse) {
            if ((accelerator & BUTTON1) == BUTTON1) {
                if (count != 0)
                    res += "| ";
                res += "SWT.BUTTON1 ";
                count++;
            }
            if ((accelerator & BUTTON2) == BUTTON2) {
                if (count != 0)
                    res += "| ";
                res += "SWT.BUTTON2 ";
                count++;
            }
            if ((accelerator & BUTTON3) == BUTTON3) {
                if (count != 0)
                    res += "| ";
                res += "SWT.BUTTON3 ";
                count++;
            }
        }

        if (key) {
            // first, check modifier keys
            if ((accelerator & ALT) == ALT) {
                if (count != 0)
                    res += "| ";
                res += "SWT.ALT ";
                count++;
            }
            if ((accelerator & SHIFT) == SHIFT) {
                if (count != 0)
                    res += "| ";
                res += "SWT.SHIFT ";
                count++;
            }
            if ((accelerator & CTRL) == CTRL) {
                if (count != 0)
                    res += "| ";
                res += "SWT.CTRL ";
                count++;
            }
            if ((accelerator & COMMAND) == COMMAND) {
                if (count != 0)
                    res += "| ";
                res += "SWT.COMMAND ";
                count++;
            }

            // now, look at the keystroke (if any)
            int keyCode = accelerator & KEY_MASK;
            if ((KEYCODE_BIT & keyCode) != 0 && keyCode != 0) { // accelerator contains a
                // keycode
                if (keyCode == ARROW_UP) {
                    res += (count != 0) ? "| " : "";
                    res += "SWT.ARROW_UP";
                    count++;
                } else if (keyCode == ARROW_DOWN) {
                    res += (count != 0) ? "| " : "";
                    res += "SWT.ARROW_DOWN";
                    count++;
                } else if (keyCode == ARROW_LEFT) {
                    res += (count != 0) ? "| " : "";
                    res += "SWT.ARROW_LEFT";
                    count++;
                } else if (keyCode == ARROW_RIGHT) {
                    res += (count != 0) ? "| " : "";
                    res += "SWT.ARROW_RIGHT";
                    count++;
                } else if (keyCode == PAGE_UP) {

                    res += (count != 0) ? "| " : "";
                    res += "SWT.PAGE_UP";
                    count++;
                } else if (keyCode == PAGE_DOWN) {
                    res += (count != 0) ? "| " : "";
                    res += "SWT.PAGE_DOWN";
                    count++;
                } else if (keyCode == HOME) {

                    res += (count != 0) ? "| " : "";
                    res += "SWT.HOME";
                    count++;
                } else if (keyCode == END) {
                    res += (count != 0) ? "| " : "";
                    res += "SWT.END";
                    count++;
                } else if (keyCode == INSERT) {
                    res += (count != 0) ? "| " : "";
                    res += "SWT.INSERT";
                    count++;
                } else if (keyCode == F1) {
                    res += (count != 0) ? "| " : "";
                    res += "SWT.F1";
                    count++;
                } else if (keyCode == F2) {
                    res += (count != 0) ? "| " : "";
                    res += "SWT.F2";
                    count++;
                } else if (keyCode == F3) {
                    res += (count != 0) ? "| " : "";
                    res += "SWT.F3";
                    count++;
                } else if (keyCode == F4) {
                    res += (count != 0) ? "| " : "";
                    res += "SWT.F4";
                    count++;
                } else if (keyCode == F5) {
                    res += (count != 0) ? "| " : "";
                    res += "SWT.F5";
                    count++;
                } else if (keyCode == F6) {
                    res += (count != 0) ? "| " : "";
                    res += "SWT.F6";
                    count++;
                } else if (keyCode == F7) {
                    res += (count != 0) ? "| " : "";
                    res += "SWT.F7";
                    count++;
                } else if (keyCode == F8) {
                    res += (count != 0) ? "| " : "";
                    res += "SWT.F8";
                    count++;
                } else if (keyCode == F9) {
                    res += (count != 0) ? "| " : "";
                    res += "SWT.F9";
                    count++;
                } else if (keyCode == F10) {
                    res += (count != 0) ? "| " : "";
                    res += "SWT.F10";
                    count++;
                } else if (keyCode == F11) {
                    res += (count != 0) ? "| " : "";
                    res += "SWT.F11";
                    count++;
                } else if (keyCode == F12) {
                    res += (count != 0) ? "| " : "";
                    res += "SWT.F12";
                    count++;
                }
            } else if (keyCode != 0) {// accelerator contains a unicode character
                if (count != 0)
                    res += "| ";
                res += "'(char)keyCode'";
                count++;
            }
        }

        res += "}";
        return res;
    }

    /**
     * returns a String representation of the key modifiers in the given accelerator
     */
    public String getAcceleratorKeyString(int accelerator) {
        return getAcceleratorString(accelerator, true, false);
    }

    /**
     * Strip the package from the class name.
     */
    public static String simpleClassName(Class cls) {
        String name = cls.getName();
        int dot = name.lastIndexOf(".");
        return name.substring(dot + 1, name.length());
    }

    // private static java.util.ArrayList bugList = null;
    // private static boolean gotBug1Event = false;
    /**
     * Check for all known robot-related bugs that will affect Abbot operation. Returns a String for
     * each bug detected on the current system.
     */
    /*
    * public static String[] bugCheck(final Window window) { if (bugList == null) { bugList = new
    * java.util.ArrayList(); if (Platform.isWindows() && Platform.getJavaVersionNumber() <
    * Platform.JAVA_1_4) { Log.debug("Checking for w32 bugs"); final int x = window.getWidth() / 2;
    * final int y = window.getHeight() / 2; final int mask = InputEvent.BUTTON2_MASK; MouseAdapter
    * ma = new MouseAdapter() { public void mouseClicked(MouseEvent ev) { Log.debug("Got " +
    * Robot.toString(ev)); gotBug1Event = true; // w32 acceleration bug if (ev.getSource() !=
    * window || ev.getX() != x || ev.getY() != y) { bugList.add(Strings.get("Bug1")); } // w32
    * mouse button mapping bug if ((ev.getModifiers() & mask) != mask) {
    * bugList.add(Strings.get("Bug2")); } } }; window.addMouseListener(ma); Robot robot = new
    * Robot(); robot.click(window, x, y, mask); robot.waitForIdle();
    * window.removeMouseListener(ma); window.toFront(); // Bogus acceleration may mean the event
    * goes entirely // elsewhere if (!gotBug1Event) { bugList.add(0, Strings.get("Bug1")); } } }
    * return (String[])bugList.toArray(new String[bugList.size()]); }
    */

    /**
     * Place the pointer in the center of the display
     */
    public void resetPointer() {
        RectangleProxy screen = swtRobot.getDisplay().getBounds();
        mouseMove(screen.__fieldGetwidth() / 2, screen.__fieldGetheight() / 2);
        mouseMove(screen.__fieldGetwidth() / 2 - 1, screen.__fieldGetheight() / 2 - 1);
    }

    /**
     * Throws an {@link IllegalArgumentException} if the specified {@link DisplayProxy} is null or is not
     * this {@link Robot}'s.
     *
     * @param display the {@link DisplayProxy}
     */
    protected void checkDisplay(DisplayProxy display) {
        if (display == null) {
            throw new IllegalArgumentException("display is null");
        }
        if (!display.equals(getDisplay())) {
            throw new IllegalArgumentException("display is invalid");
        }
    }

    /**
     * Throws an {@link IllegalArgumentException} if the specified {@link WidgetProxy} is null or its
     * {@link DisplayProxy} is not this {@link Robot}'s.
     *
     * @param widget the {@link WidgetProxy}
     */
    protected void checkWidget(WidgetProxy widget) {
        if (widget == null)
            throw new IllegalArgumentException("widget is null");
        if (!widget.isDisposed())
            checkDisplay(widget.getDisplay());
    }

    /**
     * Throws an {@link IllegalArgumentException} if any of the specified {@link WidgetProxy}s are null
     * or their {@link DisplayProxy} is not this {@link Robot}'s.
     *
     * @param widgets the {@link WidgetProxy}s
     */
    protected void checkWidgets(Collection widgets) {
        for (Iterator iterator = widgets.iterator(); iterator.hasNext();) {
            WidgetProxy widget = (WidgetProxy) iterator.next();
            checkWidget(widget);
        }
    }

    /**
     * @throws IllegalStateException if called on this {@link Robot}'s {@link DisplayProxy} {@link Thread}.
     */
    protected void checkThread() {
        if (isOnDisplayThread())
            throw new RuntimeException("invalid thread");
    }

    protected boolean isOnDisplayThread() {
        return getDisplay().getThread() == Thread.currentThread();
    }

    /**
     * Provides a more concise representation of the component than the default
     * Component.toString().
     */
    // FIXME getTag has too much overhead to be calling this frequently
    public String toString(WidgetProxy widget) {

        if (widget == null)
            return "(null)";
        checkWidget(widget);

        Class cls = widget.getClass();

        WidgetTester wt = WidgetTester.getTester(widget);
        String name = (String) wt.getData(widget, "name");

        // WidgetFinder finder = DefaultWidgetFinder.getFinder();
        // String name = finder.getWidgetName(widget);

        if (name == null)
            name = WidgetTester.getTag(widget);
        cls = getCanonicalClass(cls);
        String cname = simpleClassName(widget.getClass());
        if (!cls.equals(widget.getClass()))
            cname += "/" + simpleClassName(cls);
        if (name == null)
            name = cname + " instance";
        else
            name = "'" + name + "' (" + cname + ")";
        return name;
    }

    /**
     * OS X (as of 1.3.1, v10.1.5), will sometimes send a click to the wrong component after a mouse
     * move. This continues to be an issue in 1.4.1
     * <p/>
     * Linux x86 (1.3.1) has a similar problem, although it manifests it at different times (need a
     * bug test case for this one).
     * <p/>
     * Solaris and HPUX probably share code with the linux VM implementation, so the bug there is
     * probably identical.
     * <p/>
     */
    // FIXME add tests to determine presence of bug.
    public boolean hasRobotMotionBug() {
        return (Platform.isOSX()
                || (!Platform.isWindows() && Platform.JAVA_VERSION < Platform.JAVA_1_4) || Boolean
                .getBoolean("abbot.robot.need_jitter"));
    }
}