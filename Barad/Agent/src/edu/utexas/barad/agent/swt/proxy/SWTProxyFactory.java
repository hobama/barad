package edu.utexas.barad.agent.swt.proxy;

import edu.utexas.barad.agent.AgentUtils;
import edu.utexas.barad.agent.exceptions.AgentRuntimeException;
import edu.utexas.barad.agent.proxy.AbstractProxyFactory;
import edu.utexas.barad.agent.swt.proxy.custom.CTabFolderProxy;
import edu.utexas.barad.agent.swt.proxy.custom.CTabItemProxy;
import edu.utexas.barad.agent.swt.proxy.graphics.*;
import edu.utexas.barad.agent.swt.proxy.widgets.*;
import edu.utexas.barad.agent.swt.proxy.dnd.DragSourceProxy;
import edu.utexas.barad.agent.swt.proxy.dnd.DropTargetProxy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * University of Texas at Austin
 * Barad Project, Jul 4, 2007
 */
public class SWTProxyFactory extends AbstractProxyFactory {
    public static final String ORG_ECLIPSE_SWT_WIDGETS_BUTTON = "org.eclipse.swt.widgets.Button";
    public static final String ORG_ECLIPSE_SWT_WIDGETS_CANVAS = "org.eclipse.swt.widgets.Canvas";
    public static final String ORG_ECLIPSE_SWT_WIDGETS_CARET = "org.eclipse.swt.widgets.Caret";
    public static final String ORG_ECLIPSE_SWT_WIDGETS_COMBO = "org.eclipse.swt.widgets.Combo";
    public static final String ORG_ECLIPSE_SWT_WIDGETS_COMPOSITE = "org.eclipse.swt.widgets.Composite";
    public static final String ORG_ECLIPSE_SWT_WIDGETS_CONTROL = "org.eclipse.swt.widgets.Control";
    public static final String ORG_ECLIPSE_SWT_WIDGETS_COOLBAR = "org.eclipse.swt.widgets.CoolBar";
    public static final String ORG_ECLIPSE_SWT_WIDGETS_COOLITEM = "org.eclipse.swt.widgets.CoolItem";
    public static final String ORG_ECLIPSE_SWT_WIDGETS_DECORATIONS = "org.eclipse.swt.widgets.Decorations";
    public static final String ORG_ECLIPSE_SWT_WIDGETS_DISPLAY = "org.eclipse.swt.widgets.Display";
    public static final String ORG_ECLIPSE_SWT_WIDGETS_EVENT = "org.eclipse.swt.widgets.Event";
    public static final String ORG_ECLIPSE_SWT_WIDGETS_ITEM = "org.eclipse.swt.widgets.Item";
    public static final String ORG_ECLIPSE_SWT_WIDGETS_LABEL = "org.eclipse.swt.widgets.Label";
    public static final String ORG_ECLIPSE_SWT_WIDGETS_LAYOUT = "org.eclipse.swt.widgets.Layout";
    public static final String ORG_ECLIPSE_SWT_WIDGETS_LISTENER = "org.eclipse.swt.widgets.Listener";
    public static final String ORG_ECLIPSE_SWT_WIDGETS_LIST = "org.eclipse.swt.widgets.List";
    public static final String ORG_ECLIPSE_SWT_WIDGETS_MENUITEM = "org.eclipse.swt.widgets.MenuItem";
    public static final String ORG_ECLIPSE_SWT_WIDGETS_MENU = "org.eclipse.swt.widgets.Menu";
    public static final String ORG_ECLIPSE_SWT_WIDGETS_MONITOR = "org.eclipse.swt.widgets.Monitor";
    public static final String ORG_ECLIPSE_SWT_WIDGETS_PROGRESSBAR = "org.eclipse.swt.widgets.ProgressBar";
    public static final String ORG_ECLIPSE_SWT_WIDGETS_SASH = "org.eclipse.swt.widgets.Sash";
    public static final String ORG_ECLIPSE_SWT_WIDGETS_SCROLLABLE = "org.eclipse.swt.widgets.Scrollable";
    public static final String ORG_ECLIPSE_SWT_WIDGETS_SCROLLBAR = "org.eclipse.swt.widgets.ScrollBar";
    public static final String ORG_ECLIPSE_SWT_WIDGETS_SHELL = "org.eclipse.swt.widgets.Shell";
    public static final String ORG_ECLIPSE_SWT_WIDGETS_TABFOLDER = "org.eclipse.swt.widgets.TabFolder";
    public static final String ORG_ECLIPSE_SWT_WIDGETS_TABITEM = "org.eclipse.swt.widgets.TabItem";
    public static final String ORG_ECLIPSE_SWT_WIDGETS_TABLECOLUMN = "org.eclipse.swt.widgets.TableColumn";
    public static final String ORG_ECLIPSE_SWT_WIDGETS_TABLEITEM = "org.eclipse.swt.widgets.TableItem";
    public static final String ORG_ECLIPSE_SWT_WIDGETS_TABLE = "org.eclipse.swt.widgets.Table";
    public static final String ORG_ECLIPSE_SWT_WIDGETS_TEXT = "org.eclipse.swt.widgets.Text";
    public static final String ORG_ECLIPSE_SWT_WIDGETS_TOOLBAR = "org.eclipse.swt.widgets.ToolBar";
    public static final String ORG_ECLIPSE_SWT_WIDGETS_TOOLITEM = "org.eclipse.swt.widgets.ToolItem";
    public static final String ORG_ECLIPSE_SWT_WIDGETS_TRAYITEM = "org.eclipse.swt.widgets.TrayItem";
    public static final String ORG_ECLIPSE_SWT_WIDGETS_TRAY = "org.eclipse.swt.widgets.Tray";
    public static final String ORG_ECLIPSE_SWT_WIDGETS_TREECOLUMN = "org.eclipse.swt.widgets.TreeColumn";
    public static final String ORG_ECLIPSE_SWT_WIDGETS_TREEITEM = "org.eclipse.swt.widgets.TreeItem";
    public static final String ORG_ECLIPSE_SWT_WIDGETS_TREE = "org.eclipse.swt.widgets.Tree";
    public static final String ORG_ECLIPSE_SWT_WIDGETS_WIDGET = "org.eclipse.swt.widgets.Widget";

