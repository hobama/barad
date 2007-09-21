package barad.symboliclibrary.string;

import java.io.Serializable;

import barad.symboliclibrary.common.SymbolicEntity;
import barad.symboliclibrary.string.StringInterface;

public abstract class SymbolicStringEntity extends SymbolicEntity implements StringInterface, Serializable {
	public SymbolicStringEntity(String name) {
		super(name);
	}
}
