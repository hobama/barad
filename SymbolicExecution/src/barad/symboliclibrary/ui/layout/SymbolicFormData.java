package barad.symboliclibrary.ui.layout;

import barad.symboliclibrary.integers.IntegerInterface;

/**
 * Class used to mock the use of SWT FormData 
 * @author svetoslavganov
 *
 */
public class SymbolicFormData  extends SymbolicLayout {
	public static final long serialVersionUID = 1L;
	
	public SymbolicFormData() {
		super("SymbolicformaData");
	}
	
	public IntegerInterface width;
	public IntegerInterface height;
	public SymbolicFormAttachment left;
	public SymbolicFormAttachment top;
}
