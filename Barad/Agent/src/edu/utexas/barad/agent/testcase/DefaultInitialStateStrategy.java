package edu.utexas.barad.agent.testcase;

import edu.utexas.barad.agent.exceptions.GenerateTestCasesException;
import edu.utexas.barad.agent.swt.Displays;
import edu.utexas.barad.agent.swt.WidgetHierarchy;
import edu.utexas.barad.agent.swt.proxy.widgets.ButtonProxy;
import edu.utexas.barad.agent.swt.proxy.widgets.MenuItemProxy;
import edu.utexas.barad.agent.swt.proxy.widgets.MenuProxy;
import edu.utexas.barad.agent.swt.proxy.widgets.ShellProxy;
import edu.utexas.barad.agent.swt.tester.WidgetTester;
import edu.utexas.barad.agent.swt.tester.WidgetTesterFactory;
import edu.utexas.barad.agent.swt.widgets.MessageBoxHelper;
import edu.utexas.barad.common.swt.WidgetInfo;

import java.util.Iterator;
import java.util.Set;

/**
 * University of Texas at Austin
 * Barad Project, Aug 25, 2007
 */
public class DefaultInitialStateStrategy implements InitialStateStrategy {
    public void resetToInitialState(final WidgetHierarchy widgetHierarchy) {
        // Close any open MessageBoxes.
        MessageBoxHelper messageBoxHelper = widgetHierarchy.getMessageBoxHelper();
        if (messageBoxHelper != null) {
            if (messageBoxHelper.hasCancelButton()) {
                messageBoxHelper.clickButton(MessageBoxHelper.Button.CANCEL);
            } else if (messageBoxHelper.hasAbortButton()) {
                messageBoxHelper.clickButton(MessageBoxHelper.Button.ABORT);
            } else if (messageBoxHelper.hasNoButton()) {
                messageBoxHelper.clickButton(MessageBoxHelper.Button.NO);
            } else if (messageBoxHelper.hasOKButton()) {
                messageBoxHelper.clickButton(MessageBoxHelper.Button.OK);
            }

            widgetHierarchy.getWidgetHierarchy(true);
            messageBoxHelper = widgetHierarchy.getMessageBoxHelper();
            if (messageBoxHelper != null) {
                throw new GenerateTestCasesException("Couldn't close MessageBox, messageBoxHelper=" + messageBoxHelper);
            }
        }

        // Close any open dialog windows.
        Set<WidgetInfo> filteredSet;
        do {
            WidgetFilterVisitor visitor = new WidgetFilterVisitor(new WidgetFilterStrategy() {
                public boolean include(WidgetInfo widgetInfo, WidgetHierarchy widgetHierarchy) {
                    Object proxy = widgetHierarchy.getWidgetProxy(widgetInfo.getWidgetID());
                    if (proxy instanceof ShellProxy) {
                        final ShellProxy shell = (ShellProxy) proxy;
                        return Displays.syncExec(shell.getDisplay(), new Displays.BooleanResult() {
                            public boolean result() {
                                return shell.isVisible() && shell.getParent() != null;
                            }
                        });
                    }
                    return false;
                }
            }, widgetHierarchy);
            widgetHierarchy.accept(visitor);
            filteredSet = visitor.getFilteredSet();
            Iterator<WidgetInfo> iterator = filteredSet.iterator();
            if (iterator.hasNext()) {
                WidgetInfo widgetInfo = iterator.next();
                final ShellProxy shell = (ShellProxy) widgetHierarchy.getWidgetProxy(widgetInfo.getWidgetID());
                final Object[] result = new Object[1];
                shell.getDisplay().syncExec(new Runnable() {
                    public void run() {
                        shell.close();
                        result[0] = shell.__fieldGetactiveMenu();
                    }
                });
                MenuProxy activeMenu = (MenuProxy) result[0];
                if (activeMenu != null) {
                    MenuItemProxy parentMenuItem = activeMenu.getParentItem();
                    WidgetTester widgetTester = WidgetTesterFactory.getDefault().getTester(parentMenuItem);
                    widgetTester.actionClick(parentMenuItem);
                }
            }
            widgetHierarchy.getWidgetHierarchy(true);
        } while (!filteredSet.isEmpty());

        // Hide any showing menus.
        WidgetFilterVisitor visitor = new WidgetFilterVisitor(new WidgetFilterStrategy() {
            public boolean include(WidgetInfo widgetInfo, WidgetHierarchy widgetHierarchy) {
                Object proxy = widgetHierarchy.getWidgetProxy(widgetInfo.getWidgetID());
                if (proxy instanceof ShellProxy) {
                    final ShellProxy shell = (ShellProxy) proxy;
                    return Displays.syncExec(shell.getDisplay(), new Displays.BooleanResult() {
                        public boolean result() {
                            return shell.isVisible() && shell.getParent() == null;
                        }
                    });
                }
                return false;
            }
        }, widgetHierarchy);
        widgetHierarchy.accept(visitor);
        filteredSet = visitor.getFilteredSet();
        Iterator<WidgetInfo> iterator = filteredSet.iterator();
        if (iterator.hasNext()) {
            WidgetInfo widgetInfo = iterator.next();
            final ShellProxy shell = (ShellProxy) widgetHierarchy.getWidgetProxy(widgetInfo.getWidgetID());
            final Object[] result = new Object[1];
            shell.getDisplay().syncExec(new Runnable() {
                public void run() {
                    result[0] = shell.__fieldGetactiveMenu();
                }
            });

            final MenuProxy activeMenu = (MenuProxy) result[0];
            if (activeMenu != null) {
                activeMenu.getDisplay().syncExec(new Runnable() {
                    public void run() {
                        MenuItemProxy parentMenuItem = activeMenu.getParentItem();
                        result[0] = parentMenuItem;
                    }
                });

                MenuItemProxy parentMenuItem = (MenuItemProxy) result[0];
                WidgetTester widgetTester = WidgetTesterFactory.getDefault().getTester(parentMenuItem);
                widgetTester.actionClick(parentMenuItem);
            }
        }

        // Workout Generator -- click Clear button.
        visitor = new WidgetFilterVisitor(new WidgetFilterStrategy() {
            public boolean include(WidgetInfo widgetInfo, WidgetHierarchy widgetHierarchy) {
                Object proxy = widgetHierarchy.getWidgetProxy(widgetInfo.getWidgetID());
                if (proxy instanceof ButtonProxy) {
                    final ButtonProxy buttonProxy = (ButtonProxy) proxy;
                    return Displays.syncExec(buttonProxy.getDisplay(), new Displays.BooleanResult() {
                        public boolean result() {
                            return buttonProxy.isVisible() && "Clear".equals(buttonProxy.getText());
                        }
                    });
                }
                return false;
            }
        }, widgetHierarchy);
        widgetHierarchy.accept(visitor);
        filteredSet = visitor.getFilteredSet();
        iterator = filteredSet.iterator();
        if (iterator.hasNext()) {
            WidgetInfo widgetInfo = iterator.next();
            ButtonProxy buttonProxy = (ButtonProxy) widgetHierarchy.getWidgetProxy(widgetInfo.getWidgetID());
            buttonProxy.click();
        }
    }
}