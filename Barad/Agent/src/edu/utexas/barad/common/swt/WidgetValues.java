package edu.utexas.barad.common.swt;

import java.io.Serializable;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * University of Texas at Austin
 * Barad Project, Jul 10, 2007
 */
public class WidgetValues implements Serializable {
    private static final long serialVersionUID = 500874787336012759L;

    private Map<String, String> propertyValues = new HashMap<String, String>();
    private Map<String, String> fieldValues = new HashMap<String, String>();

    public Map<String, String> getPropertyValues() {
        return Collections.unmodifiableMap(propertyValues);
    }

    public void setPropertyValues(Map<String, String> propertyValues) {
        this.propertyValues = propertyValues;
    }

    public Map<String, String> getFieldValues() {
        return Collections.unmodifiableMap(fieldValues);
    }

    public void setFieldValues(Map<String, String> fieldValues) {
        this.fieldValues = fieldValues;
    }

    @Override
    public String toString() {
        return "WidgetValues{" +
                "propertyValues=" + propertyValues +
                ", fieldValues=" + fieldValues +
                '}';
    }
}