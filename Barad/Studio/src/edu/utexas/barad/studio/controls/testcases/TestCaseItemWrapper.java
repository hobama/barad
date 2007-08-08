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
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.List;

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
        tabItem.setText("Testcases");
        tabItem.setImage(Images.TESTCASES.createImage());

        Composite composite = new Composite(tabFolder, SWT.NONE);
        GridLayout gridLayout = new GridLayout();
        composite.setLayout(gridLayout);
        gridLayout.numColumns = 1;

        Button generateTestcasesButton = new Button(composite, SWT.PUSH);
        generateTestcasesButton.setText("Generate Testcases");
        generateTestcasesButton.setLayoutData(new GridData(SWT.LEFT, SWT.TOP, false, false));

        final List testCaseStepsList = new List(composite, SWT.SINGLE | SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER);
        testCaseStepsList.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

        generateTestcasesButton.addSelectionListener(new SelectionListener() {
            public void widgetSelected(SelectionEvent event) {
                testCaseStepsList.removeAll();
                try {
                    TestCase[] testCases = StudioMain.getApplicationWindow().getAgent().generateTestCases();
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