package edu.utexas.barad.studio.controls.testcases;

import edu.utexas.barad.common.testcase.TestCase;
import edu.utexas.barad.studio.Images;
import edu.utexas.barad.studio.StudioMain;
import org.apache.log4j.Logger;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.*;

import java.rmi.RemoteException;

/**
 * University of Texas at Austin
 * Barad Project, Jul 29, 2007
 */
public class TestCaseItemWrapper {
    private static final Logger logger = Logger.getLogger(TestCaseItemWrapper.class);

    private CTabFolder tabFolder;
    private CTabItem tabItem;

    public TestCaseItemWrapper(CTabFolder tabFolder) {
        this.tabFolder = tabFolder;
        initialize();
    }

    private void initialize() {
        tabItem = new CTabItem(tabFolder, SWT.NONE);
        tabItem.setText("Test Cases");
        tabItem.setImage(Images.TESTCASES.createImage());

        Composite composite = new Composite(tabFolder, SWT.NONE);
        GridLayout gridLayout = new GridLayout();
        composite.setLayout(gridLayout);
        gridLayout.numColumns = 4;

        Label generateTestCasesLabel = new Label(composite, SWT.WRAP);
        generateTestCasesLabel.setText("Generate test cases:");

        Label separatorLabel = new Label(composite, SWT.SEPARATOR);
        Label currentStatusLabel = new Label(composite, SWT.LEFT);

        Button playButton = new Button(composite, SWT.PUSH);
        playButton.setImage(Images.PLAY.createImage());
        Button pauseButton = new Button(composite, SWT.PUSH);
        pauseButton.setImage(Images.PAUSE.createImage());
        Button stopButton = new Button(composite, SWT.PUSH);
        stopButton.setImage(Images.STOP.createImage());

        Label predicatesLabel = new Label(composite, SWT.LEFT);
        predicatesLabel.setLayoutData(new GridData());        
        Combo predicateCombo = new Combo(composite, SWT.SINGLE);

        final List testCaseStepsList = new List(composite, SWT.SINGLE | SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER);
        testCaseStepsList.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 4, 1));


        playButton.addSelectionListener(new SelectionListener() {
            public void widgetSelected(SelectionEvent event) {
                testCaseStepsList.removeAll();
                try {
                    TestCase[] testCases = StudioMain.getApplicationWindow().getAgent().generateTestCases(null);
                    for (TestCase testCase : testCases) {
                        testCaseStepsList.add(testCase.toString());
                    }
                } catch (RemoteException e) {
                    logger.error(e);
                }
            }

            public void widgetDefaultSelected(SelectionEvent event) {
                // Empty.
            }
        });

        tabItem.setControl(composite);
    }
}