    public static final String ORG_ECLIPSE_SWT_DND_DRAGSOURCE = "org.eclipse.swt.dnd.DragSource";
    public static final String ORG_ECLIPSE_SWT_DND_DROPTARGET = "org.eclipse.swt.dnd.DropTarget";

    public static final String ORG_ECLIPSE_SWT_GRAPHICS_COLOR = "org.eclipse.swt.graphics.Color";
    public static final String ORG_ECLIPSE_SWT_GRAPHICS_CURSOR = "org.eclipse.swt.graphics.Cursor";
    public static final String ORG_ECLIPSE_SWT_GRAPHICS_DEVICE = "org.eclipse.swt.graphics.Device";
    public static final String ORG_ECLIPSE_SWT_GRAPHICS_DRAWABLE = "org.eclipse.swt.graphics.Drawable";
    public static final String ORG_ECLIPSE_SWT_GRAPHICS_FONTDATA = "org.eclipse.swt.graphics.FontData";
    public static final String ORG_ECLIPSE_SWT_GRAPHICS_FONT = "org.eclipse.swt.graphics.Font";
    public static final String ORG_ECLIPSE_SWT_GRAPHICS_GC = "org.eclipse.swt.graphics.GC";
    public static final String ORG_ECLIPSE_SWT_GRAPHICS_IMAGEDATA = "org.eclipse.swt.graphics.ImageData";
    public static final String ORG_ECLIPSE_SWT_GRAPHICS_IMAGE = "org.eclipse.swt.graphics.Image";
    public static final String ORG_ECLIPSE_SWT_GRAPHICS_PALETTEDATA = "org.eclipse.swt.graphics.PaletteData";
    public static final String ORG_ECLIPSE_SWT_GRAPHICS_POINT = "org.eclipse.swt.graphics.Point";
    public static final String ORG_ECLIPSE_SWT_GRAPHICS_RECTANGLE = "org.eclipse.swt.graphics.Rectangle";
    public static final String ORG_ECLIPSE_SWT_GRAPHICS_REGION = "org.eclipse.swt.graphics.Region";
    public static final String ORG_ECLIPSE_SWT_GRAPHICS_RESOURCE = "org.eclipse.swt.graphics.Resource";
    public static final String ORG_ECLIPSE_SWT_GRAPHICS_RGB = "org.eclipse.swt.graphics.RGB";

