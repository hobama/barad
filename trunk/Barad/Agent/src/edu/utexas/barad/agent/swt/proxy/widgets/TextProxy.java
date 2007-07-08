package edu.utexas.barad.agent.swt.proxy.widgets;

import edu.utexas.barad.agent.swt.proxy.graphics.PointProxy;

/**
 * University of Texas at Austin
 * Barad Project, Jul 6, 2007
 *
 *
 */
public interface TextProxy extends ScrollableProxy {
    public int getBorderWidth();

    public int getCaretLineNumber();

    public PointProxy getCaretLocation();

    public int getCaretPosition();

    public int getCharCount();

    public boolean getDoubleClickEnabled();

    public char getEchoChar();

    public boolean getEditable();

    public int getLineCount();

    public String getLineDelimiter();

    public int getLineHeight();

    public String getMessage();

    public int getOrientation();

    public PointProxy getSelection();

    public int getSelectionCount();

    public String getSelectionText();

    public int getTabs();

    public String getText();

    public String getText(int start, int end);

    public int getTextLimit();

    public int getTopIndex();

    public int getTopPixel();
}
