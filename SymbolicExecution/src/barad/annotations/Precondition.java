package barad.annotations;

/**
 * Annotation for GUI event handlers. Defines a precondition
 * that must be satisfied before the method execution. Its 
 * targets are primitive values returned by the getters
 * of a GUI widget.   
 * @author svetoslavganov
 */
public @interface Precondition {
	/**
	 * The field with reference to the widget
	 * @return The field name 
	 */
	String field();
	
	/**
	 * The target getter value
	 * @return The name of the getter method
	 */
	String method();
	
	/**
	 * The primitive type returned by the getter
	 * @return  The return type of the getter
	 */
	String returntype();
	
	/**
	 * The primitive value returned by the getter
	 * @return The expected value returned by the getter
	 */
	String value();
}