    public static final String ORG_ECLIPSE_SWT_CUSTOM_CTABFOLDER = "org.eclipse.swt.custom.CTabFolder";
    public static final String ORG_ECLIPSE_SWT_CUSTOM_CTABITEM = "org.eclipse.swt.custom.CTabItem";

    private static SWTProxyFactory instance;
    private static final Map<Class, Class> actualClassToProxyClassMap = new HashMap<Class, Class>();
    private static final Map<Class, Class> proxyClassToActualClassMap = new HashMap<Class, Class>();
    private static final List<Class> assignableClassList = new ArrayList<Class>();

    private SWTProxyFactory() {
        // org.eclipse.swt.widgets.*
        actualClassToProxyClassMap.put(AgentUtils.swtClassForName(ORG_ECLIPSE_SWT_WIDGETS_BUTTON), ButtonProxy.class);
        actualClassToProxyClassMap.put(AgentUtils.swtClassForName(ORG_ECLIPSE_SWT_WIDGETS_CANVAS), CanvasProxy.class);
        actualClassToProxyClassMap.put(AgentUtils.swtClassForName(ORG_ECLIPSE_SWT_WIDGETS_CARET), CaretProxy.class);
        actualClassToProxyClassMap.put(AgentUtils.swtClassForName(ORG_ECLIPSE_SWT_WIDGETS_COMBO), ComboProxy.class);
        actualClassToProxyClassMap.put(AgentUtils.swtClassForName(ORG_ECLIPSE_SWT_WIDGETS_COMPOSITE), CompositeProxy.class);
        actualClassToProxyClassMap.put(AgentUtils.swtClassForName(ORG_ECLIPSE_SWT_WIDGETS_CONTROL), ControlProxy.class);
        actualClassToProxyClassMap.put(AgentUtils.swtClassForName(ORG_ECLIPSE_SWT_WIDGETS_COOLBAR), CoolBarProxy.class);
        actualClassToProxyClassMap.put(AgentUtils.swtClassForName(ORG_ECLIPSE_SWT_WIDGETS_COOLITEM), CoolItemProxy.class);
        actualClassToProxyClassMap.put(AgentUtils.swtClassForName(ORG_ECLIPSE_SWT_WIDGETS_DECORATIONS), DecorationsProxy.class);
        actualClassToProxyClassMap.put(AgentUtils.swtClassForName(ORG_ECLIPSE_SWT_WIDGETS_DISPLAY), DisplayProxy.class);
        actualClassToProxyClassMap.put(AgentUtils.swtClassForName(ORG_ECLIPSE_SWT_WIDGETS_EVENT), EventProxy.class);
        actualClassToProxyClassMap.put(AgentUtils.swtClassForName(ORG_ECLIPSE_SWT_WIDGETS_ITEM), ItemProxy.class);
        actualClassToProxyClassMap.put(AgentUtils.swtClassForName(ORG_ECLIPSE_SWT_WIDGETS_LABEL), LabelProxy.class);
        actualClassToProxyClassMap.put(AgentUtils.swtClassForName(ORG_ECLIPSE_SWT_WIDGETS_LAYOUT), LayoutProxy.class);
        actualClassToProxyClassMap.put(AgentUtils.swtClassForName(ORG_ECLIPSE_SWT_WIDGETS_LISTENER), ListenerProxy.class);
        actualClassToProxyClassMap.put(AgentUtils.swtClassForName(ORG_ECLIPSE_SWT_WIDGETS_LIST), ListProxy.class);
        actualClassToProxyClassMap.put(AgentUtils.swtClassForName(ORG_ECLIPSE_SWT_WIDGETS_MENUITEM), MenuItemProxy.class);
        actualClassToProxyClassMap.put(AgentUtils.swtClassForName(ORG_ECLIPSE_SWT_WIDGETS_MENU), MenuProxy.class);
        actualClassToProxyClassMap.put(AgentUtils.swtClassForName(ORG_ECLIPSE_SWT_WIDGETS_MONITOR), MonitorProxy.class);
        actualClassToProxyClassMap.put(AgentUtils.swtClassForName(ORG_ECLIPSE_SWT_WIDGETS_PROGRESSBAR), ProgressBarProxy.class);
        actualClassToProxyClassMap.put(AgentUtils.swtClassForName(ORG_ECLIPSE_SWT_WIDGETS_SASH), SashProxy.class);
        actualClassToProxyClassMap.put(AgentUtils.swtClassForName(ORG_ECLIPSE_SWT_WIDGETS_SCROLLABLE), ScrollableProxy.class);
        actualClassToProxyClassMap.put(AgentUtils.swtClassForName(ORG_ECLIPSE_SWT_WIDGETS_SCROLLBAR), ScrollBarProxy.class);
        actualClassToProxyClassMap.put(AgentUtils.swtClassForName(ORG_ECLIPSE_SWT_WIDGETS_SHELL), ShellProxy.class);
        actualClassToProxyClassMap.put(AgentUtils.swtClassForName(ORG_ECLIPSE_SWT_WIDGETS_TABFOLDER), TabFolderProxy.class);
        actualClassToProxyClassMap.put(AgentUtils.swtClassForName(ORG_ECLIPSE_SWT_WIDGETS_TABITEM), TabItemProxy.class);
        actualClassToProxyClassMap.put(AgentUtils.swtClassForName(ORG_ECLIPSE_SWT_WIDGETS_TABLECOLUMN), TableColumnProxy.class);
        actualClassToProxyClassMap.put(AgentUtils.swtClassForName(ORG_ECLIPSE_SWT_WIDGETS_TABLEITEM), TableItemProxy.class);
        actualClassToProxyClassMap.put(AgentUtils.swtClassForName(ORG_ECLIPSE_SWT_WIDGETS_TABLE), TableProxy.class);
        actualClassToProxyClassMap.put(AgentUtils.swtClassForName(ORG_ECLIPSE_SWT_WIDGETS_TEXT), TextProxy.class);
        actualClassToProxyClassMap.put(AgentUtils.swtClassForName(ORG_ECLIPSE_SWT_WIDGETS_TOOLBAR), ToolBarProxy.class);
        actualClassToProxyClassMap.put(AgentUtils.swtClassForName(ORG_ECLIPSE_SWT_WIDGETS_TOOLITEM), ToolItemProxy.class);
        actualClassToProxyClassMap.put(AgentUtils.swtClassForName(ORG_ECLIPSE_SWT_WIDGETS_TRAYITEM), TrayItemProxy.class);
        actualClassToProxyClassMap.put(AgentUtils.swtClassForName(ORG_ECLIPSE_SWT_WIDGETS_TRAY), TrayProxy.class);
        actualClassToProxyClassMap.put(AgentUtils.swtClassForName(ORG_ECLIPSE_SWT_WIDGETS_TREECOLUMN), TreeColumnProxy.class);
        actualClassToProxyClassMap.put(AgentUtils.swtClassForName(ORG_ECLIPSE_SWT_WIDGETS_TREEITEM), TreeItemProxy.class);
        actualClassToProxyClassMap.put(AgentUtils.swtClassForName(ORG_ECLIPSE_SWT_WIDGETS_TREE), TreeProxy.class);
        actualClassToProxyClassMap.put(AgentUtils.swtClassForName(ORG_ECLIPSE_SWT_WIDGETS_WIDGET), WidgetProxy.class);

        // org.eclipse.swt.dnd.*
        actualClassToProxyClassMap.put(AgentUtils.swtClassForName(ORG_ECLIPSE_SWT_DND_DRAGSOURCE), DragSourceProxy.class);
        actualClassToProxyClassMap.put(AgentUtils.swtClassForName(ORG_ECLIPSE_SWT_DND_DROPTARGET), DropTargetProxy.class);

        // org.eclipse.swt.graphics.*
        actualClassToProxyClassMap.put(AgentUtils.swtClassForName(ORG_ECLIPSE_SWT_GRAPHICS_COLOR), ColorProxy.class);
        actualClassToProxyClassMap.put(AgentUtils.swtClassForName(ORG_ECLIPSE_SWT_GRAPHICS_CURSOR), CursorProxy.class);
        actualClassToProxyClassMap.put(AgentUtils.swtClassForName(ORG_ECLIPSE_SWT_GRAPHICS_DEVICE), DeviceProxy.class);
        actualClassToProxyClassMap.put(AgentUtils.swtClassForName(ORG_ECLIPSE_SWT_GRAPHICS_FONTDATA), FontDataProxy.class);
        actualClassToProxyClassMap.put(AgentUtils.swtClassForName(ORG_ECLIPSE_SWT_GRAPHICS_FONT), FontProxy.class);
        actualClassToProxyClassMap.put(AgentUtils.swtClassForName(ORG_ECLIPSE_SWT_GRAPHICS_GC), GCProxy.class);
        actualClassToProxyClassMap.put(AgentUtils.swtClassForName(ORG_ECLIPSE_SWT_GRAPHICS_IMAGEDATA), ImageDataProxy.class);
        actualClassToProxyClassMap.put(AgentUtils.swtClassForName(ORG_ECLIPSE_SWT_GRAPHICS_IMAGE), ImageProxy.class);
        actualClassToProxyClassMap.put(AgentUtils.swtClassForName(ORG_ECLIPSE_SWT_GRAPHICS_POINT), PointProxy.class);
        actualClassToProxyClassMap.put(AgentUtils.swtClassForName(ORG_ECLIPSE_SWT_GRAPHICS_RECTANGLE), RectangleProxy.class);
        actualClassToProxyClassMap.put(AgentUtils.swtClassForName(ORG_ECLIPSE_SWT_GRAPHICS_REGION), RegionProxy.class);
        actualClassToProxyClassMap.put(AgentUtils.swtClassForName(ORG_ECLIPSE_SWT_GRAPHICS_RESOURCE), ResourceProxy.class);
        actualClassToProxyClassMap.put(AgentUtils.swtClassForName(ORG_ECLIPSE_SWT_GRAPHICS_RGB), RGBProxy.class);

        // org.eclipse.swt.custom.*
        actualClassToProxyClassMap.put(AgentUtils.swtClassForName(ORG_ECLIPSE_SWT_CUSTOM_CTABFOLDER), CTabFolderProxy.class);
        actualClassToProxyClassMap.put(AgentUtils.swtClassForName(ORG_ECLIPSE_SWT_CUSTOM_CTABITEM), CTabItemProxy.class);

        assignableClassList.add(AgentUtils.swtClassForName(ORG_ECLIPSE_SWT_WIDGETS_SHELL));
        assignableClassList.add(AgentUtils.swtClassForName(ORG_ECLIPSE_SWT_WIDGETS_COMPOSITE));
        assignableClassList.add(AgentUtils.swtClassForName(ORG_ECLIPSE_SWT_WIDGETS_CONTROL));
        assignableClassList.add(AgentUtils.swtClassForName(ORG_ECLIPSE_SWT_WIDGETS_MENU));
        assignableClassList.add(AgentUtils.swtClassForName(ORG_ECLIPSE_SWT_WIDGETS_MENUITEM));
        assignableClassList.add(AgentUtils.swtClassForName(ORG_ECLIPSE_SWT_WIDGETS_WIDGET));

        // Now build the reverse mapping.
        for (Map.Entry<Class, Class> entry : actualClassToProxyClassMap.entrySet()) {
            proxyClassToActualClassMap.put(entry.getValue(), entry.getKey());
        }
    }

    public static synchronized SWTProxyFactory getInstance() {
        if (instance == null) {
            instance = new SWTProxyFactory();
        }
        return instance;
    }

    protected Map<Class, Class> getActualClassToProxyClassMap() {
        return actualClassToProxyClassMap;
    }

    @Override
    public Class getProxyClass(Class actualClass) {
        Map<Class, Class> actualClassToProxyClassMap = getActualClassToProxyClassMap();

        // First check if the actual class is one of the SWT classes.
        Class proxyClass = actualClassToProxyClassMap.get(actualClass);

        if (proxyClass == null) {
            // Is the actual class a subclass of one of the SWT classes?
            for (Class clazz : assignableClassList) {
                if (clazz.isAssignableFrom(actualClass)) {
                    proxyClass = actualClassToProxyClassMap.get(clazz);
                    break;
                }
            }
        }

        if (proxyClass == null) {
            // We couldn't find a proxy class for the actual class.
            throw new AgentRuntimeException("No proxy class available, actualClass=" + actualClass);
        }

        return proxyClass;
    }

    public Class getActualClass(Class proxyClass) {
        return proxyClassToActualClassMap.get(proxyClass);
    }
}