package edu.utexas.barad.studio.controls.widgetspy;

import edu.utexas.barad.agent.IAgent;
import edu.utexas.barad.common.swt.WidgetInfo;
import edu.utexas.barad.common.swt.WidgetValues;
import edu.utexas.barad.studio.Images;
import edu.utexas.barad.studio.StudioMain;
import edu.utexas.barad.studio.actions.ConnectAction;
import edu.utexas.barad.studio.actions.DisconnectAction;
import org.apache.log4j.Logger;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.StatusLineManager;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.jface.viewers.*;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.events.ControlAdapter;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.ScrollBar;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;

import java.lang.reflect.InvocationTargetException;
import java.rmi.RemoteException;

/**
 * University of Texas at Austin
 * Barad Project, Jul 10, 2007
 */
public class SpyTabItemWrapper implements IPropertyChangeListener, ISelectionChangedListener {
    private static final Logger logger = Logger.getLogger(SpyTabItemWrapper.class);

    private CTabFolder tabFolder;
    private TreeViewer treeViewer;
    private TableViewer tableViewer;
    private CTabItem tabItem;
    private Table table;
    private TableColumn nameColumn;
    private TableColumn valueColumn;

    public SpyTabItemWrapper(CTabFolder tabFolder) {
        this.tabFolder = tabFolder;
        initialize();
    }

    private void initialize() {
        tabItem = new CTabItem(tabFolder, SWT.NONE);
        tabItem.setText("Widget Spy");
        tabItem.setImage(Images.SPY.createImage());

        final SashForm sashForm = new SashForm(tabFolder, SWT.VERTICAL);

        treeViewer = new TreeViewer(sashForm);
        treeViewer.setContentProvider(new SpyTreeContentProvider());
        treeViewer.setLabelProvider(new SpyTreeLabelProvider());
        treeViewer.setComparer(new SpyTreeElementComparer());
        treeViewer.addSelectionChangedListener(this);

        tableViewer = new TableViewer(sashForm, SWT.BORDER);
        tableViewer.setContentProvider(new SpyTableContentProvider());
        tableViewer.setLabelProvider(new SpyTableLabelProvider());

        nameColumn = new TableColumn(tableViewer.getTable(), SWT.LEFT, 0);
        nameColumn.setText("Name");
        nameColumn.addSelectionListener(new SelectionListener() {
            public void widgetSelected(SelectionEvent e) {
                tableViewer.setSorter(new ViewerSorter());
            }

            public void widgetDefaultSelected(SelectionEvent e) {
                tableViewer.setSorter(new ViewerSorter());
            }
        });

        valueColumn = new TableColumn(tableViewer.getTable(), SWT.LEFT, 1);
        valueColumn.setText("Value");

        table = tableViewer.getTable();
        table.setHeaderVisible(true);
        table.setLinesVisible(true);

        tabFolder.addControlListener(new ControlAdapter() {
            public void controlResized(ControlEvent event) {
                resizeTable();
            }
        });

        tabItem.setControl(sashForm);
    }

    private void resizeTable() {
        Rectangle area = tabFolder.getClientArea();
        Point size = table.computeSize(SWT.DEFAULT, SWT.DEFAULT);
        ScrollBar verticalBar = table.getVerticalBar();
        int width = area.width - table.computeTrim(0, 0, 0, 0).width - verticalBar.getSize().x;
        if (size.y > area.height + table.getHeaderHeight()) {
            // Subtract the scrollbar width from the total column width
            // if a vertical scrollbar will be required
            Point barSize = verticalBar.getSize();
            width -= barSize.x;
        }
        Point oldSize = table.getSize();
        if (oldSize.x > area.width) {
            // table is getting smaller so make the columns
            // smaller first and then resize the table to
            // match the client area width
            nameColumn.setWidth(width / 3);
            valueColumn.setWidth(width - nameColumn.getWidth());
            table.setSize(area.width, area.height);
        } else {
            // table is getting bigger so make the table
            // bigger first and then make the columns wider
            // to match the client area width
            table.setSize(area.width, area.height);
            nameColumn.setWidth(width / 3);
            valueColumn.setWidth(width - nameColumn.getWidth());
        }
    }

    public CTabItem getTabItem() {
        return tabItem;
    }

