package com.git.ganksquad.data;

import com.git.ganksquad.data.types.ReimuType;
import com.git.ganksquad.data.types.SpecialType;
import com.git.ganksquad.exceptions.runtime.CannotCastException;
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

	
	public static Data commonCast(Data d, ReimuType newType) throws ReimuRuntimeException {
		
		if(newType == SpecialType.ANY) {
			return d;
		}

		if(newType.isAssignableFrom(d.getType())) {
			return d;
		}

		throw new CannotCastException(d.getType(), newType);
	}
}