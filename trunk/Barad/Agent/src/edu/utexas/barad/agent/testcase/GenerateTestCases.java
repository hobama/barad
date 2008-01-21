package edu.utexas.barad.agent.testcase;

import edu.utexas.barad.agent.exceptions.GenerateTestCasesException;
import edu.utexas.barad.agent.swt.Displays;
import edu.utexas.barad.agent.swt.WidgetHierarchy;
import edu.utexas.barad.agent.swt.proxy.SWTProxy;
import edu.utexas.barad.agent.swt.proxy.widgets.*;
import edu.utexas.barad.agent.swt.tester.ComboTester;
import edu.utexas.barad.agent.swt.tester.WidgetTester;
import edu.utexas.barad.agent.swt.tester.WidgetTesterFactory;
import edu.utexas.barad.agent.swt.widgets.MessageBoxHelper;
import edu.utexas.barad.common.swt.WidgetInfo;
import edu.utexas.barad.common.testcase.*;
import org.apache.log4j.Logger;

import java.util.*;

/**
 * University of Texas at Austin
 * Barad Project, Jul 30, 2007
 */
public class GenerateTestCases {
    private static final Logger logger = Logger.getLogger(GenerateTestCases.class);

    private WidgetFilterStrategy widgetFilterStrategy;
    private InitialStateStrategy initialStateStrategy;
    private Set<WidgetHierarchy> widgetStates = new LinkedHashSet<WidgetHierarchy>();
    private Set<TestCase> uniqueTestCases = Collections.synchronizedSet(new LinkedHashSet<TestCase>());
    private TestCase currentTestCase;
    private Throwable throwable;
    private ListenerProxy keyListener;
    private WorkerThread workerThread;
    private ExecutionState executionState = ExecutionState.STOPPED;
    private Set<TestCase> candidateTestCases;
    private Set<TestCase> processedTestCases = Collections.synchronizedSet(new LinkedHashSet<TestCase>());
    private final Object lock = new Object();

    public void start(WidgetFilterStrategy widgetFilterStrategy, InitialStateStrategy initialStateStrategy) {
        initializeKeyListener();

        if (widgetFilterStrategy == null) {
            widgetFilterStrategy = new DefaultWidgetFilterStrategy();
        }
        if (initialStateStrategy == null) {
            initialStateStrategy = new DefaultInitialStateStrategy();
        }
        this.widgetFilterStrategy = widgetFilterStrategy;
        this.initialStateStrategy = initialStateStrategy;

        setExecutionState(ExecutionState.STARTED);
    }

    public void pause() {
        setExecutionState(ExecutionState.PAUSED);
    }

    public void continue_() {
        setExecutionState(ExecutionState.STARTED);
    }

    public void stop() {
        setExecutionState(ExecutionState.STOPPED);
    }

    public ExecutionState getExecutionState() {
        synchronized (lock) {
            return executionState;
        }
    }

    private void initializeKeyListener() {
        if (keyListener == null) {
            keyListener = ListenerProxy.Factory.newListenerProxy(new ListenerProxy.Impl() {
                public void handleEvent(EventProxy event) {
                    int stateMask = event.__fieldGetstateMask();
                    char keyCode = (char) event.__fieldGetkeyCode();
                    if (keyCode == 'p' && (stateMask & SWTProxy.CTRL) == SWTProxy.CTRL && (stateMask & SWTProxy.ALT) == SWTProxy.ALT) {
                        logger.info("CTRL-ALT-P entered, pausing test case generation.");
                        pause();
                    } else
                    if (keyCode == 'q' && (stateMask & SWTProxy.CTRL) == SWTProxy.CTRL && (stateMask & SWTProxy.ALT) == SWTProxy.ALT) {
                        logger.info("CTRL-ALT-Q entered, stopping test case generation.");
                        stop();
                    } else
                    if (keyCode == 'c' && (stateMask & SWTProxy.CTRL) == SWTProxy.CTRL && (stateMask & SWTProxy.ALT) == SWTProxy.ALT) {
                        logger.info("CTRL-ALT-C entered, continuing test case generation.");
                        continue_();
                    }
                }
            });
        }
    }

