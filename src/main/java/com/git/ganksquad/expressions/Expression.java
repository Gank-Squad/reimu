package com.git.ganksquad.expressions;

import java.util.Arrays;

import com.git.ganksquad.ReimuRuntime;
import com.git.ganksquad.data.Data;
import com.git.ganksquad.exceptions.ReimuRuntimeException;

public interface Expression {

	/**
	 * Evaluate the expression
	 * @param reimuRuntime The runtime
	 * @return The result of the expression
	 */
	public Data eval(ReimuRuntime reimuRuntime) throws ReimuRuntimeException;
	
	public default String formatToString(Object... param) {
		
		return String.format("%s%s", 
				this.getClass().getSimpleName(),
				Arrays.toString(param));
	}
}
