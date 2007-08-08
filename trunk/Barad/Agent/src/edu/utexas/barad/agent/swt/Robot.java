package edu.utexas.barad.agent.swt;

import edu.utexas.barad.agent.exceptions.WaitTimedOutException;
import edu.utexas.barad.agent.swt.proxy.SWTProxy;
import edu.utexas.barad.agent.swt.proxy.graphics.*;
import edu.utexas.barad.agent.swt.proxy.widgets.DisplayProxy;
import edu.utexas.barad.agent.swt.proxy.widgets.EventProxy;
import edu.utexas.barad.agent.swt.proxy.widgets.MonitorProxy;

/**
 * University of Texas at Austin
 * Barad Project, Jul 25, 2007
 */
public class Robot {
    /**
     * The number of pixels in each dimension we need to move the mouse cursor in order to trigger a
     * DragDetect event. This could be 1, but GEF (on Windows, at least) seems to need it to be at
     * least 2.
     */
    private static final int DRAG_NUDGE = 2;

    /**
     * The default {@link Robot} instance, based on the default {@link edu.utexas.barad.agent.swt.proxy.widgets.DisplayProxy}.
     *
     * @see #getDefault()
     */
    private static Robot Default;

    /**
     * Gets the default {@link Robot}, based on the default {@link DisplayProxy}, creating a new one if
     * necessary.
     *
     * @return the default {@link Robot}
     */
    public static synchronized Robot getDefault() {
        if (Default == null)
            Default = new Robot(DisplayProxy.Factory.getDefault());
        return Default;
    }

    /**
     * This {@link Robot}'s {@link DisplayProxy}.
     */
    private final DisplayProxy display;

    /**
     * Constructs a new {@link Robot} for the specified {@link edu.utexas.barad.agent.swt.proxy.widgets.DisplayProxy}.
     * <p/>
     * <b>Note:</b> You should probably call factory method {@link #getDefault()} instead of
     * explicitly constructing a new {@link Robot}.
     *
     * @throws IllegalArgumentException if <code>display</code> is null
     */
    public Robot(DisplayProxy display) {
        if (display == null)
            throw new IllegalArgumentException("display is null");
        this.display = display;
    }

    /**
     * Gets this {@link Robot}'s {@link DisplayProxy}.
     *
     * @return this {@link Robot}'s {@link DisplayProxy}
     */
    public DisplayProxy getDisplay() {
        return display;
    }

    /*
    * High-level user input event generation support.
    */

    /**
     * Generates a user input event to move the mouse cursor to a specified display coordinate.
     *
     * @param x the x-position
     * @param y the y-position
     */
    public synchronized void mouseMove(int x, int y) {
        checkThread();
        checkLocation(x, y);
        postMouseMove(x, y);
    }

    /**
     * Presses all mouse buttons contained in the buttons mask (zero or more of
     * {@link SWTProxy#BUTTON_MASK}).
     *
     * @param buttons SWT accelerator containing the mouse buttons to be pressed
     * @see SWTProxy (for more info on accelerators)
     * @see #mouseRelease
     * @see SWTProxy#BUTTON_MASK
     */
    public synchronized void mousePress(int buttons) {
        checkButtons(buttons);
        checkThread();

        if ((buttons & SWTProxy.BUTTON1) != 0)
            postMousePress(1);
        if ((buttons & SWTProxy.BUTTON2) != 0)
            postMousePress(2);
        if ((buttons & SWTProxy.BUTTON3) != 0)
            postMousePress(3);
        if ((buttons & SWTProxy.BUTTON4) != 0)
            postMousePress(4);
        if ((buttons & SWTProxy.BUTTON5) != 0)
            postMousePress(5);
    }

    /**
     * Releases all mouse buttons contained in a given accelerator.
     *
     * @param buttons SWT accelerator containing the mouse buttons to be released
     * @see SWTProxy for more info on accelerators
     * @see #mousePress for how the accelerator is interpreted
     */
    public synchronized void mouseRelease(int buttons) {
        checkThread();
        checkButtons(buttons);

        if ((buttons & SWTProxy.BUTTON5) != 0)
            postMouseRelease(5);
        if ((buttons & SWTProxy.BUTTON4) != 0)
            postMouseRelease(4);
        if ((buttons & SWTProxy.BUTTON3) != 0)
            postMouseRelease(3);
        if ((buttons & SWTProxy.BUTTON2) != 0)
            postMouseRelease(2);
        if ((buttons & SWTProxy.BUTTON1) != 0)
            postMouseRelease(1);
    }

