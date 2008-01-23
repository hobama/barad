package barad.symboliclibrary.path;

import barad.symboliclibrary.common.ConstraintType;

public interface PathConstraintInterface {
	/**
	 * Returns the complementary path constraint
	 * @return The complementary path constraint
	 */
	public PathConstraintInterface inverse();
	
	/**
	 * Returns the type of the constraint
	 * @return The type of the constraint
	 */
	public ConstraintType getType();
}