    public void propertyChange(PropertyChangeEvent event) {
        if (event == null) {
            return;
        }

        Object source = event.getSource();
        String property = event.getProperty();
        Object newValue = event.getNewValue();

        if (source instanceof ConnectAction && IAction.RESULT.equals(property) && Boolean.TRUE.equals(newValue)) {
            getWidgetHierarchy(false);
        } else
        if (source instanceof DisconnectAction && IAction.RESULT.equals(property) && Boolean.TRUE.equals(newValue)) {
            treeViewer.setInput(null);
            tableViewer.setInput(null);
        }
    }

    private void getWidgetHierarchy(final boolean rebuild) {
        // New connection to agent succeeded.
        final StudioMain applicationWindow = StudioMain.getApplicationWindow();

        // Clear any existing error message.
        StatusLineManager statusLineManager = applicationWindow.getStatusLineManager();
        statusLineManager.setErrorMessage(null);

        // Update the spy tree with the UI object hierarchy.
        final Object[] holder = new Object[1];
        try {
            applicationWindow.run(true, false, new IRunnableWithProgress() {
                public void run(IProgressMonitor monitor) throws InvocationTargetException, InterruptedException {
                    IAgent agent = applicationWindow.getAgent();
                    monitor.beginTask("Getting widget hierarchy...", IProgressMonitor.UNKNOWN);
                    monitor.worked(1);
                    try {
                        WidgetInfo hierarchy = agent.getWidgetHierarchy(rebuild);
                        holder[0] = hierarchy;
                    } catch (RemoteException e) {
                        logger.error("Couldn't get UI object hierarchy.", e);
                    } finally {
                        monitor.done();
                    }
                }
            });
        } catch (Exception e) {
            logger.error("An unexpected exception occurred getting UI object hierarchy.", e);
        }

        WidgetInfo hierarchy = (WidgetInfo) holder[0];
        if (hierarchy != null) {
            // Try to maintain the current tree selection.
            ITreeSelection selection = (ITreeSelection) treeViewer.getSelection();
            if (selection.isEmpty()) {
                selection = new TreeSelection(new TreePath(new Object[]{hierarchy}));
            }
            treeViewer.setInput(hierarchy);
            treeViewer.expandAll();
            treeViewer.setSelection(selection, true); // Reveal the selection if it's hidden.

            tabFolder.setSelection(tabItem);
            statusLineManager.setMessage(Images.STATUS_OK.createImage(), "Ready.");
        } else {
            statusLineManager.setMessage(Images.STATUS_ERROR.createImage(), "Couldn't get widget hierarchy.");
        }
    }

    public void selectionChanged(SelectionChangedEvent event) {
        IStructuredSelection structuredSelection = (IStructuredSelection) event.getSelection();
        final WidgetInfo widgetInfo = (WidgetInfo) structuredSelection.getFirstElement();
        if (widgetInfo == null) {
            return;
        }

        // New connection to agent succeeded.
        final StudioMain applicationWindow = StudioMain.getApplicationWindow();
        final IAgent agent = applicationWindow.getAgent();
        if (agent == null) {
            return;
        }

        // Clear any existing error message.
        StatusLineManager statusLineManager = applicationWindow.getStatusLineManager();
        statusLineManager.setErrorMessage(null);

        // Update the spy tree with the UI object hierarchy.
        final Object[] holder = new Object[1];
        try {
            applicationWindow.run(true, false, new IRunnableWithProgress() {
                public void run(IProgressMonitor monitor) throws InvocationTargetException, InterruptedException {
                    monitor.beginTask("Getting widget values...", IProgressMonitor.UNKNOWN);
                    monitor.worked(1);
                    try {
                        WidgetValues widgetValues = agent.getWidgetValues(widgetInfo);
                        holder[0] = widgetValues;
                    } catch (RemoteException e) {
                        logger.error("Couldn't get widget values.", e);
                    } finally {
                        monitor.done();
                    }
                }
            });
        } catch (Exception e) {
            logger.error("An unexpected exception occurred getting widget values.", e);
        }

        WidgetValues widgetValues = (WidgetValues) holder[0];
        if (widgetValues != null) {
            tableViewer.setInput(widgetValues);
            statusLineManager.setMessage(Images.STATUS_OK.createImage(), "Ready.");
        } else {
            statusLineManager.setMessage(Images.STATUS_ERROR.createImage(), "Couldn't get widget values.");
        }
    }

    public void refresh() {
        getWidgetHierarchy(true);
    }
}