package edu.utexas.barad.studio;

import org.eclipse.jface.resource.ImageDescriptor;

/**
 * University of Texas at Austin
 * Barad Project, Jul 8, 2007
 */
public class Images {
    public static final ImageDescriptor BARAD_ICON = ImageDescriptor.createFromURL(StudioMain.getImageURL("barad.png"));
    public static final ImageDescriptor ABOUT = ImageDescriptor.createFromURL(StudioMain.getImageURL("about.png"));

    public static final ImageDescriptor HOME = ImageDescriptor.createFromURL(StudioMain.getImageURL("home.png"));
    public static final ImageDescriptor SPY = ImageDescriptor.createFromURL(StudioMain.getImageURL("spy.png"));
    public static final ImageDescriptor TESTCASES = ImageDescriptor.createFromURL(StudioMain.getImageURL("testcases.png"));
    public static final ImageDescriptor REFRESH = ImageDescriptor.createFromURL(StudioMain.getImageURL("refresh.png"));

    public static final ImageDescriptor CONNECT = ImageDescriptor.createFromURL(StudioMain.getImageURL("connect.png"));
    public static final ImageDescriptor DISCONNECT = ImageDescriptor.createFromURL(StudioMain.getImageURL("disconnect.png"));

    public static final ImageDescriptor STATUS_OK = ImageDescriptor.createFromURL(StudioMain.getImageURL("tick.png"));
    public static final ImageDescriptor STATUS_ERROR = ImageDescriptor.createFromURL(StudioMain.getImageURL("exclamation.png"));

    public static final ImageDescriptor WIDGETS_BUTTON = ImageDescriptor.createFromURL(StudioMain.getImageURL("widgets/button.png"));
    public static final ImageDescriptor WIDGETS_DISPLAY = ImageDescriptor.createFromURL(StudioMain.getImageURL("widgets/display.png"));
    public static final ImageDescriptor WIDGETS_SHELL = ImageDescriptor.createFromURL(StudioMain.getImageURL("widgets/shell.png"));
    public static final ImageDescriptor WIDGETS_WINDOW = ImageDescriptor.createFromURL(StudioMain.getImageURL("widgets/window.png"));
    public static final ImageDescriptor WIDGETS_MENU = ImageDescriptor.createFromURL(StudioMain.getImageURL("widgets/menu.png"));
    public static final ImageDescriptor WIDGETS_TEXT = ImageDescriptor.createFromURL(StudioMain.getImageURL("widgets/text.png"));
    public static final ImageDescriptor WIDGETS_LABEL = ImageDescriptor.createFromURL(StudioMain.getImageURL("widgets/label.png"));
    public static final ImageDescriptor WIDGETS_TAB = ImageDescriptor.createFromURL(StudioMain.getImageURL("widgets/tab.png"));
    public static final ImageDescriptor WIDGETS_TABLE = ImageDescriptor.createFromURL(StudioMain.getImageURL("widgets/table.png"));
    public static final ImageDescriptor WIDGETS_TREE = ImageDescriptor.createFromURL(StudioMain.getImageURL("widgets/tree.png"));
    public static final ImageDescriptor WIDGETS_OTHER = ImageDescriptor.createFromURL(StudioMain.getImageURL("widgets/other.png"));

    public static final ImageDescriptor START = ImageDescriptor.createFromURL(StudioMain.getImageURL("play.png"));
    public static final ImageDescriptor PAUSE = ImageDescriptor.createFromURL(StudioMain.getImageURL("pause.png"));
    public static final ImageDescriptor STOP = ImageDescriptor.createFromURL(StudioMain.getImageURL("stop.png"));

    private Images() {
        // Private constructor.
    }
}