    /**
     * Presses all keys in the specified accelerator. The accelerator can contain:
     * <ul>
     * <li>zero or more modifier key masks ({@link SWTProxy#CTRL}, {@link SWTProxy#ALT}, {@link SWTProxy#SHIFT},
     * etc.);</li>
     * <li>at most one character or keycode.</li>
     * <ul>
     * <li>To type a Unicode character use the Unicode character value (e.g., <code>'k'</code>,
     * <code>'K'</code>, <code>'5'</code>, etc.).
     * <li>For non-character keys use the keycodes defined in {@link SWTProxy}. For example, use
     * {@link SWTProxy#F1} for F1.</li>
     * </ul>
     * </ul>
     * <strong>Note:</strong>
     * <ul>
     * <li>Character processing is case-sensitive, so {@link SWTProxy#SHIFT} is implied if the character
     * is uppercase or otherwise requires the <code>SWTProxy.SHIFT</code> key.</li>
     * <li>An accelerator can contain zero or more modifier key masks but at most one character or
     * keycode.</li>
     * <li>Characters that do not appear on a US keyboard are ignored.</li>
     * <li>Mouse-button masks are ignored.</li>
     * </ul>
     *
     * @param accelerator SWT accelerator containing the keys to be pressed
     * @see #keyRelease(int)
     */
    public synchronized void keyPress(int accelerator) {
        checkThread();

        accelerator = getNormalizedAccelerator(accelerator);
        if ((accelerator & SWTProxy.CTRL) != 0)
            postKeyPress(SWTProxy.CTRL, '\0');
        if ((accelerator & SWTProxy.ALT) != 0)
            postKeyPress(SWTProxy.ALT, '\0');
        if ((accelerator & SWTProxy.SHIFT) != 0)
            postKeyPress(SWTProxy.SHIFT, '\0');

        if ((accelerator & SWTProxy.KEYCODE_BIT) != 0) {
            postKeyPress(accelerator & SWTProxy.KEY_MASK, '\0');
        } else {
            char c = (char) (accelerator & SWTProxy.KEY_MASK);
            if (c != '\0')
                postKeyPress(0, c);
        }
    }

    /**
     * Releases all keys in the specified accelerator.
     *
     * @param accelerator SWT accelerator containing the keys to be released
     * @see #keyPress(int) for information about how the accelerator is interpreted
     */
    public synchronized void keyRelease(int accelerator) {
        checkThread();

        accelerator = getNormalizedAccelerator(accelerator);
        if ((accelerator & SWTProxy.KEYCODE_BIT) != 0) {
            postKeyRelease(accelerator & SWTProxy.KEY_MASK, '\0');
        } else {
            char c = (char) (accelerator & SWTProxy.KEY_MASK);
            if (c != '\0')
                postKeyRelease(0, c);
        }
        if ((accelerator & SWTProxy.SHIFT) != 0)
            postKeyRelease(SWTProxy.SHIFT, '\0');
        if ((accelerator & SWTProxy.ALT) != 0)
            postKeyRelease(SWTProxy.ALT, '\0');
        if ((accelerator & SWTProxy.CTRL) != 0)
            postKeyRelease(SWTProxy.CTRL, '\0');
    }

    /**
     * Gets a "normalized" version of an accelerator. If the accelerator contains a virtual key code
     * or the null key character then the accelerator is returned unchanged. If the
     * {@link SWTProxy#SHIFT} modifier is set then the character key is adjusted, if necessary, to ensure
     * that it is the shifted key value. Otherwise, if the character key requires {@link SWTProxy#SHIFT}
     * in order to be typed then we set the {@link SWTProxy#SHIFT} modifier.
     *
     * @param accelerator a key accelerator
     * @return a possibly modified accelerator
     * @see #keyPress(int)
     * @see #keyRelease(int)
     */
    private static int getNormalizedAccelerator(int accelerator) {
        if ((accelerator & SWTProxy.KEYCODE_BIT) == 0) {
            char ch = (char) (accelerator & SWTProxy.KEY_MASK);
            if (ch != '\0') {
                if ((accelerator & SWTProxy.SHIFT) != 0) {
                    char chShifted = getShiftedCharacter(ch);
                    if (chShifted != ch) {
                        accelerator &= ~SWTProxy.KEY_MASK;
                        accelerator |= chShifted;
                    }
                } else {
                    if (isShiftNeeded(ch))
                        accelerator |= SWTProxy.SHIFT;
                }
            }
        }
        return accelerator;
    }

