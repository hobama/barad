package edu.utexas.barad.agent.testcases;

import edu.utexas.barad.agent.exceptions.GenerateTestCasesException;
import edu.utexas.barad.agent.swt.Displays;
import edu.utexas.barad.agent.swt.Displays.BooleanResult;
import edu.utexas.barad.agent.swt.WidgetHierarchy;
import edu.utexas.barad.agent.swt.proxy.SWTProxy;
import edu.utexas.barad.agent.swt.proxy.widgets.*;
import edu.utexas.barad.agent.swt.tester.WidgetTester;
import edu.utexas.barad.agent.swt.tester.WidgetTesterFactory;
import edu.utexas.barad.agent.swt.widgets.MessageBoxHelper;
import edu.utexas.barad.common.Visitor;
import edu.utexas.barad.common.swt.WidgetInfo;
import edu.utexas.barad.common.testcase.MessageBoxTestStep;
import edu.utexas.barad.common.testcase.TestCase;
import edu.utexas.barad.common.testcase.TestCaseAction;
import edu.utexas.barad.common.testcase.TestStep;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * University of Texas at Austin
 * Barad Project, Jul 30, 2007
 */
public class GenerateTestCases {
    private static final Logger logger = Logger.getLogger(GenerateTestCases.class);

    private Set<WidgetHierarchy> states = new LinkedHashSet<WidgetHierarchy>();

    public TestCase[] generate() {
        states.clear();

        returnToInitialState();

        List<TestCase> testCases = new ArrayList<TestCase>();
        int size;
        do {
            size = testCases.size();

            List<TestCase> newTestCases = new ArrayList<TestCase>();
            boolean firstStep = testCases.size() == 0;
            if (firstStep) {
                TestStep[] testSteps = generateNextStep();
                for (TestStep testStep : testSteps) {
                    TestCase newTestCase = new TestCase();
                    newTestCase.add(testStep);
                    newTestCases.add(newTestCase);
                }
            } else {
                for (TestCase testCase : testCases) {
                    returnToInitialState();
                    executeTestCase(testCase);

                    TestStep[] testSteps = generateNextStep();
                    for (TestStep testStep : testSteps) {
                        try {
                            TestCase newTestCase = (TestCase) testCase.clone();
                            newTestCase.add(testStep);
                            newTestCases.add(newTestCase);
                        } catch (CloneNotSupportedException ignore) {
                            // Ignore.
                        }
                    }
                }
            }

            for (TestCase testCase : newTestCases) {
                if (!pruneTestCase(testCase)) {
                    logger.debug("Adding testcase, testCase=" + testCase);
                    testCases.add(testCase);
                } else {
                    logger.debug("Test case will be pruned, testCase=" + testCase);
                }
            }

            logger.debug("Current testcase count=" + testCases.size() + ", previous testcase count=" + size);
        } while (testCases.size() > size);

        returnToInitialState();

        return testCases.toArray(new TestCase[0]);
    }

