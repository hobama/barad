package edu.utexas.barad.agent.testcase;

import edu.utexas.barad.agent.swt.Displays;
import edu.utexas.barad.agent.swt.WidgetHierarchy;
import edu.utexas.barad.agent.swt.proxy.widgets.*;
import edu.utexas.barad.common.swt.WidgetInfo;

public class DefaultWidgetFilterStrategy implements WidgetFilterStrategy {
    public boolean include(final WidgetInfo widgetInfo, final WidgetHierarchy widgetHierarchy) {
        Object proxy = widgetHierarchy.getWidgetProxy(widgetInfo.getWidgetID());
        if (proxy instanceof MenuItemProxy) {
            final MenuItemProxy menuItemProxy = (MenuItemProxy) proxy;
            return Displays.syncExec(menuItemProxy.getDisplay(), new Displays.BooleanResult() {
                public boolean result() {
                    MenuProxy menuProxy = menuItemProxy.getParent();
                    return menuProxy.isVisible() && menuItemProxy.isEnabled();
                }
            });
        } else if (proxy instanceof ButtonProxy) {
            final ButtonProxy buttonProxy = (ButtonProxy) proxy;
            return Displays.syncExec(buttonProxy.getDisplay(), new Displays.BooleanResult() {
                public boolean result() {
                    return buttonProxy.isVisible() && buttonProxy.isEnabled() && !buttonProxy.getSelection();
                }
            });
        } else if (proxy instanceof TextProxy) {
            final TextProxy textProxy = (TextProxy) proxy;
            return Displays.syncExec(textProxy.getDisplay(), new Displays.BooleanResult() {
                public boolean result() {
                    return textProxy.isVisible() && textProxy.isEnabled();
                }
            });
        } else if (proxy instanceof ComboProxy) {
            final ComboProxy comboProxy = (ComboProxy) proxy;
            return Displays.syncExec(comboProxy.getDisplay(), new Displays.BooleanResult() {
                public boolean result() {
                    return comboProxy.isVisible() && comboProxy.isEnabled();
                }
            });
        }

        return false;
    }
}