    /**
     * Returns whether or not the specified key character needs the shift key down in order for it
     * to be typed.
     *
     * @param c the character
     * @return <code>true</code> if the specified character needs the shift key down in order for
     *         it to be typed, <code>false</code> otherwise
     * @see #getNormalizedAccelerator(int)
     */
    private static boolean isShiftNeeded(char c) {
        if (c >= 'A' && c <= 'Z')
            return true;
        if (c >= 'a' && c <= 'z')
            return false;
        if (c >= '0' && c <= '9')
            return false;
        // boolean isGTK = SWTProxy.getPlatform().equals("gtk");
        switch (c) {
            case'~':
            case'!':
            case'@':
            case'#':
            case'$':
            case'%':
            case'^':
            case'&':
            case'*':
            case'(':
            case')':
            case'_':
            case'+':
            case'{':
            case'}':
            case'|':
            case':':
            case'"':
            case'<':
            case'>':
            case'?':
                return true;
            case (char) 27: // SWTProxy.ESC
            case (char) 127: // SWTProxy.DEL
            case' ':
            case'\t':
            case'\n':
            case'\r':
            case'`':
            case'-':
            case'=':
            case'[':
            case']':
            case'\\':
            case';':
            case'\'':
            case',':
            case'.':
            case'/':
                return false;
        }
        throw new IllegalArgumentException("invalid key character" + c);
    }

    /**
     * Gets the character you'd get if you hit the specified character key while the shift key is
     * down.
     *
     * @param c the character key
     * @return the shifted value of <code>key</code>
     * @see #getNormalizedAccelerator(int)
     */
    private static char getShiftedCharacter(char c) {
        if (c >= 'A' && c <= 'Z')
            return c;
        if (c >= 'a' && c <= 'z')
            return (char) ((c - 'a') + 'A');

        // boolean isGTK = SWTProxy.getPlatform().equals("gtk");
        switch (c) {
            case'~':
            case'!':
            case'@':
            case'#':
            case'$':
            case'%':
            case'^':
            case'&':
            case'*':
            case'(':
            case')':
            case'_':
            case'+':
            case'{':
            case'}':
            case'|':
            case':':
            case'"':
            case'<':
            case'>':
            case'?':
            case (char) 27: // SWTProxy.ESC
            case (char) 127: // SWTProxy.DEL
            case' ':
            case'\t':
            case'\n':
            case'\r':
                return c;
            case'1':
                return '!';
            case'2':
                return '@';
            case'3':
                return '#';
            case'4':
                return '$';
            case'5':
                return '%';
            case'6':
                return '^';
            case'7':
                return '&';
            case'8':
                return '*';
            case'9':
                return '(';
            case'0':
                return ')';
            case'`':
                return '~';
            case'-':
                return '_';
            case'=':
                return '+';
            case'[':
                return '{';
            case']':
                return '}';
            case'\\':
                return '|';
            case';':
                return ':';
            case'\'':
                return '"';
            case',':
                return '<';
            case'.':
                return '>';
            case'/':
                return '?';
        }
        throw new IllegalArgumentException("invalid key character" + c);
    }

    public synchronized void mouseDrag(int buttons) {
        checkThread();
        checkButtons(buttons);

        PointProxy p = getNudgeLocation();
        mousePress(buttons);
        postMouseMove(p.__fieldGetx(), p.__fieldGety());
    }

    private PointProxy getNudgeLocation() {
        PointProxy p = getCursorLocation();
        for (int x = p.__fieldGetx() - DRAG_NUDGE; x <= p.__fieldGetx() + DRAG_NUDGE; x += 2 * DRAG_NUDGE) {
            for (int y = p.__fieldGety() - DRAG_NUDGE; y <= p.__fieldGety() + DRAG_NUDGE; y += 2 * DRAG_NUDGE) {
                if (isValidLocation(x, y)) {
                    p.__fieldSetx(x);
                    p.__fieldSety(y);
                    return p;
                }
            }
        }
        throw new RuntimeException("cannot nudge");
    }

