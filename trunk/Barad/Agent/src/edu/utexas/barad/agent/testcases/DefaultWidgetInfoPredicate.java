package edu.utexas.barad.agent.testcases;

import edu.utexas.barad.agent.swt.Displays;
import edu.utexas.barad.agent.swt.WidgetHierarchy;
import edu.utexas.barad.agent.swt.proxy.widgets.ButtonProxy;
import edu.utexas.barad.agent.swt.proxy.widgets.MenuItemProxy;
import edu.utexas.barad.agent.swt.proxy.widgets.MenuProxy;
import edu.utexas.barad.agent.swt.proxy.widgets.TextProxy;
import edu.utexas.barad.common.swt.WidgetInfo;

public class DefaultWidgetInfoPredicate implements WidgetInfoPredicate {
    public boolean evaluate(final WidgetInfo widgetInfo, final WidgetHierarchy widgetHierarchy) {
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
        }

        return false;
    }
}