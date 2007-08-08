package edu.utexas.barad.agent.swt.tester;

import edu.utexas.barad.agent.swt.Displays.Result;
import edu.utexas.barad.agent.swt.Displays.StringResult;
import edu.utexas.barad.agent.swt.proxy.graphics.ImageProxy;
import edu.utexas.barad.agent.swt.proxy.widgets.ItemProxy;
import edu.utexas.barad.agent.swt.proxy.widgets.WidgetProxy;
import edu.utexas.barad.agent.swt.Robot;

import java.util.List;
import java.util.ArrayList;

/**
 * University of Texas at Austin
 * Barad Project, Jul 26, 2007
 */
public class ItemTester extends WidgetTester {
    /**
     * Factory method.
     */
    public static ItemTester getItemTester() {
        return (ItemTester) WidgetTester.getTester(ItemProxy.class);
    }

    /**
     * Constructs a new {@link ItemTester} associated with the specified {@link TesterRobot}.
     */
    public ItemTester(Robot swtRobot) {
        super(swtRobot);
    }

    /* Actions */

    /* Proxies */

    /**
     * Proxy for {@link ItemProxy#getImage()}. <p/>
     *
     * @param item the item under test.
     * @return the image placed on the item.
     */
    public ImageProxy getImage(final ItemProxy item) {
        checkWidget(item);
        return (ImageProxy) syncExec(new Result() {
            public Object result() {
                return item.getImage();
            }
        });
    }

    /**
     * @see ItemProxy#setImage(ImageProxy)
     */
    public void setImage(final ItemProxy item, final ImageProxy image) {
        checkWidget(item);
        syncExec(new Runnable() {
            public void run() {
                item.setImage(image);
            }
        });
    }

    /**
     * Proxy for {@link ItemProxy#getText()}. <p/>
     *
     * @param item the item under test.
     * @return the text of the item.
     */
    public String getText(final ItemProxy item) {
        checkWidget(item);
        return syncExec(new StringResult() {
            public String result() {
                return item.getText();
            }
        });
    }

    /**
     * @see Textable#getText(WidgetProxy)
     */
    public String getText(WidgetProxy widget) {
        return getText((ItemProxy) widget);
    }

    public boolean isTextEditable(WidgetProxy widget) {
        return false;
    }

    /**
     * @see ItemProxy#setText(String)
     */
    public void setText(final ItemProxy item, final String string) {
        checkWidget(item);
        syncExec(new Runnable() {
            public void run() {
                item.setText(string);
            }
        });
    }

    /* Miscellaneous */

    public ItemPath getPath(ItemProxy item) {
        List<String> segments = new ArrayList<String>();
        while (item != null) {
            segments.add(0, getText(item));
            item = getParentItem(item);
        }
        return new ItemPath(segments);
    }

    /**
     * Extenders that represent hierarchical ItemProxy trees should redefine this method.
     *
//     * @see TreeItemTester#getParentItem(ItemProxy)
     * @see MenuItemTester#getParentItem(ItemProxy)
     */
    protected ItemProxy getParentItem(ItemProxy item) {
        return null;
    }
}