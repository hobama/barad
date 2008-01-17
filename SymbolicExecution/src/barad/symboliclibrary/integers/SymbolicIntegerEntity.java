package barad.symboliclibrary.integers;

import java.io.Serializable;

import barad.symboliclibrary.common.SymbolicEntity;
import barad.symboliclibrary.path.Path;
import barad.symboliclibrary.strings.StringInterface;
import barad.symboliclibrary.strings.SymbolicString;
import barad.symboliclibrary.test.Descriptor;

/**
 * Class that should be extended by all symbolic integer entities
 * @author svetoslavganov
 */
public abstract class SymbolicIntegerEntity extends SymbolicEntity implements IntegerInterface, Serializable {
	protected Descriptor descriptor;
	
	public SymbolicIntegerEntity(String name) {
		super(name);
	}
	
	public Descriptor getDescriptor() {
		return descriptor;
	}

	public void setDescriptor(Descriptor descriptor) {
		this.descriptor = descriptor;
	}
	
	public static IntegerInterface parseInt(StringInterface symbolicString) {
		SymbolicString string = (SymbolicString)symbolicString;
		IVAR ivar = new IVAR();
		ivar.setDescriptor(string.getDescriptor());
		Path.addInputVariable(ivar);
		return ivar;
	}
}