    public synchronized void mouseDrop(int buttons) {
        mouseRelease(buttons);
    }

    /**
     * Generates a user input event to move the scroll wheel on wheel-equipped mice.
     *
     * @param count the number of "notches" to move the mouse wheel. Negative values indicate movement
     *              up/away from the user. Positive values indicate movement down/towards the user.
     * @param page  <code>true</code> or <code>false</code> indicating page or line scrolling,
     *              respectively
     */
    public synchronized void mouseWheel(int count, boolean page) {
        checkThread();
        // TODO checkCount();
        postMouseWheel(count, page);
    }

    /*
    * User input event generation support.
    */

    /*
    * Screen capture support.
    */

    /**
     * Creates an {@link ImageProxy} containing pixels read from a rectangular area on this {@link Robot}'s
     * {@link DisplayProxy}.
     * <p/>
     * <b>Note:</b> The <b><i>caller</i></b> is responsible for disposing the returned
     * {@link ImageProxy}.
     *
     * @param rectangle the {@link RectangleProxy} area to capture in display coordinates
     * @return the captured {@link ImageProxy}
     */
    public ImageProxy createScreenCapture(final RectangleProxy rectangle) {
        checkThread();
        // TODO checkRectangle(rectangle);
        return capture(rectangle);
    }

    /**
     * Creates an {@link ImageProxy} containing pixels read from this {@link Robot}'s entire
     * {@link DisplayProxy}.
     * <p/>
     * <b>Note:</b> The <b><i>caller</i></b> is responsible for disposing the returned
     * {@link ImageProxy}.
     *
     * @return the captured {@link ImageProxy}
     */
    public ImageProxy createScreenCapture() {
        checkThread();
        return capture(display.getBounds());
    }

    /**
     * Returns the {@link ColorProxy} of a pixel at specified display coordinates.
     * <p/>
     * <strong>Note:</strong> The <b><i>caller</i></b> is responsible for disposing the
     * {@link ColorProxy}.
     * <p/>
     * TODO Seems like there should be a more efficient way to get the color of a single pixel.
     * Currently, we're essentially doing a single pixel screen area capture, which seems
     * excessively heavyweight.
     *
     * @param x the x position of the pixel
     * @param y the y position of the pixel
     * @return the {@link ColorProxy} of the pixel
     */
    public ColorProxy getPixelColor(int x, int y) {
        checkThread();
        checkLocation(x, y);

        ImageProxy image = capture(RectangleProxy.Factory.newRectangleProxy(x, y, 1, 1));
        try {
            ImageDataProxy data = image.getImageData();
            int pixel = data.getPixel(0, 0);
            RGBProxy rgb = data.__fieldGetpalette().getRGB(pixel);
            return ColorProxy.Factory.newColorProxy(display, rgb);
        } finally {
            if (image != null)
                image.dispose();
        }
    }

    private ImageProxy capture(final RectangleProxy rectangle) {
        final ImageProxy image = ImageProxy.Factory.newImageProxy(display, rectangle.__fieldGetwidth(), rectangle.__fieldGetheight());
        final GCProxy[] gc = new GCProxy[1];
        try {
            display.syncExec(new Runnable() {
                public void run() {
                    gc[0] = GCProxy.Factory.newGCProxy(display);
                    gc[0].copyArea(image, rectangle.__fieldGetx(), rectangle.__fieldGety());
                }
            });
        } finally {
            if (gc[0] != null)
                gc[0].dispose();
        }
        return image;
    }

    /*
    * End of screen capture support.
    */

    /*
    * Start of low-level user event generation support.
    */

    private void postMouseMove(int x, int y) {
        EventProxy event = EventProxy.Factory.newEventProxy();
        event.__fieldSettype(SWTProxy.MouseMove);
        event.__fieldSetx(x);
        event.__fieldSety(y);
        post(event);
    }

    /**
     * Generates a user input event to press a mouse button.
     *
     * @param button The number of the button to be pressed (i.e., 1, 2, 3, etc.)
     */
    private void postMousePress(int button) {
        EventProxy event = EventProxy.Factory.newEventProxy();
        event.__fieldSettype(SWTProxy.MouseDown);
        event.__fieldSetbutton(button);
        post(event);
    }

