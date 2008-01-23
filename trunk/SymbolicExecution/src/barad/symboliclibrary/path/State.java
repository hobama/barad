package barad.symboliclibrary.path;

import static barad.util.Properties.DEBUG;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;

import org.apache.log4j.Logger;
import org.objectweb.asm.Label;

import barad.symboliclibrary.common.ConstraintType;

public class State {
	private static int ids = 0;
	
	private int id;
	private Logger log = Logger.getLogger(this.getClass());
	private ConstraintType type;
	private LinkedHashMap<Integer, Object> variableValues;
	private LinkedHashMap<String, Object> fieldValues;
	private LinkedList<PathConstraintInterface> constraints;
	private Label backtrackLabel;
	private boolean terminal;

	public Label getBacktrackLabel() {
		return backtrackLabel;
	}

	public void setBacktrackLabel(Label backtrackLabel) {
		this.backtrackLabel = backtrackLabel;
	}

	public State() {
		this.variableValues = new LinkedHashMap<Integer, Object>();
		this.fieldValues = new LinkedHashMap<String, Object>();
		this.constraints = new LinkedList<PathConstraintInterface>();
		id = ids++;
	}

	public LinkedHashMap<Integer, Object> getVariableValues() {
		return variableValues;
	}
	
	public LinkedHashMap<String, Object> getFieldValues() {
		return fieldValues;
	}

	public int getId() {
		return id;
	}

	public void setVariableValues(LinkedHashMap<Integer, Object> variableValues) {
		this.variableValues = variableValues;
	}
	
	public void setFieldValues(LinkedHashMap<String, Object> fieldValues) {
		this.fieldValues = fieldValues;
	}
	
	public void printStoredValues() {
		if (DEBUG) {
			log.debug("========================================================");
			log.debug("SUMMARY FOR STATE: " + id);
			log.debug("========================================================");
			log.debug("STORED VRIABLES");
			for (Map.Entry<Integer, Object> e: variableValues.entrySet()) {
				log.debug("Vriable id: " + e.getKey() + " Value: " + e.getValue().toString());
			}
			log.debug("STORED FIELDS");
			for (Map.Entry<String, Object> e: fieldValues.entrySet()) {
				log.debug("Vriable id: " + e.getKey() + " Value: " + e.getValue().toString());
			}
			log.debug("========================================================");
		}
	}

	public ConstraintType getType() {
		return type;
	}

	public void setType(ConstraintType type) {
		this.type = type;
	}

	public LinkedList<PathConstraintInterface> getConstraints() {
		return constraints;
	}

	public void setConstraints(LinkedList<PathConstraintInterface> consttraints) {
		this.constraints = consttraints;
	}
	
	public boolean isTerminal() {
		return terminal;
	}

	public void setTerminal(boolean terminal) {
		this.terminal = terminal;
	}
}