    private TestStep[] generateNextStep() {
        List<TestStep> testSteps = new ArrayList<TestStep>();

        final WidgetHierarchy widgetHierarchy = new WidgetHierarchy();
        widgetHierarchy.getWidgetHierarchy(true);

        MessageBoxHelper messageBoxHelper = widgetHierarchy.getMessageBoxHelper();
        if (messageBoxHelper == null) {
            WidgetInfoPredicate predicate = new WidgetInfoPredicate() {
                public boolean evaluate(final WidgetInfo widgetInfo) {
                    Object proxy = widgetHierarchy.getWidgetProxy(widgetInfo.getWidgetID());
                    if (proxy instanceof MenuItemProxy) {
                        final MenuItemProxy menuItemProxy = (MenuItemProxy) proxy;
                        return Displays.syncExec(menuItemProxy.getDisplay(), new BooleanResult() {
                            public boolean result() {
                                MenuProxy menuProxy = menuItemProxy.getParent();
                                String text = menuItemProxy.getText();
//                                if ("&Help".equals(text) || "&About Address Book...".equals(text)) {
                                if ("&Search".equals(text) || text.startsWith("&Find...") || text.startsWith("Find &Next...")) {
                                    return menuProxy.isVisible() && menuItemProxy.isEnabled();
                                }
                                return false;
                            }
                        });
                    } else if (proxy instanceof ButtonProxy) {
                        final ButtonProxy buttonProxy = (ButtonProxy) proxy;
                        return Displays.syncExec(buttonProxy.getDisplay(), new BooleanResult() {
                            public boolean result() {
                                return buttonProxy.isVisible() && buttonProxy.isEnabled() && !buttonProxy.getSelection();
                            }
                        });
                    } else if (proxy instanceof TextProxy) {
                        final TextProxy textProxy = (TextProxy) proxy;
                        return Displays.syncExec(textProxy.getDisplay(), new BooleanResult() {
                            public boolean result() {
                                return textProxy.isVisible() && textProxy.isEnabled();
                            }
                        });
                    }

                    return false;
                }
            };

            PredicateVisitor visitor = new PredicateVisitor(predicate);
            widgetHierarchy.accept(visitor);
            List<WidgetInfo> satisfiesList = visitor.getSatisfiesList();

            for (WidgetInfo widgetInfo : satisfiesList) {
                Object proxy = widgetHierarchy.getWidgetProxy(widgetInfo.getWidgetID());
                if (proxy instanceof MenuItemProxy || proxy instanceof ButtonProxy) {
                    TestStep testStep = new TestStep(TestCaseAction.LEFT_MOUSE_CLICK, widgetInfo);
                    testSteps.add(testStep);
                } else if (proxy instanceof TextProxy) {
                    TestStep testStep = new TestStep(TestCaseAction.ENTER_TEXT, widgetInfo);
                    testSteps.add(testStep);
                }
            }
        } else {
            if (messageBoxHelper.hasOKButton()) {
                testSteps.add(new MessageBoxTestStep(TestCaseAction.LEFT_MOUSE_CLICK, MessageBoxHelper.Button.OK));
            }
            if (messageBoxHelper.hasCancelButton()) {
                testSteps.add(new MessageBoxTestStep(TestCaseAction.LEFT_MOUSE_CLICK, MessageBoxHelper.Button.CANCEL));
            }
            if (messageBoxHelper.hasYesButton()) {
                testSteps.add(new MessageBoxTestStep(TestCaseAction.LEFT_MOUSE_CLICK, MessageBoxHelper.Button.YES));
            }
            if (messageBoxHelper.hasNoButton()) {
                testSteps.add(new MessageBoxTestStep(TestCaseAction.LEFT_MOUSE_CLICK, MessageBoxHelper.Button.NO));
            }
            if (messageBoxHelper.hasAbortButton()) {
                testSteps.add(new MessageBoxTestStep(TestCaseAction.LEFT_MOUSE_CLICK, MessageBoxHelper.Button.ABORT));
            }
            if (messageBoxHelper.hasRetryButton()) {
                testSteps.add(new MessageBoxTestStep(TestCaseAction.LEFT_MOUSE_CLICK, MessageBoxHelper.Button.RETRY));
            }
            if (messageBoxHelper.hasIgnoreButton()) {
                testSteps.add(new MessageBoxTestStep(TestCaseAction.LEFT_MOUSE_CLICK, MessageBoxHelper.Button.IGNORE));
            }
        }

        return testSteps.toArray(new TestStep[0]);
    }

    private void executeTestCase(TestCase testCase) {
//        logger.debug("Executing test case, testCase=" + testCase);

        for (TestStep testStep : testCase.getSteps()) {
            executeTestStep(testStep);
        }
    }

