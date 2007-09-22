package barad.symboliclibrary.integers;

import java.io.Serializable;

import barad.symboliclibrary.common.SymbolicEntity;

/**
 * Class that should be extended by all symbolic integer entities
 * @author svetoslavganov
 */
public abstract class SymbolicIntegerEntity extends SymbolicEntity implements IntegerInterface, Serializable {
	public SymbolicIntegerEntity(String name) {
		super(name);
	}
}
