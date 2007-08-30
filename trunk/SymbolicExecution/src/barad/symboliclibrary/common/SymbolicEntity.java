package barad.symboliclibrary.common;

import java.io.Serializable;

/**
 * Class that is inherited by all classes in the
 * library that represent symbolic integer entities
 * (primitives and expressions) 
 * 
 * @author Svetoslav Ganov
 */
 public abstract class SymbolicEntity implements Serializable
{
	 protected static int nextId = 0;
	 protected int id;
	 protected String name;

	 /**
	  * Constructor that generates a new instance with 
	  * unique id and name (is specified)
	  * 
	  * @param name The name of the entity (optional)
	  */
	 public SymbolicEntity(String name) {
		 this.name = name;
		 id = nextId;
         nextId++;
     }

     public String getName() {
            return name;
     }
     
     public void setName(String name) {
    	 this.name = name;
     }

     /**
      * Gives the unique name of the entity in
      * the instrumented code
      * 
      * @return Unique name of the entity
      */
     public String getId() {
            return name + "_" + String.valueOf(id);
     }
     
 	@Override
	public String toString() {
		return null;
	}
	
	@Override
	public Object clone() {
		return null;
	}
}