    private void setExecutionState(ExecutionState executionState) {
        if (executionState == null) {
            throw new NullPointerException("executionState");
        }

        synchronized (lock) {
            switch (executionState) {
                case STARTED: {
                    switch (this.executionState) {
                        case STARTED: {
                            break;
                        }

                        case PAUSED: {
                            this.executionState = ExecutionState.STARTED;
                            lock.notifyAll();
                            break;
                        }

                        case STOPPED: {
                            this.executionState = ExecutionState.STARTED;
                            workerThread = new WorkerThread(this);
                            workerThread.start();
                            break;
                        }

                        default: {
                            break;
                        }
                    }
                    break;
                }

                case STOPPED: {
                    switch (this.executionState) {
                        case STARTED: {
                            this.executionState = ExecutionState.STOPPED;
                            while (workerThread.isAlive()) {
                                try {
                                    lock.wait(250);
                                } catch (InterruptedException ignore) {
                                    // Ignore.
                                }
                            }
                        }

                        case PAUSED: {
                            this.executionState = ExecutionState.STOPPED;
                            lock.notifyAll();
                            while (workerThread.isAlive()) {
                                try {
                                    lock.wait(250);
                                } catch (InterruptedException ignore) {
                                    // Ignore.
                                }
                            }
                            break;
                        }

                        case STOPPED: {
                            break;
                        }

                        default: {
                            break;
                        }
                    }
                    break;
                }

                case PAUSED: {
                    switch (this.executionState) {
                        case STARTED: {
                            this.executionState = ExecutionState.PAUSED;
                            while (workerThread.getState() != Thread.State.TIMED_WAITING && workerThread.getState() != Thread.State.WAITING) {
                                try {
                                    lock.wait(250);
                                } catch (InterruptedException ignore) {
                                    // Ignore.
                                }
                            }
                            break;
                        }

                        case PAUSED: {
                            break;
                        }

                        case STOPPED: {
                            break;
                        }

                        default: {
                            break;
                        }
                    }
                    break;
                }

                default: {
                    break;
                }
            }
        }
    }

    public List<TestCase> getUniqueTestCases() {
        return new ArrayList<TestCase>(uniqueTestCases);
    }

    public TestCase getCurrentTestCase() {
        return currentTestCase;
    }

    public Throwable getThrowable() {
        return throwable;
    }

    public List<TestCase> getCandidateTestCases() {
        return candidateTestCases != null ? new ArrayList<TestCase>(candidateTestCases) : null;
    }

    private void generate() {
        synchronized (lock) {
            widgetStates.clear();
            uniqueTestCases.clear();
            processedTestCases.clear();
            currentTestCase = null;
            throwable = null;
        }

        try {
            final DisplayProxy defaultDisplay = DisplayProxy.Factory.getDefault();
            defaultDisplay.asyncExec(new Runnable() {
                public void run() {
                    defaultDisplay.addFilter(SWTProxy.KeyDown, keyListener);
                }
            });

            returnToInitialState();

            // Add initial SUT state.
            WidgetHierarchy widgetHierarchy = new WidgetHierarchy();
            widgetHierarchy.getWidgetHierarchy(true);
            widgetHierarchy.populateValues();
            widgetStates.add(widgetHierarchy);

            boolean newUniqueTestCasesFound;
            do {
                newUniqueTestCasesFound = false;

                candidateTestCases = new LinkedHashSet<TestCase>();
                boolean firstStep = uniqueTestCases.size() == 0;
                if (firstStep) {
                    List<TestStep> nextSteps = generateNextSteps();
                    for (TestStep testStep : nextSteps) {
                        TestCase newTestCase = new TestCase();
                        newTestCase.add(testStep);
                        candidateTestCases.add(newTestCase);
                    }
                } else {
                    for (TestCase testCase : uniqueTestCases) {
                        if (processedTestCases.contains(testCase)) {
                            continue;
                        }

                        returnToInitialState();

                        if (isStopped()) {
                            break;
                        }

                        currentTestCase = testCase;
                        executeTestCase(testCase);

                        List<TestStep> nextSteps = generateNextSteps();
                        for (TestStep nextStep : nextSteps) {
                            try {
                                TestCase newTestCase = (TestCase) testCase.clone();
                                newTestCase.add(nextStep);
                                newTestCase.setParent(testCase);
                                candidateTestCases.add(newTestCase);
                            } catch (CloneNotSupportedException ignore) {
                                // Ignore.
                            }
                        }

                        processedTestCases.add(testCase);
                    }
                }

                for (TestCase newTestCase : candidateTestCases) {
                    if (isStopped()) {
                        break;
                    }

                    if (!pruneTestCase(newTestCase)) {
                        if (uniqueTestCases.add(newTestCase)) {
                            uniqueTestCases.remove(newTestCase.getParent());
                            logger.debug("Adding test case, newTestCase=" + newTestCase);
                            newUniqueTestCasesFound = true;
                        }
                    } else {
                        logger.debug("Test case will be pruned, newTestCase=" + newTestCase);
                    }
                }

                logger.debug("Current test case count=" + uniqueTestCases.size() + ", new unique test cases found=" + newUniqueTestCasesFound);
            } while (newUniqueTestCasesFound && !isStopped());

            returnToInitialState();
        } catch (Throwable throwable) {
            logger.error("An exception occurred during test case generation.", throwable);
            this.throwable = throwable;
        }

        final DisplayProxy defaultDisplay = DisplayProxy.Factory.getDefault();
        defaultDisplay.asyncExec(new Runnable() {
            public void run() {
                defaultDisplay.removeFilter(SWTProxy.KeyDown, keyListener);
            }
        });

        synchronized (lock) {
            executionState = ExecutionState.STOPPED;
            lock.notifyAll();
        }
    }

