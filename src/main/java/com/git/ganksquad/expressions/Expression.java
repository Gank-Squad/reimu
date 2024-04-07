package com.git.ganksquad.expressions;

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
}
