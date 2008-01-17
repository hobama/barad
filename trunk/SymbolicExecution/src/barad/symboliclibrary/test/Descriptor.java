package barad.symboliclibrary.test;

import java.io.Serializable;

/**
 * Describes the value for which widgetProperty of which
 * GUI widget is represented by a symbolic entity 
 * @author svetoslavganov
 *
 */
public class Descriptor implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer hashCode;
	private String parentId;
	private String parentClass;
	private String parentIndex;
	private String widgetId;
	private String widgetClass;
	private String widgetProperty;
	private String widgetIndex;

	@Override
	public String toString() {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(super.toString());
		stringBuilder.append(" ParentId:" + parentId + ",");
		stringBuilder.append(" ParentClass:" + parentClass + ",");
		stringBuilder.append(" ParentIndex:" + parentIndex + ",");
		stringBuilder.append(" WidgetId:" + widgetId + ",");
		stringBuilder.append(" WidgetClass:" + widgetClass + ",");
		stringBuilder.append(" WidgetProperty:" + widgetProperty + ",");
		stringBuilder.append(" WidgetIndex:" + widgetIndex + ",");
		return stringBuilder.toString();
	}
	
	@Override
	public Object clone() {
		Descriptor clone = new Descriptor();
		clone.parentId = parentId;
		clone.parentClass = parentClass;
		clone.parentIndex = parentIndex;
		clone.widgetId = widgetId;
		clone.widgetClass = widgetClass;
		clone.widgetProperty = widgetProperty;
		clone.widgetIndex = widgetIndex;
		return clone;
	}
	
	@Override
	public int hashCode() {
		if (hashCode == null) {
			calculateHashCode();
		}
		return hashCode;
	}
	
	@Override 
	public boolean equals(Object obj) {
		if (obj == null) { 
			return false;
		}
		if (!(obj.getClass().equals(this.getClass()))) {
			return false;
		}
		Descriptor other = (Descriptor) obj;
		return ((parentId != null && parentId.equals(other.parentId)) || (parentId == other.parentId)) &&
		       ((parentClass != null && (parentClass.equals(other.parentClass)) || (parentClass == other.parentClass)) &&
		       ((parentIndex != null && parentIndex.equals(other.parentIndex) ) || (parentIndex == other.parentIndex)) &&
		       ((widgetId != null && widgetId.equals(other.widgetId) ) || (widgetId == other.widgetId)) &&
		       ((widgetClass != null && widgetClass.equals(other.widgetClass) ) || (widgetClass == other.widgetClass)) &&
		       ((widgetProperty != null && widgetProperty.equals(other.widgetProperty) ) || (widgetProperty == other.widgetProperty)) &&
		       ((widgetIndex != null && widgetIndex.equals(other.widgetIndex) ) || (widgetIndex == other.widgetIndex)));
	}
	
	public String getWidgetClass() {
		return widgetClass;
	}
	
	public void setWidgetClass(String widgetClass) {
		this.widgetClass = widgetClass;
	}
	
	public String getParentClass() {
		return parentClass;
	}
	
	public void setParentClass(String parentClass) {
		this.parentClass = parentClass;
	}

	public String getWidgetProperty() {
		return widgetProperty;
	}

	public void setWidgetProperty(String widgetProperty) {
		this.widgetProperty = widgetProperty;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getWidgetId() {
		return widgetId;
	}

	public void setWidgetId(String widgetId) {
		this.widgetId = widgetId;
	}
	
	public String getWidgetIndex() {
		return widgetIndex;
	}

	public void setWidgetIndex(String index) {
		this.widgetIndex = index;
	}
	

	public String getParentIndex() {
		return parentIndex;
	}

	public void setParentIndex(String parentIndex) {
		this.parentIndex = parentIndex;
	}
	
	private void calculateHashCode() {
		int hash = 0;
		if (parentId != null) {
			hash = hash + parentId.hashCode() * 3;
		}
		if (parentClass != null) {
			hash = hash + parentClass.hashCode() * 5;
		}
		if (parentIndex != null) {
			hash = hash + widgetIndex.hashCode() * 17;
		}
		if (widgetId != null) {
			hash = hash + widgetId.hashCode() * 7;
		}
		if (widgetClass != null) {
			hash = hash + widgetClass.hashCode() * 11;
		}
		if (widgetProperty != null) {
			hash = hash + widgetProperty.hashCode() * 13;
		}
		if (widgetIndex != null) {
			hash = hash + widgetIndex.hashCode() * 17;
		}
		hashCode = hash;
	}
}