    private void executeTestStep(TestStep testStep) {
//        logger.debug("Executing test step, testStep=" + testStep);

        WidgetHierarchy widgetHierarchy = new WidgetHierarchy();
        widgetHierarchy.getWidgetHierarchy(true);

        if (!(testStep instanceof MessageBoxTestStep)) {
            WidgetInfo widgetInfo = testStep.getWidgetInfo();
            Object proxy = widgetHierarchy.getWidgetProxy(widgetInfo.getWidgetID());
            if (proxy == null) {
                throw new GenerateTestCasesException("Widget doesn't exist, widgetInfo=" + widgetInfo);
            }
            WidgetProxy widgetProxy = (WidgetProxy) proxy;
            WidgetTester widgetTester = WidgetTesterFactory.getDefault().getTester(widgetProxy);
            switch (testStep.getAction()) {
                case LEFT_MOUSE_CLICK: {
                    widgetTester.actionClick(widgetProxy, SWTProxy.BUTTON1);
                    break;
                }
                case RIGHT_MOUSE_CLICK: {
                    widgetTester.actionClick(widgetProxy, SWTProxy.BUTTON3);
                    break;
                }
                case ENTER_TEXT: {
                    final TextProxy textProxy = (TextProxy) widgetProxy;
                    textProxy.getDisplay().syncExec(new Runnable() {
                        public void run() {
                            textProxy.setText(""); // Clear the textfield to prevent generation of new strings.    
                        }
                    });
                    widgetTester.actionKeyString("This is a test");
                    break;
                }
            }
        } else {
            MessageBoxHelper messageBoxHelper = widgetHierarchy.getMessageBoxHelper();
            if (messageBoxHelper == null) {
                throw new GenerateTestCasesException("MessageBox doesn't exist.");
            }
            MessageBoxTestStep messageBoxTestStep = (MessageBoxTestStep) testStep;
            messageBoxHelper.clickButton(messageBoxTestStep.getButton());
        }
    }

    private void returnToInitialState() {
        final WidgetHierarchy widgetHierarchy = new WidgetHierarchy();
        widgetHierarchy.getWidgetHierarchy(true);

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
        List<WidgetInfo> satisfiesList;
        do {
            PredicateVisitor visitor = new PredicateVisitor(new WidgetInfoPredicate() {
                public boolean evaluate(WidgetInfo widgetInfo) {
                    Object proxy = widgetHierarchy.getWidgetProxy(widgetInfo.getWidgetID());
                    if (proxy instanceof ShellProxy) {
                        final ShellProxy shell = (ShellProxy) proxy;
                        return Displays.syncExec(shell.getDisplay(), new BooleanResult() {
                            public boolean result() {
                                return shell.isVisible() && shell.getParent() != null;
                            }
                        });
                    }
                    return false;
                }
            });
            widgetHierarchy.accept(visitor);
            satisfiesList = visitor.getSatisfiesList();
            if (satisfiesList.size() > 0) {
                WidgetInfo widgetInfo = satisfiesList.get(0);
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
        } while (!satisfiesList.isEmpty());

        // Hide any showing menus.
        PredicateVisitor visitor = new PredicateVisitor(new WidgetInfoPredicate() {
            public boolean evaluate(WidgetInfo widgetInfo) {
                Object proxy = widgetHierarchy.getWidgetProxy(widgetInfo.getWidgetID());
                if (proxy instanceof ShellProxy) {
                    final ShellProxy shell = (ShellProxy) proxy;
                    return Displays.syncExec(shell.getDisplay(), new BooleanResult() {
                        public boolean result() {
                            return shell.isVisible() && shell.getParent() == null;
                        }
                    });
                }
                return false;
            }
        });
        widgetHierarchy.accept(visitor);
        satisfiesList = visitor.getSatisfiesList();
        if (satisfiesList.size() > 0) {
            WidgetInfo widgetInfo = satisfiesList.get(0);
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
    }

    private boolean pruneTestCase(TestCase testCase) {
        returnToInitialState();
        executeTestCase(testCase);

        WidgetHierarchy widgetHierarchy = new WidgetHierarchy();
        widgetHierarchy.getWidgetHierarchy(true);
        widgetHierarchy.populateValues();

        if (states.contains(widgetHierarchy)) {
            return true;
        } else {
            for (WidgetHierarchy existingState : states) {
                logger.debug(CompareHierarchies.compare(existingState, widgetHierarchy));
            }
        }
        states.add(widgetHierarchy);
        return false;
    }

    private static class PredicateVisitor implements Visitor {
        private WidgetInfoPredicate predicate;
        private List<WidgetInfo> satisfiesList = new ArrayList<WidgetInfo>();

        public PredicateVisitor(WidgetInfoPredicate predicate) {
            this.predicate = predicate;
        }

        public void visit(Object object) {
            WidgetInfo widgetInfo = (WidgetInfo) object;
            if (predicate.evaluate(widgetInfo)) {
                satisfiesList.add(widgetInfo);
            }
        }

        public List<WidgetInfo> getSatisfiesList() {
            return satisfiesList;
        }
    }

    private static interface WidgetInfoPredicate {
        public boolean evaluate(WidgetInfo widgetInfo);
    }
}