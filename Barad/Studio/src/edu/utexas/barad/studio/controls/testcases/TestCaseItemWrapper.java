package edu.utexas.barad.studio.controls.testcases;

import edu.utexas.barad.agent.IAgent;
import edu.utexas.barad.common.testcase.ExecutionState;
import edu.utexas.barad.common.testcase.TestCase;
import edu.utexas.barad.studio.Images;
import edu.utexas.barad.studio.StudioMain;
import org.apache.log4j.Logger;
import org.eclipse.swt.SWT;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.*;

import java.rmi.RemoteException;
import java.util.Timer;
import java.util.TimerTask;

/**
 * University of Texas at Austin
 * Barad Project, Jul 29, 2007
 */
public class TestCaseItemWrapper {
    private static final Logger logger = Logger.getLogger(TestCaseItemWrapper.class);

    private CTabFolder tabFolder;
    private CTabItem tabItem;
    private Browser currentTestCaseBrowser;
    private Browser allTestCasesBrowser;
    private Browser currentIterationTestCasesBrowser;
    private Browser exceptionBrowser;
    private Combo filterStrategyClassCombo;
    private Combo initialStateStrategyCombo;
    private ExecutionState executionState;
    private Button startButton;
    private Button stopButton;
    private Label currentStatusLabel;
    private Timer refreshTimer = new Timer("Refresh Timer", true);
    private CTabItem currentTestCaseItem;
    private CTabItem allTestCasesItem;
    private CTabItem currentIterationTestCasesItem;
    private CTabItem exceptionItem;

    public TestCaseItemWrapper(CTabFolder tabFolder) {
        this.tabFolder = tabFolder;
        initialize();
    }

