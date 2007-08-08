package edu.utexas.barad.agent.swt.tester;

import edu.utexas.barad.agent.swt.Displays.BooleanResult;
import edu.utexas.barad.agent.swt.Displays.Result;
import edu.utexas.barad.agent.swt.Displays.StringResult;
import edu.utexas.barad.agent.swt.proxy.graphics.ImageProxy;
import edu.utexas.barad.agent.swt.proxy.graphics.PointProxy;
import edu.utexas.barad.agent.swt.proxy.graphics.RectangleProxy;
import edu.utexas.barad.agent.swt.proxy.widgets.ButtonProxy;
import edu.utexas.barad.agent.swt.proxy.widgets.DecorationsProxy;
import edu.utexas.barad.agent.swt.proxy.widgets.MenuProxy;
import edu.utexas.barad.agent.swt.proxy.widgets.WidgetProxy;
import edu.utexas.barad.agent.swt.Robot;

/**
 * University of Texas at Austin
 * Barad Project, Jul 25, 2007
 */
public class DecorationsTester extends CanvasTester {
    /**
     * Factory method.
     */
    public static DecorationsTester getDecorationsTester() {
        return (DecorationsTester) getTester(DecorationsProxy.class);
    }

    /**
     * Constructs a new {@link DecorationsTester} associated with the specified
     * {@link TesterRobot}.
     */
    public DecorationsTester(Robot swtRobot) {
        super(swtRobot);
    }

    /**
     * Proxy for {@link DecorationsProxy#getBounds()}. <p/>
     *
     * @param decorations the decorations under test.
     * @return the bounds.
     */
    public RectangleProxy getBounds(final DecorationsProxy decorations) {
        checkWidget(decorations);
        return (RectangleProxy) syncExec(new Result() {
            public Object result() {
                return decorations.getBounds();
            }
        });
    }

    /**
     * Proxy for {@link DecorationsProxy#getClientArea()}. <p/>
     *
     * @param decorations the decorations under test.
     * @return the client area bounds.
     */
    public RectangleProxy getClientArea(final DecorationsProxy decorations) {
        checkWidget(decorations);
        return (RectangleProxy) syncExec(new Result() {
            public Object result() {
                return decorations.getClientArea();
            }
        });
    }

    /**
     * Proxy for {@link DecorationsProxy#getDefaultButton()}. <p/>
     *
     * @param decorations the decorations under test.
     * @return the default button.
     */
    public ButtonProxy getDefaultButton(final DecorationsProxy decorations) {
        checkWidget(decorations);
        return (ButtonProxy) syncExec(new Result() {
            public Object result() {
                return decorations.getDefaultButton();
            }
        });
    }

    /**
     * Proxy for {@link DecorationsProxy#getImage()}. <p/>
     *
     * @param decorations the decorations under test.
     * @return the image.
     */
    public ImageProxy getImage(final DecorationsProxy decorations) {
        checkWidget(decorations);
        return (ImageProxy) syncExec(new Result() {
            public Object result() {
                return decorations.getImage();
            }
        });
    }

    /**
     * Proxy for {@link DecorationsProxy#getImages()}. <p/>
     *
     * @param decorations the decorations under test.
     * @return the images.
     */
    public ImageProxy[] getImages(final DecorationsProxy decorations) {
        checkWidget(decorations);
        return (ImageProxy[]) syncExec(new Result() {
            public Object result() {
                return decorations.getImages();
            }
        });
    }

    /**
     * Proxy for {@link DecorationsProxy#getLocation()}. <p/>
     *
     * @param decorations the decorations under test.
     * @return the loacation.
     */
    public PointProxy getLocation(final DecorationsProxy decorations) {
        checkWidget(decorations);
        return (PointProxy) syncExec(new Result() {
            public Object result() {
                return decorations.getLocation();
            }
        });
    }

    /**
     * Proxy for {@link DecorationsProxy#getMaximized()}. <p/>
     *
     * @param decorations the decorations under test.
     * @return the maximized state.
     */
    public boolean getMaximized(final DecorationsProxy decorations) {
        checkWidget(decorations);
        return syncExec(new BooleanResult() {
            public boolean result() {
                return decorations.getMaximized();
            }
        });
    }

    /**
     * Proxy for {@link DecorationsProxy#getMenuBar()}. <p/>
     *
     * @param decorations the decorations under test.
     * @return the menu bar.
     */
    public MenuProxy getMenuBar(final DecorationsProxy decorations) {
        checkWidget(decorations);
        return (MenuProxy) syncExec(new Result() {
            public Object result() {
                return decorations.getMenuBar();
            }
        });
    }

    /**
     * Proxy for {@link DecorationsProxy#getMinimized()}. <p/>
     *
     * @param decorations the decorations under test.
     * @return the minimized state.
     */
    public boolean getMinimized(final DecorationsProxy decorations) {
        checkWidget(decorations);
        return syncExec(new BooleanResult() {
            public boolean result() {
                return decorations.getMinimized();
            }
        });
    }

    /**
     * Proxy for {@link DecorationsProxy#getSize()}. <p/>
     *
     * @param decorations the decorations under test.
     * @return the size of the decorations.
     */
    public PointProxy getSize(final DecorationsProxy decorations) {
        checkWidget(decorations);
        return (PointProxy) syncExec(new Result() {
            public Object result() {
                return decorations.getSize();
            }
        });
    }

    /**
     * Proxy for {@link DecorationsProxy#getText()}. <p/>
     *
     * @param decorations the decorations under test.
     * @return the text.
     */
    public String getText(final DecorationsProxy decorations) {
        checkWidget(decorations);
        return syncExec(new StringResult() {
            public String result() {
                return decorations.getText();
            }
        });
    }

    /**
     * @see Textable#getText(WidgetProxy)
     */
    public String getText(WidgetProxy widget) {
        return getText((DecorationsProxy) widget);
    }

    public boolean isTextEditable(WidgetProxy widget) {
        return false;
    }
}