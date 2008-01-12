package barad.symboliclibrary.common;

public interface CommonInterface {
	/**
	 * @return The id of the entity
	 */
	public String getId();
	
	/**
	 * @return The name of the entity
	 */
	public String getName();
	
	/**
	 * @return Clone of the object
	 */
	public Object clone();
}
