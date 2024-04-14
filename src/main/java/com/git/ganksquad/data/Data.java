package com.git.ganksquad.data;

import com.git.ganksquad.data.types.ReimuType;
import com.git.ganksquad.exceptions.runtime.ReimuRuntimeException;

/**
 * The base data type which all data must be
 */
public interface Data {
	
	/**
	 * Gets the integer value which represents the unique data type
	 * @return A unique integer
	 */
	public int getClassKey();
	
	public ReimuType getType();

	public Data castTo(ReimuType newType) throws ReimuRuntimeException;

}