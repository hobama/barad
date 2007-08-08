package edu.utexas.barad.agent.swt.proxy;

import edu.utexas.barad.agent.AgentUtils;
import edu.utexas.barad.common.ReflectionUtils;

/**
 * University of Texas at Austin
 * Barad Project, Jul 20, 2007
 */
public final class SWTProxy implements SWTProxyMarker {
    private static final Class actualClass;

    static {
        actualClass = AgentUtils.swtClassForName("org.eclipse.swt.SWT");
    }

    public static final int RIGHT_TO_LEFT = (Integer) ReflectionUtils.getField(actualClass, "RIGHT_TO_LEFT");
    public static final int KEYCODE_BIT = (Integer) ReflectionUtils.getField(actualClass, "KEYCODE_BIT");
    public static final int KEY_MASK = (Integer) ReflectionUtils.getField(actualClass, "KEY_MASK");
    public static final int SHIFT = (Integer) ReflectionUtils.getField(actualClass, "SHIFT");
    public static final int ALT = (Integer) ReflectionUtils.getField(actualClass, "ALT");
    public static final int CTRL = (Integer) ReflectionUtils.getField(actualClass, "CTRL");
    public static final int BUTTON1 = (Integer) ReflectionUtils.getField(actualClass, "BUTTON1");
    public static final int BUTTON2 = (Integer) ReflectionUtils.getField(actualClass, "BUTTON2");
    public static final int BUTTON3 = (Integer) ReflectionUtils.getField(actualClass, "BUTTON3");
    public static final int BUTTON4 = (Integer) ReflectionUtils.getField(actualClass, "BUTTON4");
    public static final int BUTTON5 = (Integer) ReflectionUtils.getField(actualClass, "BUTTON5");
    public static final int BUTTON_MASK = (Integer) ReflectionUtils.getField(actualClass, "BUTTON_MASK");
    public static final int MODIFIER_MASK = (Integer) ReflectionUtils.getField(actualClass, "MODIFIER_MASK");
    public static final int F1 = (Integer) ReflectionUtils.getField(actualClass, "F1");
    public static final int F2 = (Integer) ReflectionUtils.getField(actualClass, "F2");
    public static final int F3 = (Integer) ReflectionUtils.getField(actualClass, "F3");
    public static final int F4 = (Integer) ReflectionUtils.getField(actualClass, "F4");
    public static final int F5 = (Integer) ReflectionUtils.getField(actualClass, "F5");
    public static final int F6 = (Integer) ReflectionUtils.getField(actualClass, "F6");
    public static final int F7 = (Integer) ReflectionUtils.getField(actualClass, "F7");
    public static final int F8 = (Integer) ReflectionUtils.getField(actualClass, "F8");
    public static final int F9 = (Integer) ReflectionUtils.getField(actualClass, "F9");
    public static final int F10 = (Integer) ReflectionUtils.getField(actualClass, "F10");
    public static final int F11 = (Integer) ReflectionUtils.getField(actualClass, "F11");
    public static final int F12 = (Integer) ReflectionUtils.getField(actualClass, "F12");
    public static final char ESC = (Character) ReflectionUtils.getField(actualClass, "ESC");
    public static final char DEL = (Character) ReflectionUtils.getField(actualClass, "DEL");
    public static final int MouseMove = (Integer) ReflectionUtils.getField(actualClass, "MouseMove");
    public static final int MouseDown = (Integer) ReflectionUtils.getField(actualClass, "MouseDown");
    public static final int MouseUp = (Integer) ReflectionUtils.getField(actualClass, "MouseUp");
    public static final int KeyDown = (Integer) ReflectionUtils.getField(actualClass, "KeyDown");
    public static final int KeyUp = (Integer) ReflectionUtils.getField(actualClass, "KeyUp");
    public static final int MouseWheel = (Integer) ReflectionUtils.getField(actualClass, "MouseWheel");
    public static final int SCROLL_PAGE = (Integer) ReflectionUtils.getField(actualClass, "SCROLL_PAGE");
    public static final int SCROLL_LINE = (Integer) ReflectionUtils.getField(actualClass, "SCROLL_LINE");
    public static final int COMMAND = (Integer) ReflectionUtils.getField(actualClass, "COMMAND");
    public static final int ARROW_UP = (Integer) ReflectionUtils.getField(actualClass, "ARROW_UP");
    public static final int ARROW_DOWN = (Integer) ReflectionUtils.getField(actualClass, "ARROW_DOWN");
    public static final int ARROW_LEFT = (Integer) ReflectionUtils.getField(actualClass, "ARROW_LEFT");
    public static final int ARROW_RIGHT = (Integer) ReflectionUtils.getField(actualClass, "ARROW_RIGHT");
    public static final int PAGE_UP = (Integer) ReflectionUtils.getField(actualClass, "PAGE_UP");
    public static final int PAGE_DOWN = (Integer) ReflectionUtils.getField(actualClass, "PAGE_DOWN");
    public static final int HOME = (Integer) ReflectionUtils.getField(actualClass, "HOME");
    public static final int END = (Integer) ReflectionUtils.getField(actualClass, "END");
    public static final int INSERT = (Integer) ReflectionUtils.getField(actualClass, "INSERT");
    public static final int MIN = (Integer) ReflectionUtils.getField(actualClass, "MIN");
    public static final int MAX = (Integer) ReflectionUtils.getField(actualClass, "MAX");
    public static final int POP_UP = (Integer) ReflectionUtils.getField(actualClass, "POP_UP");
    public static final int SYSTEM_MODAL = (Integer) ReflectionUtils.getField(actualClass, "SYSTEM_MODAL");
    public static final int APPLICATION_MODAL = (Integer) ReflectionUtils.getField(actualClass, "APPLICATION_MODAL");
    public static final int PRIMARY_MODAL = (Integer) ReflectionUtils.getField(actualClass, "PRIMARY_MODAL");
    public static final int RESIZE = (Integer) ReflectionUtils.getField(actualClass, "RESIZE");

    public static String getPlatform() {
        return (String) ReflectionUtils.invokeMethod(actualClass, "getPlatform", null, null);
    }

    public static int getVersion() {
        return (Integer) ReflectionUtils.invokeMethod(actualClass, "getVersion", null, null);
    }

    public static String getMessage(String key) {
        return (String) ReflectionUtils.invokeMethod(actualClass, "getMessage", new Class[]{String.class}, new Object[]{key});
    }

    private SWTProxy() {
        // Private constructor.
    }
}