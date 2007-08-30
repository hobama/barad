package barad.examples;

import java.io.Serializable;
import java.util.HashSet;

public class Dummy implements Serializable {
	private HashSet<String> set;
	public Dummy(HashSet<String> set) {
		this.set = set;
	}
}
