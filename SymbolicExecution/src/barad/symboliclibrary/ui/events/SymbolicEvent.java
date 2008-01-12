package barad.symboliclibrary.ui.events;

import java.io.Serializable;

public class SymbolicEvent implements Serializable {
	public static final long serialVersionUID = 1L;
	private static int idCounter = 0;
	private int id;
	
	SymbolicEvent() {
		id = idCounter++;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
}
