package barad.symboliclibrary.strings;

import java.io.Serializable;

import barad.symboliclibrary.common.SymbolicEntity;
import barad.symboliclibrary.strings.StringInterface;
import barad.symboliclibrary.test.Descriptor;

public abstract class SymbolicStringEntity extends SymbolicEntity implements StringInterface, Serializable {
	protected Descriptor descriptor;
	
	public SymbolicStringEntity(String name) {
		super(name);
	}

	public Descriptor getDescriptor() {
		return descriptor;
	}

	public void setDescriptor(Descriptor descriptor) {
		this.descriptor = descriptor;
	}
}
