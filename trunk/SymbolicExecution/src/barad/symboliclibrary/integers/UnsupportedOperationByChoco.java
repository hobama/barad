package barad.symboliclibrary.integers;

public class UnsupportedOperationByChoco extends Exception {
	public static final long serialVersionUID = 1L;
	
	public UnsupportedOperationByChoco() {
		
	}
	
	public UnsupportedOperationByChoco(String msg) {
		super(msg);
	}
	
	public UnsupportedOperationByChoco(String msg, Throwable cause) {
		super(msg, cause);
	}
	
	public UnsupportedOperationByChoco(Throwable cause) {
		super(cause);
	}
}