    private void initialize() {
        tabItem = new CTabItem(tabFolder, SWT.NONE);
        tabItem.setText("Test Cases");
        tabItem.setImage(Images.TESTCASES.createImage());

        Composite composite = new Composite(tabFolder, SWT.NONE);
        GridLayout gridLayout = new GridLayout(1, false);
        gridLayout.marginBottom = gridLayout.marginTop = gridLayout.marginLeft = gridLayout.marginRight = 5;
        composite.setLayout(gridLayout);

        Group generateTestCasesGroup = new Group(composite, SWT.NONE);
        gridLayout = new GridLayout(2, false);
        gridLayout.marginBottom = gridLayout.marginTop = gridLayout.marginLeft = gridLayout.marginRight = 5;
        gridLayout.verticalSpacing = 11;
        generateTestCasesGroup.setLayout(gridLayout);
        generateTestCasesGroup.setText("Generate Test Cases");
        generateTestCasesGroup.setLayoutData(new GridData(SWT.FILL, SWT.BEGINNING, true, false, 1, 1));

        Composite buttonComposite = new Composite(generateTestCasesGroup, SWT.NONE);
        gridLayout = new GridLayout(4, false);
        gridLayout.marginWidth = gridLayout.marginHeight = 0;
        gridLayout.verticalSpacing = 11;
        buttonComposite.setLayout(gridLayout);
        buttonComposite.setLayoutData(new GridData(SWT.BEGINNING, SWT.BEGINNING, true, true, 4, 1));

        startButton = new Button(buttonComposite, SWT.PUSH);
        startButton.setText("Start");
        startButton.setImage(Images.START.createImage());

        stopButton = new Button(buttonComposite, SWT.PUSH);
        stopButton.setText("Stop");
        stopButton.setImage(Images.STOP.createImage());

        Label separatorLabel = new Label(buttonComposite, SWT.SEPARATOR);
        separatorLabel.setLayoutData(new GridData(2, 25));

        currentStatusLabel = new Label(buttonComposite, SWT.WRAP);
        Font font = currentStatusLabel.getFont();
        FontData fontData = font.getFontData()[0];
        fontData.setStyle(SWT.BOLD);
        currentStatusLabel.setFont(new Font(currentStatusLabel.getDisplay(), fontData));
        currentStatusLabel.setText("Stopped      ");
        currentStatusLabel.setLayoutData(new GridData());

        Label filterStrategyClassLabel = new Label(generateTestCasesGroup, SWT.LEFT);
        filterStrategyClassLabel.setText("Widget filter strategy:");
        filterStrategyClassCombo = new Combo(generateTestCasesGroup, SWT.SINGLE);
        filterStrategyClassCombo.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1));
        filterStrategyClassCombo.setText("edu.utexas.barad.agent.testcase.DefaultWidgetFilterStrategy");

        Label initialStateStrategyLabel = new Label(generateTestCasesGroup, SWT.LEFT);
        initialStateStrategyLabel.setText("Initial state strategy:");
        initialStateStrategyCombo = new Combo(generateTestCasesGroup, SWT.SINGLE);
        initialStateStrategyCombo.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1));
        initialStateStrategyCombo.setText("edu.utexas.barad.agent.testcase.DefaultInitialStateStrategy");

        Group resultsGroup = new Group(composite, SWT.NONE);
        gridLayout = new GridLayout(1, false);
        gridLayout.marginBottom = gridLayout.marginTop = gridLayout.marginLeft = gridLayout.marginRight = 5;
        gridLayout.verticalSpacing = 11;
        resultsGroup.setLayout(gridLayout);
        resultsGroup.setText("Results");
        resultsGroup.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));

        CTabFolder detailsFolder = new CTabFolder(resultsGroup, SWT.BORDER | SWT.BOTTOM);
        detailsFolder.setLayout(new GridLayout(1, false));
        detailsFolder.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
        detailsFolder.setSimple(false);

        currentTestCaseItem = new CTabItem(detailsFolder, SWT.NONE);
        currentTestCaseItem.setText("Current Test Case");
        detailsFolder.setSelection(currentTestCaseItem);

        allTestCasesItem = new CTabItem(detailsFolder, SWT.NONE);
        allTestCasesItem.setText("All Test Cases");

        currentIterationTestCasesItem = new CTabItem(detailsFolder, SWT.NONE);
        currentIterationTestCasesItem.setText("Current Iteration Test Cases");

        exceptionItem = new CTabItem(detailsFolder, SWT.NONE);
        exceptionItem.setText("Exceptions");

        currentTestCaseBrowser = new Browser(detailsFolder, SWT.NONE);
        currentTestCaseItem.setControl(currentTestCaseBrowser);

        allTestCasesBrowser = new Browser(detailsFolder, SWT.NONE);
        allTestCasesItem.setControl(allTestCasesBrowser);

        currentIterationTestCasesBrowser = new Browser(detailsFolder, SWT.NONE);
        currentIterationTestCasesItem.setControl(currentIterationTestCasesBrowser);

        exceptionBrowser = new Browser(detailsFolder, SWT.NONE);
        exceptionItem.setControl(exceptionBrowser);

        startButton.addSelectionListener(new SelectionListener() {
            public void widgetSelected(SelectionEvent event) {                
                synchronized (TestCaseItemWrapper.this) {
                    try {
                        StudioMain application = StudioMain.getApplicationWindow();
                        if (!application.isConnected()) {
                            return;
                        }

                        IAgent agent = application.getAgent();
                        if (executionState != null && executionState == ExecutionState.STARTED) {
                            agent.pauseGenerateTestCases();
                        } else {
                            agent.startGenerateTestCases(filterStrategyClassCombo.getText(), initialStateStrategyCombo.getText());
                        }

                        refresh();
                    } catch (RemoteException e) {
                        logger.error("An unexpected exception occurred starting test case generation.", e);
                    }
                }
            }

            public void widgetDefaultSelected(SelectionEvent event) {
                // Empty.
            }
        });

        stopButton.addSelectionListener(new SelectionListener() {
            public void widgetSelected(SelectionEvent event) {
                synchronized (TestCaseItemWrapper.this) {
                    try {
                        StudioMain application = StudioMain.getApplicationWindow();
                        if (!application.isConnected()) {
                            return;
                        }

                        IAgent agent = application.getAgent();
                        if (executionState != null && (executionState == ExecutionState.STARTED || executionState == ExecutionState.PAUSED)) {
                            agent.stopGenerateTestCases();
                        }

                        refresh();
                    } catch (RemoteException e) {
                        logger.error("An unexpected exception occurred stopping test case generation.", e);
                    }
                }
            }

            public void widgetDefaultSelected(SelectionEvent e) {
                // Empty;
            }
        });

        tabItem.setControl(composite);

        refreshTimer.schedule(new TimerTask() {
            public void run() {
                Display.getDefault().asyncExec(new Runnable() {
                    public void run() {
                        refresh();
                    }
                });
            }
        }, 0, 3000);

        refresh();
    }

    public CTabItem getTabItem() {
        return tabItem;
    }

    public synchronized void refresh() {
        try {                      
            StudioMain application = StudioMain.getApplicationWindow();
            if (!application.isConnected()) {
                return;
            }

            IAgent agent = application.getAgent();

            executionState = agent.getExecutionState();
            switch (executionState) {
                case STARTED: {
                    startButton.setImage(Images.PAUSE.createImage());
                    startButton.setText("Pause");
                    startButton.setEnabled(true);
                    stopButton.setEnabled(true);
                    currentStatusLabel.setText("Started      ");
                    currentStatusLabel.getParent().layout();
                    break;
                }

                case PAUSED: {
                    startButton.setImage(Images.START.createImage());
                    startButton.setText("Continue");
                    startButton.setEnabled(true);
                    stopButton.setEnabled(true);
                    currentStatusLabel.setText("Paused       ");
                    currentStatusLabel.getParent().layout();
                    break;
                }

                case STOPPED: {
                    startButton.setImage(Images.START.createImage());
                    startButton.setText("Start");
                    startButton.setEnabled(true);
                    stopButton.setEnabled(false);
                    currentStatusLabel.setText("Stopped      ");
                    currentStatusLabel.getParent().layout();
                    break;
                }

                default: {
                    break;
                }
            }

            TestCase currentTestCase = agent.getCurrentTestCase();
            if (currentTestCase != null) {
                StringBuffer buffer = new StringBuffer();
                buffer.append("<html>");
                buffer.append(getStyle());
                buffer.append(currentTestCase.toHTML());
                buffer.append("</html>");
                currentTestCaseBrowser.setText(buffer.toString());
            } else {
                StringBuffer buffer = new StringBuffer();
                buffer.append("<html>");
                buffer.append(getStyle());
                buffer.append("<b>None</b>");
                buffer.append("</html>");
                currentTestCaseBrowser.setText(buffer.toString());
            }

            TestCase[] allTestCases = agent.getGeneratedTestCases();
            if (allTestCases != null) {
                StringBuffer buffer = new StringBuffer();
                buffer.append("<html>");
                buffer.append(getStyle());
                for (int i = 0; i < allTestCases.length; ++i) {
                    buffer.append("<b>Test Case&nbsp;").append(i + 1).append("</b>");
                    buffer.append("<br>");
                    buffer.append(allTestCases[i].toHTML());
                    if (i + 1 < allTestCases.length) {
                        buffer.append("<br>");
                        buffer.append("<br>");
                    }
                }
                buffer.append("</html>");
                allTestCasesBrowser.setText(buffer.toString());
            } else {
                StringBuffer buffer = new StringBuffer();
                buffer.append("<html>");
                buffer.append(getStyle());
                buffer.append("<b>None</b>");
                buffer.append("</html>");
                allTestCasesBrowser.setText(buffer.toString());
            }
            allTestCasesItem.setText("All Test Cases" + (allTestCases != null ? " (" + allTestCases.length + ")" : " (0)"));

            TestCase[] executingTestCases = agent.getExecutingTestCases();
            if (executingTestCases != null) {
                StringBuffer buffer = new StringBuffer();
                buffer.append("<html>");
                buffer.append(getStyle());
                for (int i = 0; i < executingTestCases.length; ++i) {
                    buffer.append("<b>Candidate Test Case&nbsp;").append(i + 1).append("</b>");
                    buffer.append("<br>");
                    buffer.append(executingTestCases[i].toHTML());
                    if (i + 1 < executingTestCases.length) {
                        buffer.append("<br>");
                        buffer.append("<br>");
                    }
                }
                buffer.append("</html>");
                currentIterationTestCasesBrowser.setText(buffer.toString());
            } else {
                StringBuffer buffer = new StringBuffer();
                buffer.append("<html>");
                buffer.append(getStyle());
                buffer.append("<b>None</b>");
                buffer.append("</html>");
                currentIterationTestCasesBrowser.setText(buffer.toString());
            }
            currentIterationTestCasesItem.setText("Current Iteration Test Cases" + (executingTestCases != null ? " (" + executingTestCases.length + ")" : " (0)"));

            String stackTrace = agent.getThrowableStackTrace();
            StringBuffer buffer = new StringBuffer();
            if (stackTrace != null) {
                buffer.append("<html>");
                buffer.append(getStyle());
                buffer.append(stackTrace);
                buffer.append("</html>");
                exceptionBrowser.setText(buffer.toString());
            } else {
                buffer.append("<html>");
                buffer.append(getStyle());
                buffer.append("<b>None</b>");
                buffer.append("</html>");
                exceptionBrowser.setText(buffer.toString());
            }
            exceptionItem.setText("Exceptions" + (stackTrace != null ? " (1)" : " (0)"));
        } catch (RemoteException e) {
            logger.error("An unexpected exception occurred.", e);
        }
    }

    private static String getStyle() {
        return
                "<HEAD>\n" +
                        " <STYLE type=\"text/css\">\n" +
                        "   BODY {font:76% Arial,sans-serif;}\n" +
                        " </STYLE>\n" +
                        "</HEAD>";
    }
}