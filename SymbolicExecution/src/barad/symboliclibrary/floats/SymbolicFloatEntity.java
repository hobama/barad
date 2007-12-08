package barad.symboliclibrary.floats;

import java.io.Serializable;

import barad.symboliclibrary.common.SymbolicEntity;

/**
 * Class that should be subclassed by all symbolic float entities 
 * @author svetoslavganov
 */
public abstract class SymbolicFloatEntity extends SymbolicEntity implements FloatInterface, Serializable {
	public SymbolicFloatEntity(String name) {
		super(name);
	}
}
