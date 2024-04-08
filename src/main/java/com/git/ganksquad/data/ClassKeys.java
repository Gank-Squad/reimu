package com.git.ganksquad.data;

/**
 * Used for unique numbering of class types.
 * 
 * This allows the {@link Data} type to be switch cased easily
 */
public interface ClassKeys {

	public static final int NONE_DATA = 0;
	public static final int INTEGER_DATA = 1;
	public static final int BOOLEAN_DATA = 2;
	public static final int STRING_DATA = 3;
	public static final int FUNCTION_DATA = 4;
	public static final int RANGE_DATA = 5;
}
