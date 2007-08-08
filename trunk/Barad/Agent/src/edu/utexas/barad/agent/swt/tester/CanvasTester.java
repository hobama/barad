package edu.utexas.barad.agent.swt.tester;

import edu.utexas.barad.agent.swt.Displays;
import edu.utexas.barad.agent.swt.Robot;
import edu.utexas.barad.agent.swt.Displays.Result;
import edu.utexas.barad.agent.swt.proxy.graphics.FontProxy;
import edu.utexas.barad.agent.swt.proxy.widgets.CanvasProxy;
import edu.utexas.barad.agent.swt.proxy.widgets.CaretProxy;

/**
 * University of Texas at Austin
 * Barad Project, Jul 26, 2007
 */
public class CanvasTester extends CompositeTester {
    /**
     * Factory method.
     */
    public static CanvasTester getCanvasTester() {
        return (CanvasTester) getTester(CanvasProxy.class);
    }

    /**
     * Constructs a new {@link CanvasTester} associated with the specified {@link TesterRobot}.
     */
    public CanvasTester(Robot swtRobot) {
        super(swtRobot);
    }

    /*
    * These getter methods return a particular property of the given widget.
    *
    * @see the corresponding member function in class Widget
    */
    /* Begin getters */
    /**
     * Proxy for {@link CanvasProxy#getCaret()}. <p/>
     *
     * @param canvas the canvas under test.
     * @return the caret.
     */
    public CaretProxy getCaret(final CanvasProxy canvas) {
        return (CaretProxy) Displays.syncExec(canvas.getDisplay(), new Result() {
            public Object result() {
                return canvas.getCaret();
            }
        });
    }

    /* End getters */

    /**
     * Proxy for
     * {@link edu.utexas.barad.agent.swt.proxy.widgets.CanvasProxy#scroll(int,int,int,int,int,int,boolean)}.
     */
    public void scroll(final CanvasProxy c, final int destX, final int destY, final int x, final int y,
                       final int width, final int height, final boolean all) {
        syncExec(new Runnable() {
            public void run() {
                c.scroll(destX, destY, x, y, width, height, all);
            }
        });
    }

    /**
     * Proxy for {@link CanvasProxy#setCaret(CaretProxy)}.
     */
    public void setCaret(final CanvasProxy c, final CaretProxy caret) {
        syncExec(new Runnable() {
            public void run() {
                c.setCaret(caret);
            }
        });
    }

    /**
     * Proxy for {@link CanvasProxy#setFont(FontProxy)}
     */
    public void setFont(final CanvasProxy c, final FontProxy font) {
        syncExec(new Runnable() {
            public void run() {
                c.setFont(font);
            }
        });
    }
}