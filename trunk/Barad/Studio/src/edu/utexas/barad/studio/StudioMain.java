package edu.utexas.barad.studio;

import edu.utexas.barad.agent.IAgent;
import edu.utexas.barad.studio.actions.*;
import edu.utexas.barad.studio.controls.widgetspy.SpyTabItemWrapper;
import edu.utexas.barad.studio.exceptions.StudioRuntimeException;
import org.apache.log4j.Logger;
import org.eclipse.jface.action.*;
import org.eclipse.jface.window.ApplicationWindow;
import org.eclipse.swt.SWT;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import java.net.URL;

/**
 * University of Texas at Austin
 * Barad Project, Jul 1, 2007
 *
 * @author Chip Killmar (ckillmar@mail.utexas.edu)
 */
public class StudioMain extends ApplicationWindow {
    public static final String SHELL_TITLE = "Barad Studio";

    private static final Logger logger = Logger.getLogger(StudioMain.class);
    private static StudioMain studioMain;

    private IAgent agent;
    private CTabFolder tabFolder;
    private SpyTabItemWrapper spyTabItemWrapper;

    private ConnectAction connectAction;
    private DisconnectAction disconnectAction;
    private RefreshAction refreshAction;
    private ExitAction exitAction;
    private AboutAction aboutAction;

    private StudioMain() {
        super(null);
        createActions();
        addMenuBar();
        addToolBar(SWT.FLAT | SWT.WRAP);
        addStatusLine();
    }

    public static synchronized StudioMain getApplicationWindow() {
        if (studioMain == null) {
            studioMain = new StudioMain();
        }
        return studioMain;
    }

    @Override
    protected void configureShell(Shell shell) {
        super.configureShell(shell);
        shell.setText(SHELL_TITLE);
        shell.setImage(Images.BARAD_ICON.createImage());
        getStatusLineManager().setMessage(Images.STATUS_OK.createImage(true), "Ready.");
    }

    @Override
    protected void initializeBounds() {
        getShell().setSize(800, 600);
    }

    @Override
    protected MenuManager createMenuManager() {
        MenuManager menuManager = new MenuManager();
        MenuManager fileMenu = new MenuManager("&File");
        fileMenu.add(connectAction);
        fileMenu.add(disconnectAction);
        fileMenu.add(new Separator());
        fileMenu.add(exitAction);
        menuManager.add(fileMenu);

        MenuManager viewMenu = new MenuManager("&View");
        viewMenu.add(refreshAction);
        menuManager.add(viewMenu);

        MenuManager aboutMenu = new MenuManager("&Help");
        aboutMenu.add(aboutAction);
        menuManager.add(aboutMenu);

        return menuManager;
    }

    @Override
    protected ToolBarManager createToolBarManager(int style) {
        ToolBarManager toolBarManager = new ToolBarManager(style);

        ActionContributionItem actionContributionItem = new ActionContributionItem(connectAction);
        actionContributionItem.setMode(ActionContributionItem.MODE_FORCE_TEXT);
        toolBarManager.add(actionContributionItem);

        actionContributionItem = new ActionContributionItem(disconnectAction);
        actionContributionItem.setMode(ActionContributionItem.MODE_FORCE_TEXT);
        toolBarManager.add(actionContributionItem);

        toolBarManager.add(new Separator());

        actionContributionItem = new ActionContributionItem(refreshAction);
        actionContributionItem.setMode(ActionContributionItem.MODE_FORCE_TEXT);
        toolBarManager.add(actionContributionItem);

        return toolBarManager;
    }

    private void createActions() {
        connectAction = new ConnectAction("&Connect", Images.CONNECT);
        connectAction.setAccelerator(SWT.CTRL | 'C');

        disconnectAction = new DisconnectAction("&Disconnect", Images.DISCONNECT);

        refreshAction = new RefreshAction("&Refresh", Images.REFRESH);
        refreshAction.setAccelerator(SWT.F5);

        aboutAction = new AboutAction("&About Barad Studio");

        exitAction = new ExitAction("E&xit");
        exitAction.setAccelerator(SWT.CTRL | SWT.F4);
    }

    @Override
    protected Control createContents(Composite parent) {
        tabFolder = new CTabFolder(parent, SWT.BORDER);

        CTabItem homeTabItem = new CTabItem(tabFolder, SWT.NONE);
        homeTabItem.setText("Home");
        homeTabItem.setImage(Images.HOME.createImage());
        Browser browser = new Browser(tabFolder, SWT.NONE);
        browser.setUrl("www.google.com");
        homeTabItem.setControl(browser);

        spyTabItemWrapper = new SpyTabItemWrapper(tabFolder);
        connectAction.addPropertyChangeListener(spyTabItemWrapper);
        disconnectAction.addPropertyChangeListener(spyTabItemWrapper);

        tabFolder.addSelectionListener(refreshAction);
        tabFolder.setSimple(false);
        tabFolder.setSelection(homeTabItem);

        updateActions();

        return tabFolder;
    }

    public static URL getImageURL(String imageFileName) {
        URL imageURL = StudioMain.class.getResource("/edu/utexas/barad/studio/resources/images/" + imageFileName);
        if (imageURL == null) {
            throw new StudioRuntimeException("Couldn't load image, imageFileName=" + imageFileName);
        }
        return imageURL;
    }

    public IAgent getAgent() {
        return agent;
    }

    public void setAgent(IAgent agent) {
        this.agent = agent;
    }

    public boolean isConnected() {
        return getAgent() != null;
    }

    public void updateActions() {
        connectAction.setEnabled(!isConnected());
        disconnectAction.setEnabled(isConnected());

        if (tabFolder != null) {
            CTabItem selectedTabItem = tabFolder.getSelection();
            if (selectedTabItem == spyTabItemWrapper.getTabItem()) {
                refreshAction.setEnabled(isConnected());
            } else {
                refreshAction.setEnabled(false);
            }
        }

        exitAction.setEnabled(true);
    }

    public void refresh() {
        CTabItem selectedTabItem = tabFolder.getSelection();
        if (selectedTabItem == spyTabItemWrapper.getTabItem()) {
            spyTabItemWrapper.refresh();
        }
    }

    @Override
    public StatusLineManager getStatusLineManager() {
        return super.getStatusLineManager();
    }

    public static void main(String[] args) throws Exception {
        StudioMain studioMain = getApplicationWindow();
        studioMain.setBlockOnOpen(true);
        try {
            studioMain.open();
        } catch (Exception e) {
            logger.debug("An unexpected exception occurred.", e);
        }
        Display.getCurrent().dispose();
    }
}