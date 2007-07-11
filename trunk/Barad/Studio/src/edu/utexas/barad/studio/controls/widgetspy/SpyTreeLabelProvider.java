package edu.utexas.barad.studio.controls.widgetspy;

import edu.utexas.barad.common.swt.WidgetCategory;
import edu.utexas.barad.common.swt.WidgetInfo;
import edu.utexas.barad.studio.Images;
import org.apache.log4j.Logger;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;

/**
 * University of Texas at Austin
 * Barad Project, Jul 9, 2007
 */
public class SpyTreeLabelProvider extends LabelProvider {
    private static final Logger logger = Logger.getLogger(SpyTreeLabelProvider.class);
    private static final String SWT_PACKAGE_PREFIX = "org.eclipse.swt.widgets.";

    @Override
    public Image getImage(Object element) {
        WidgetInfo widgetInfo = (WidgetInfo) element;
        WidgetCategory widgetCategory = widgetInfo.getCategory();
        switch (widgetCategory) {
            case BUTTON:
                return Images.WIDGETS_BUTTON.createImage();
            case DISPLAY:
                return Images.WIDGETS_DISPLAY.createImage();
            case SHELL:
                return Images.WIDGETS_SHELL.createImage();
            case LABEL:
                return Images.WIDGETS_LABEL.createImage();
            case TEXT:
                return Images.WIDGETS_TEXT.createImage();
            case MENU:
                return Images.WIDGETS_MENU.createImage();
            case TAB:
                return Images.WIDGETS_TAB.createImage();
            case TABLE:
                return Images.WIDGETS_TABLE.createImage();
            case TREE:
                return Images.WIDGETS_TREE.createImage();
            case WINDOW:
                return Images.WIDGETS_WINDOW.createImage();
            case OTHER:
                return Images.WIDGETS_OTHER.createImage();
            default: {
                logger.warn("Unknown widget category: " + widgetCategory);
                return Images.WIDGETS_OTHER.createImage();
            }
        }
    }

    @Override
    public String getText(Object element) {
        WidgetInfo widgetInfo = (WidgetInfo) element;
        String className = widgetInfo.getClassName();
        if (className == null) {
            className = "Unknown";
        }
        if (className.startsWith(SWT_PACKAGE_PREFIX)) {
            className = className.substring(SWT_PACKAGE_PREFIX.length());
        }
        String displayText = className;
        String text = widgetInfo.getText();
        if (text != null && !"".equals(text)) {
            text = text.replace('\t', ' ');
            text = text.replace('\n', ' ');
            displayText += " - " + text;
        }
        return displayText;
    }
}