    /**
     * Generates a user input event to release a mouse button.
     *
     * @param button The number of the button to be pressed (i.e., 1, 2, 3, etc.)
     */
    private void postMouseRelease(int button) {
        EventProxy event = EventProxy.Factory.newEventProxy();
        event.__fieldSettype(SWTProxy.MouseUp);
        event.__fieldSetbutton(button);
        post(event);
    }

    private void postKeyPress(int keyCode, char character) {
        EventProxy event = EventProxy.Factory.newEventProxy();
        event.__fieldSettype(SWTProxy.KeyDown);
        event.__fieldSetkeyCode(keyCode);
        event.__fieldSetcharacter(character);
        post(event);
    }

    private void postKeyRelease(int keyCode, char character) {
        EventProxy event = EventProxy.Factory.newEventProxy();
        event.__fieldSettype(SWTProxy.KeyUp);
        event.__fieldSetkeyCode(keyCode);
        event.__fieldSetcharacter(character);
        post(event);
    }

    private void postMouseWheel(int count, boolean page) {
        EventProxy event = EventProxy.Factory.newEventProxy();
        event.__fieldSettype(SWTProxy.MouseWheel);
        event.__fieldSetdetail(page ? SWTProxy.SCROLL_PAGE : SWTProxy.SCROLL_LINE);
        event.__fieldSetcount(count);
        post(event);
    }

    /**
     * Posts an {@link EventProxy} on the UI input queue.
     *
     * @param event the {@link EventProxy} to post
     * @throws RuntimeException if it fails
     * @see DisplayProxy#post(EventProxy)
     */
    private void post(EventProxy event) {

        // System.err.println(Events.toString(" ", event));

        // Special cases.
        // switch (event.type) {
        // case SWTProxy.MouseDown:
        // case SWTProxy.MouseMove:
        // case SWTProxy.MouseUp:
        // case SWTProxy.MouseWheel:
        // if (!post33(event))
        // throw new RuntimeException("post failed: " + event);
        // return;
        // }

        // Normal case.
        if (!display.post(event))
            throw new RuntimeException("post failed: " + event);
    }