    private boolean isStopped() {
        synchronized (lock) {
            while (executionState != ExecutionState.STARTED) {
                switch (executionState) {
                    case PAUSED: {
                        try {
                            lock.wait(1000);
                        } catch (InterruptedException ignore) {
                            // Ignore.
                        }
                        break;
                    }

                    case STOPPED: {
                        return true;
                    }

                    default: {
                        break;
                    }
                }
            }
            return false;
        }
    }

    private List<TestStep> generateNextSteps() {
        List<TestStep> testSteps = new ArrayList<TestStep>();

        final WidgetHierarchy widgetHierarchy = new WidgetHierarchy();
        widgetHierarchy.getWidgetHierarchy(true);

        MessageBoxHelper messageBoxHelper = widgetHierarchy.getMessageBoxHelper();
        if (messageBoxHelper == null) {
            WidgetFilterVisitor visitor = new WidgetFilterVisitor(widgetFilterStrategy, widgetHierarchy);
            widgetHierarchy.accept(visitor);
            Set<WidgetInfo> filteredSet = visitor.getFilteredSet();

            for (WidgetInfo widgetInfo : filteredSet) {
                Object proxy = widgetHierarchy.getWidgetProxy(widgetInfo.getWidgetID());
                if (proxy instanceof MenuItemProxy || proxy instanceof ButtonProxy) {
                    TestStep testStep = new TestStep(TestCaseAction.LEFT_MOUSE_CLICK, widgetInfo);
                    testSteps.add(testStep);
                } else if (proxy instanceof TextProxy) {
                    TestStep testStep = new EnterTextTestStep(TestCaseAction.ENTER_TEXT, widgetInfo, "Test");
                    testSteps.add(testStep);
                } else if (proxy instanceof ComboProxy) {
                    final ComboProxy comboProxy = (ComboProxy) proxy;
                    int itemCount = Displays.syncExec(comboProxy.getDisplay(), new Displays.IntResult() {
                        public int result() {
                            return comboProxy.getItemCount();
                        }
                    });
                    for (int i = 0; i < itemCount; ++i) {
                        TestStep testStep = new ComboTestStep(TestCaseAction.SELECT_ITEM, widgetInfo, i);
                        testSteps.add(testStep);
                    }
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

        return testSteps;
    }

    private void executeTestCase(TestCase testCase) {
//        logger.debug("Executing test case, testCase=" + testCase);

        for (TestStep testStep : testCase.getSteps()) {
            if (isStopped()) {
                return;
            }
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
            widgetTester.mouseMove(widgetProxy);
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
                    if (testStep instanceof EnterTextTestStep && widgetProxy instanceof TextProxy) {
                        final TextProxy textProxy = (TextProxy) widgetProxy;
                        widgetTester.actionClick(textProxy);
                        textProxy.getDisplay().syncExec(new Runnable() {
                            public void run() {
                                textProxy.setText(""); // Clear the textfield to prevent generation of new strings.
                            }
                        });

                        EnterTextTestStep enterTextTestStep = (EnterTextTestStep) testStep;
                        widgetTester.actionKeyString(enterTextTestStep.getTextToEnter());
                    }
                    break;
                }
                case SELECT_ITEM: {
                    if (testStep instanceof ComboTestStep && widgetProxy instanceof ComboProxy && widgetTester instanceof ComboTester) {
                        ComboTestStep comboTestStep = (ComboTestStep) testStep;
                        int itemIndex = comboTestStep.getItemIndex();

                        ComboProxy comboProxy = (ComboProxy) widgetProxy;

                        ComboTester comboTester = (ComboTester) widgetTester;
                        comboTester.actionSelectIndex(comboProxy, itemIndex);
                    }
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
        initialStateStrategy.resetToInitialState(widgetHierarchy);
    }

    private boolean pruneTestCase(TestCase testCase) {
        returnToInitialState();
        executeTestCase(testCase);

        WidgetHierarchy widgetHierarchy = new WidgetHierarchy();
        widgetHierarchy.getWidgetHierarchy(true);
        widgetHierarchy.populateValues();

        if (widgetStates.contains(widgetHierarchy)) {
            return true;
        } else {
            for (WidgetHierarchy existingState : widgetStates) {
                logger.debug(CompareHierarchies.compare(existingState, widgetHierarchy));
            }
        }
        widgetStates.add(widgetHierarchy);
        return false;
    }

    private static class WorkerThread extends Thread {
        private GenerateTestCases generateTestCases;

        public WorkerThread(GenerateTestCases generateTestCases) {
            super("Generate Test Cases Worker");
            this.generateTestCases = generateTestCases;
        }

        @Override
        public void run() {
            generateTestCases.generate();
        }
    }
}