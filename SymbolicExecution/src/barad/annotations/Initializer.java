package barad.annotations;

/**
 * Annotation for GUI initialization methods. Defines that
 * a method is used for initialization of certain fields
 * which are GUI widgets.
 * Atributes:
 * 	type -  Defines the type of the initializer
 * 			Type.ALL - initializes all the widgets
 * 			Type.SOME - initializes some wodgets. In such case
 * 			the field attribute should enumerate which
 * 			fields (containing GUI widgets) are initialized in 
 * 			this method (For example: type = Type.ALL)
 *  field - The list of fields initialized in the initialization 
 *          method (For exampe: field = {"field1", "field2"})
 * @author svetoslavganov
 */
public @interface Initializer {
	String[] field() default "";
	Type type() default Type.SOME;
	public enum Type {ALL, SOME;}
}