    /** @see #post33(EventProxy) */
    // private static final int OS_MOUSEEVENTF_VIRTUALDESK = 0x4000;
    /** @see #post33(EventProxy) */
    // private static final int OS_MOUSEEVENTF_WHEEL = 0x0800;
    /** @see #post33(EventProxy) */
    // private static final int OS_MOUSEEVENTF_XDOWN = 0x0080;
    /** @see #post33(EventProxy) */
    // private static final int OS_MOUSEEVENTF_XUP = 0x0100;
    /**
     * This method exists only to work around a handful of issues with {@link DisplayProxy#post(EventProxy)}
     * in Eclipse 3.2.x (which we expect to be resolved in Eclipse 3.3, after which this method will
     * go away):
     * <dl>
     * <dt>{@link SWTProxy#MouseMove}</dt>
     * <dd>Fixes bugzilla 172179 "{@link DisplayProxy#post(EventProxy)} with {@link SWTProxy#MouseMove} moves to
     * wrong location" (https://bugs.eclipse.org/bugs/show_bug.cgi?id=172179).</dd>
     * <dt>{@link SWTProxy#MouseMove}</dt>
     * <dd>Support for multiple monitors.</dd>
     * <dt>{@link SWTProxy#MouseDown},<br>
     * {@link SWTProxy#MouseUp}</dt>
     * <dd>Support for buttons {@link SWTProxy#BUTTON4} and {@link SWTProxy#BUTTON5}.</dd>
     * <dt>{@link SWTProxy#MouseWheel}</dt>
     * <dd>Support for mouse wheel events. See bugzilla 172167 "Make {@link DisplayProxy#post(EventProxy)}
     * support generating {@link SWTProxy#MouseWheel} events"
     * (https://bugs.eclipse.org/bugs/show_bug.cgi?id=172167).</dd>
     * </dl>
     * <p>
     * Current code adapted from Eclipse 3.3 build: eclipse-SDK-N20070220-0010-win32
     *
     * @see #post(EventProxy)
     */
    // private boolean post33(EventProxy event) {
    // if (display.isDisposed())
    // SWTProxy.error(SWTProxy.ERROR_DEVICE_DISPOSED);
    // if (event == null)
    // SWTProxy.error(SWTProxy.ERROR_NULL_ARGUMENT);
    // int type = event.type;
    // switch (type) {
    // case SWTProxy.MouseDown:
    // case SWTProxy.MouseMove:
    // case SWTProxy.MouseUp:
    // case SWTProxy.MouseWheel: {
    // MOUSEINPUT inputs = new MOUSEINPUT();
    // if (type == SWTProxy.MouseMove) {
    // inputs.dwFlags = OS.MOUSEEVENTF_MOVE | OS.MOUSEEVENTF_ABSOLUTE;
    // int x = 0, y = 0, width = 0, height = 0;
    // if (OS.WIN32_VERSION >= OS.VERSION(5, 0)) {
    // inputs.dwFlags |= OS_MOUSEEVENTF_VIRTUALDESK;
    // x = OS.GetSystemMetrics(OS.SM_XVIRTUALSCREEN);
    // y = OS.GetSystemMetrics(OS.SM_YVIRTUALSCREEN);
    // width = OS.GetSystemMetrics(OS.SM_CXVIRTUALSCREEN);
    // height = OS.GetSystemMetrics(OS.SM_CYVIRTUALSCREEN);
    // } else {
    // width = OS.GetSystemMetrics(OS.SM_CXSCREEN);
    // height = OS.GetSystemMetrics(OS.SM_CYSCREEN);
    // }
    // inputs.dx = ((event.x - x) * 65535 + width - 2) / (width - 1);
    // inputs.dy = ((event.y - y) * 65535 + height - 2) / (height - 1);
    // } else {
    // if (type == SWTProxy.MouseWheel) {
    // if (OS.WIN32_VERSION < OS.VERSION(5, 0))
    // return false;
    // inputs.dwFlags = OS_MOUSEEVENTF_WHEEL;
    // switch (event.detail) {
    // case SWTProxy.SCROLL_PAGE:
    // inputs.mouseData = event.count * OS.WHEEL_DELTA;
    // break;
    // case SWTProxy.SCROLL_LINE:
    // int[] value = new int[1];
    // OS.SystemParametersInfo(OS.SPI_GETWHEELSCROLLLINES, 0, value, 0);
    // inputs.mouseData = event.count * OS.WHEEL_DELTA / value[0];
    // break;
    // default:
    // return false;
    // }
    // } else {
    // switch (event.button) {
    // case 1:
    // inputs.dwFlags = type == SWTProxy.MouseDown ? OS.MOUSEEVENTF_LEFTDOWN
    // : OS.MOUSEEVENTF_LEFTUP;
    // break;
    // case 2:
    // inputs.dwFlags = type == SWTProxy.MouseDown ? OS.MOUSEEVENTF_MIDDLEDOWN
    // : OS.MOUSEEVENTF_MIDDLEUP;
    // break;
    // case 3:
    // inputs.dwFlags = type == SWTProxy.MouseDown ? OS.MOUSEEVENTF_RIGHTDOWN
    // : OS.MOUSEEVENTF_RIGHTUP;
    // break;
    // case 4: {
    // if (OS.WIN32_VERSION < OS.VERSION(5, 0))
    // return false;
    // inputs.dwFlags = type == SWTProxy.MouseDown ? OS_MOUSEEVENTF_XDOWN
    // : OS_MOUSEEVENTF_XUP;
    // inputs.mouseData = OS.XBUTTON1;
    // break;
    // }
    // case 5: {
    // if (OS.WIN32_VERSION < OS.VERSION(5, 0))
    // return false;
    // inputs.dwFlags = type == SWTProxy.MouseDown ? OS_MOUSEEVENTF_XDOWN
    // : OS_MOUSEEVENTF_XUP;
    // inputs.mouseData = OS.XBUTTON2;
    // break;
    // }
    // default:
    // return false;
    // }
    // }
    // }
    // int hHeap = OS.GetProcessHeap();
    // int pInputs = OS.HeapAlloc(hHeap, OS.HEAP_ZERO_MEMORY, INPUT.sizeof);
    // OS.MoveMemory(pInputs, new int[] { OS.INPUT_MOUSE }, 4);
    // OS.MoveMemory(pInputs + 4, inputs, MOUSEINPUT.sizeof);
    // boolean result = OS.SendInput(1, pInputs, INPUT.sizeof) != 0;
    // OS.HeapFree(hHeap, 0, pInputs);
    // return result;
    // }
    // }
    // return false;
    // }
    /*
    * End of low-level user event generation support.
    */

