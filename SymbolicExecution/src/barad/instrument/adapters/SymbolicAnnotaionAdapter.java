package barad.instrument.adapters;

import org.objectweb.asm.AnnotationVisitor;

@SuppressWarnings("all")
public class SymbolicAnnotaionAdapter implements AnnotationVisitor{

	public void visit(String name, Object value) {
		int x = 0;	
	}

	public AnnotationVisitor visitAnnotation(String name, String desc) {
		int x = 0;
		return null;
	}

	public AnnotationVisitor visitArray(String name) {
		return new SymbolicAnnotaionAdapter();
	}
	
	
	public void visitEnd() {
		int x = 0;
		
	}
	
	public void visitEnum(String name, String desc, String value) {
		int x = 0;
		
	}

}
