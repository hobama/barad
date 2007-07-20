package edu.utexas.barad.studio.controls.widgetspy;

import edu.utexas.barad.common.swt.WidgetCategory;
import edu.utexas.barad.common.swt.WidgetInfo;
import edu.utexas.barad.studio.exceptions.StudioRuntimeException;
import org.eclipse.jface.viewers.IElementComparer;

/**
 * University of Texas at Austin
 * Barad Project, Jul 11, 2007
 */
public class SpyTreeElementComparer implements IElementComparer {
    public boolean equals(Object object1, Object object2) {
        if (object1 == null && object2 == null) {
            return true;
        }

        if (object1 instanceof WidgetInfo[]) {
            if (!(object2 instanceof WidgetInfo[])) {
                return false;
            }
        }

        if (object1 instanceof WidgetInfo) {
            if (!(object2 instanceof WidgetInfo)) {
                return false;
            }
        }

        if (object2 instanceof WidgetInfo[]) {
            if (!(object1 instanceof WidgetInfo[])) {
                return false;
            }
        }

        if (object2 instanceof WidgetInfo) {
            if (!(object1 instanceof WidgetInfo)) {
                return false;
            }
        }

        if (object1 instanceof WidgetInfo[]) {
            WidgetInfo[] array1 = (WidgetInfo[]) object1;
            WidgetInfo[] array2 = (WidgetInfo[]) object2;
            if (array1.length != array2.length) {
                return false;
            }
            for (int i = 0; i < array1.length; ++i) {
                if (!equalsWidget(array1[i], array2[i])) {
                    return false;
                }
            }
            return true;
        } else if (object1 instanceof WidgetInfo) {
            WidgetInfo widgetInfo1 = (WidgetInfo) object1;
            WidgetInfo widgetInfo2 = (WidgetInfo) object2;
            return equalsWidget(widgetInfo1, widgetInfo2);
        }

        return false;
    }

    private static boolean equalsWidget(WidgetInfo widgetInfo1, WidgetInfo widgetInfo2) {
        if (widgetInfo1 == null && widgetInfo2 == null) {
            return true;
        }
        if (widgetInfo1 == null && widgetInfo2 != null) {
            return false;
        }
        if (widgetInfo1 != null && widgetInfo2 == null) {
            return false;
        }

        if (!equalsAux(widgetInfo1.getClassName(), widgetInfo2.getClassName())) {
            return false;
        }
        if (!equalsAux(widgetInfo1.getText(), widgetInfo2.getText())) {
            return false;
        }
        if (!equalsAux(widgetInfo1.getCategory(), widgetInfo2.getCategory())) {
            return false;
        }

        return equalsWidget(widgetInfo1.getParent(), widgetInfo2.getParent());
    }

    public int hashCode(Object element) {
        if (element == null) {
            return 0;
        }

        WidgetInfo widgetInfo = (WidgetInfo) element;
        int hashCode = 0;
        String className = widgetInfo.getClassName();
        hashCode += className != null ? className.hashCode() : 0;
        String text = widgetInfo.getText();
        hashCode += text != null ? text.hashCode() : 0;
        WidgetCategory category = widgetInfo.getCategory();
        hashCode += category != null ? category.hashCode() : 0;
        WidgetInfo parentInfo = widgetInfo.getParent();
        hashCode += parentInfo != null ? hashCode(parentInfo) : 0;
        return hashCode;
    }

    private static <T> boolean equalsAux(T object1, T object2) {
        if (object1 == null && object2 == null) {
            return true;
        }
        if (object1 == null && object2 != null) {
            return false;
        }
        if (object1 != null && object2 == null) {
            return false;
        }
        return object1.equals(object2);
    }
}