    /*
    * Argument checking utilities.
    */

    /**
     * @throws RuntimeException if called on this {@link Robot}'s {@link DisplayProxy} thread.
     */
    private void checkThread() {
        if (isOnDisplayThread())
            throw new IllegalArgumentException("invalid thread");
    }

    private void checkLocation(int x, int y) {
        if (!isValidLocation(x, y))
            throw new IllegalArgumentException("out of bounds: " + x + ',' + y);
    }

    /**
     * Check the validity of a button mask.
     *
     * @param buttons the mouse buttons to check
     * @throws IllegalArgumentException if the buttons are not valid
     * @see SWTProxy#BUTTON_MASK
     */
    private void checkButtons(int buttons) {
        if ((buttons | SWTProxy.BUTTON_MASK) != SWTProxy.BUTTON_MASK)
            throw new IllegalArgumentException("invalid buttons: " + buttons);
    }

    /*
    * End of argument checking utilities.
    */

    /*
    * Other utilities.
    */

    /**
     * @return <code>true</code> if the caller is running on this {@link Robot}'s {@link DisplayProxy}
     *         thread, <code>false</code> otherwise
     */
    public boolean isOnDisplayThread() {
        return display.getThread() == Thread.currentThread();
    }

    private static final long WAIT_IDLE_TIMEOUT = 60000L;

    private static final long WAIT_IDLE_INTERVAL = 100L;

    public void waitForIdle() {
        checkThread();

        // Queue up an asyncExec to set a flag when it runs. Because the DisplayProxy runs them in FIFO
        // order, ours will not run until everything that was previously in the queue has run and
        // completed.
        final boolean[] done = new boolean[1];
        getDisplay().asyncExec(new Runnable() {
            public void run() {
                done[0] = true;
            }
        });

        // Wait for the asyncExec to complete. If it times out we will assume it means that the
        // display thread is hung so we will do a System.exit().
        final long limit = System.currentTimeMillis() + WAIT_IDLE_TIMEOUT;
        while (!done[0]) {
            try {
                Thread.sleep(WAIT_IDLE_INTERVAL);
            } catch (InterruptedException exception) {
                // Empty block intended.
            }
            if (System.currentTimeMillis() > limit)
                throw new WaitTimedOutException();
        }

    }

    /**
     * Return <code>true</code> if a point is a valid display coordinate, <code>false</code>
     * otherwise.
     *
     * @param x the x coordinate
     * @param y the y coordinate
     * @return <code>true</code> if the point is a valid display coordinate, <code>false</code>
     *         otherwise
     */
    private boolean isValidLocation(int x, int y) {
        RectangleProxy bounds = getBounds();
        if (bounds.contains(x, y)) {
            MonitorProxy[] monitors = getMonitors();
            if (monitors.length == 1)
                return true;
            for (int i = 0; i < monitors.length; i++) {
                MonitorProxy monitor = monitors[i];
                bounds = monitor.getBounds();
                if (bounds.contains(x, y))
                    return true;
            }
        }
        return false;
    }

    private RectangleProxy getBounds() {
        final RectangleProxy[] bounds = new RectangleProxy[1];
        display.syncExec(new Runnable() {
            public void run() {
                bounds[0] = display.getBounds();
            }
        });
        return bounds[0];
    }

    private MonitorProxy[] getMonitors() {
        final MonitorProxy[][] monitors = new MonitorProxy[1][];
        display.syncExec(new Runnable() {
            public void run() {
                monitors[0] = display.getMonitors();
            }
        });
        return monitors[0];
    }

    private PointProxy getCursorLocation() {
        final PointProxy[] point = new PointProxy[1];
        display.syncExec(new Runnable() {
            public void run() {
                point[0] = display.getCursorLocation();
            }
        });
        return point[0];
    }

    /*
    * End of other utilities.
    */

    /*
    * Miscellaneous.
    */

    /**
     * @see Object#toString()
     */
    public String toString() {
        return String.format("%s [%s]", getClass().getName(), display);
    }
}