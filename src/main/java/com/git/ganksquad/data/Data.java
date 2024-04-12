package com.git.ganksquad.data;

/**
 * The base data type which all data must be
 */
public interface Data {
	
	/**
	 * Gets the integer value which represents the unique data type
	 * @return A unique integer
	 */
	public int getClassKey();

}