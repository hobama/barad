package barad.instrument.instrumenter;

/**
 * Auxiliary class that describes a method which should
 * be executed symbolivally
 * @author svetoslavganov
 *
 */
public class Method {
	private Integer hashCode;
	private String clazz;
	private String desc;
	private String name;

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
		Method other = (Method) obj;
		return ((clazz != null && clazz.equals(other.clazz)) || (clazz == other.clazz)) &&
			   ((desc != null && (desc.equals(other.desc)) || (desc == other.desc)) &&
			   ((name != null && name.equals(other.name) )|| (name == other.name)));
	}

	@Override 
	public String toString() {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("Removed method: ");
		stringBuilder.append(clazz);
		stringBuilder.append('.');
		stringBuilder.append(name);
		stringBuilder.append(desc);
		return stringBuilder.toString();
	}
	
	public String getClazz() {
		return clazz;
	}

	public void setClazz(String clazz) {
		this.clazz = clazz;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	private void calculateHashCode() {
		int hash = 0;
		if (clazz != null) {
			hash = hash + clazz.hashCode() * 3;
		}
		if (desc != null) {
			hash = hash + desc.hashCode() * 5;
		}
		if (name != null) {
			hash = hash + name.hashCode() * 7;
		}
		hashCode = hash;
	}
}