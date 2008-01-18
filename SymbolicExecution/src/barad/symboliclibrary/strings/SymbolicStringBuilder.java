package barad.symboliclibrary.strings;

import barad.symboliclibrary.common.SymbolicEntity;
import barad.util.Util;

public class SymbolicStringBuilder extends SymbolicEntity {
	public static final long serialVersionUID = 1L;
	private static int SYMBOLIC_STRING_BUFFER_MAX_LENGTH = Integer.parseInt(Util.getProperties().getProperty("symbolic.string.buffer.max.length", "100"));
	public StringInterface value;
	
	public SymbolicStringBuilder() {
		super("SymbolicStringBuilder");
	}
	
	public SymbolicStringBuilder(StringInterface stringInterface) {
		super("SymbolicStringBuilder");
		this.value = stringInterface;
	}
	public SymbolicStringBuilder append(StringInterface stringInterface) {
		SymbolicString rhs = (SymbolicString)value;
		SymbolicString lhs = (SymbolicString)stringInterface;
		if (value != null && lhs.getLength() + rhs.getLength() < SYMBOLIC_STRING_BUFFER_MAX_LENGTH) {
			value = value.concat(stringInterface);
		} else {
			value = stringInterface;
		}
		return this;
	}
	
	public StringInterface toSymbolicString() {
		return value;
	}
	
	@Override
	public Object clone() {
		StringInterface clonedValue = (StringInterface)value.clone();
		SymbolicStringBuilder clone = new SymbolicStringBuilder(clonedValue);
		return clone;
	}
	
}
