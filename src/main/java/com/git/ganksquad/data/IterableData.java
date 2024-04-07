package com.git.ganksquad.data;

/**
 * Represents a generic data which can be iterated over,
 * 
 * IE. using a for loop
 */
public interface IterableData {
	
	/**
	 * Return the next item
	 * @return the next {@link Data} or {@link NoneData} if the end has been reached
	 */
	public Data next();
}
