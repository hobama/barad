package edu.utexas.barad.agent.swt;

import edu.utexas.barad.common.Visitor;
import edu.utexas.barad.common.swt.WidgetInfo;

import java.util.*;

/**
 * University of Texas at Austin
 * Barad Project, Aug 4, 2007
 */
public class CompareHierarchies {
    public static final List<String> propertyNamesToCompare = Arrays.asList(
            "isEnabled",
            "getText",
            "isVisible");

    public static HierarchyDiff compare(final WidgetHierarchy before, final WidgetHierarchy after) {
        if (before == null) {
            throw new NullPointerException("before");
        }
        if (after == null) {
            throw new NullPointerException("after");
        }

        final HierarchyDiff diff = new HierarchyDiff();
        after.accept(new Visitor() {
            public void visit(Object object) {
                WidgetInfo afterWidgetInfo = (WidgetInfo) object;
                Map<String, String> beforeValues = before.getWidgetPropertyValues(afterWidgetInfo.getWidgetID());
                if (beforeValues == null) {
                    diff.addAddedWidget(afterWidgetInfo);
                } else {
                    Map<String, String> afterValues = after.getWidgetPropertyValues(afterWidgetInfo.getWidgetID());
                    for (String propertyNameToCompare : propertyNamesToCompare) {
                        if (beforeValues.containsKey(propertyNameToCompare)) {
                            String beforeValue = beforeValues.get(propertyNameToCompare);
                            String afterValue = afterValues.get(propertyNameToCompare);
                            if (!beforeValue.equals(afterValue)) {
                                PropertyDiff propertyDiff = new PropertyDiff();
                                propertyDiff.setPropertyName(propertyNameToCompare);
                                propertyDiff.setBeforeValue(beforeValue);
                                propertyDiff.setAfterValue(afterValue);
                                diff.addChangedWidget(afterWidgetInfo);
                                diff.addPropertyChange(afterWidgetInfo, propertyDiff);
                            }
                        }
                    }
                }
            }
        });

        before.accept(new Visitor() {
            public void visit(Object object) {
                WidgetInfo beforeWidgetInfo = (WidgetInfo) object;
                Map<String, String> afterValues = after.getWidgetPropertyValues(beforeWidgetInfo.getWidgetID());
                if (afterValues == null) {
                    diff.addRemovedWidget(beforeWidgetInfo);
                }
            }
        });

        return diff;
    }

    public static class HierarchyDiff {
        private List<WidgetInfo> added = new ArrayList<WidgetInfo>();
        private List<WidgetInfo> removed = new ArrayList<WidgetInfo>();
        private List<WidgetInfo> changed = new ArrayList<WidgetInfo>();
        private Map<WidgetInfo, List<PropertyDiff>> changes = new HashMap<WidgetInfo, List<PropertyDiff>>();

        public List<WidgetInfo> getAddedWidgets() {
            return Collections.unmodifiableList(added);
        }

        public void addAddedWidget(WidgetInfo widgetInfo) {
            added.add(widgetInfo);
        }

        public List<WidgetInfo> getRemovedWidgets() {
            return Collections.unmodifiableList(removed);
        }

        public void addRemovedWidget(WidgetInfo widgetInfo) {
            removed.add(widgetInfo);
        }

        public List<WidgetInfo> getChangedWidgets() {
            return Collections.unmodifiableList(changed);
        }

        public void addChangedWidget(WidgetInfo widgetInfo) {
            changed.add(widgetInfo);
        }

        public List<PropertyDiff> getPropertyChanges(WidgetInfo widgetInfo) {
            List<PropertyDiff> diffList = changes.get(widgetInfo);
            if (diffList == null) {
                diffList = new ArrayList<PropertyDiff>();
            }
            return Collections.unmodifiableList(diffList);
        }

        public void addPropertyChange(WidgetInfo widgetInfo, PropertyDiff diff) {
            List<PropertyDiff> diffList = changes.get(widgetInfo);
            if (diffList == null) {
                diffList = new ArrayList<PropertyDiff>();
            }
            diffList.add(diff);
            changes.put(widgetInfo, diffList);
        }

        public boolean isDifferent() {
            return added.size() > 0 || removed.size() > 0 || changed.size() > 0;
        }

        public String toString() {
            return "HierarchyDiff{" +
                    "added=" + added +
                    ", removed=" + removed +
                    ", changed=" + changed +
                    ", changes=" + changes +
                    '}';
        }
    }

    public static class PropertyDiff {
        private String propertyName;
        private Object beforeValue;
        private Object afterValue;

        public String getPropertyName() {
            return propertyName;
        }

        public void setPropertyName(String propertyName) {
            this.propertyName = propertyName;
        }

        public Object getBeforeValue() {
            return beforeValue;
        }

        public void setBeforeValue(Object beforeValue) {
            this.beforeValue = beforeValue;
        }

        public Object getAfterValue() {
            return afterValue;
        }

        public void setAfterValue(Object afterValue) {
            this.afterValue = afterValue;
        }

        public String toString() {
            return "PropertyDiff{" +
                    "propertyName='" + propertyName + '\'' +
                    ", beforeValue=" + beforeValue +
                    ", afterValue=" + afterValue +
                    '}';
        }
    }
}