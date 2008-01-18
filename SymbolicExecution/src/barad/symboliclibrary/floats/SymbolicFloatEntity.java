package barad.symboliclibrary.floats;

import java.io.Serializable;

import barad.symboliclibrary.common.SymbolicEntity;
import barad.symboliclibrary.path.Path;
import barad.symboliclibrary.strings.StringInterface;
import barad.symboliclibrary.strings.SymbolicString;
import barad.symboliclibrary.test.Descriptor;

/**
 * Class that should be subclassed by all symbolic float entities 
 * @author svetoslavganov
 */
public abstract class SymbolicFloatEntity extends SymbolicEntity implements FloatInterface, Serializable {
	protected Descriptor descriptor;
	
	public SymbolicFloatEntity(String name) {
		super(name);
	}

	public Descriptor getDescriptor() {
		return descriptor;
	}

	public void setDescriptor(Descriptor descriptor) {
		this.descriptor = descriptor;
	}
	
	public static FloatInterface parseFloat(StringInterface symbolicString) {
		SymbolicString string = (SymbolicString)symbolicString;
		FVAR fvar = new FVAR();
		fvar.setDescriptor(string.getDescriptor());
		Path.addInputVariable(fvar);
		return fvar;
	}
}
