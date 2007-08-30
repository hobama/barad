package barad.symboliclibrary.integer;

import java.io.Serializable;

import barad.symboliclibrary.common.SymbolicEntity;

public abstract class SymbolicIntegerEntity extends SymbolicEntity implements IntegerInterface, Serializable {
	public SymbolicIntegerEntity(String name) {
		super(name);
	}
}
