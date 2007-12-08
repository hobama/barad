package barad.profiler;

/**
 * Class that gives a string representation of the method/field accessor
 * in Java bytecode
 * 
 * @author svetoslavganov
 *
 */
public final class OpcodeToAccessorMap {

	public static String getAccessor(int access) {
		StringBuffer accessor = new StringBuffer();
		if ((access & 0x0001) == 0x0001) {accessor.append("public ");}
		if ((access & 0x0002) == 0x0002) {accessor.append("private ");}
		if ((access & 0x0004) == 0x0004) {accessor.append("protected ");}
		if ((access & 0x0008) == 0x0008) {accessor.append("static ");}
		if ((access & 0x0010) == 0x0010) {accessor.append("final ");}
		if ((access & 0x0020) == 0x0020) {accessor.append("synchronized ");}
		if ((access & 0x0100) == 0x0100) {accessor.append("native ");}
		if ((access & 0x0200) == 0x0200) {accessor.append("interface ");}
		if ((access & 0x0400) == 0x0400) {accessor.append("abstract ");}
		if ((access & 0x0800) == 0x0800) {accessor.append("strictfp ");}
		return accessor.toString();
	}
}

/* MAPPING:
ACC_PUBLIC 	0x0001 	public
ACC_PRIVATE 	0x0002 	private
ACC_PROTECTED 	0x0004 	protected
ACC_STATIC 	0x0008 	static.
ACC_FINAL 	0x0010 	final
ACC_SYNCHRONIZED 	0x0020 	synchronized
ACC_NATIVE 	0x0100 	native
ACC_INTERFACE 	0x0200 	interface
ACC_ABSTRACT 	0x0400 	abstract
ACC_STRICT 	0x0800 	strictfp
*/