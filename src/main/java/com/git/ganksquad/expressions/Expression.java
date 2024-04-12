package com.git.ganksquad.expressions;

import java.util.Arrays;

import com.git.ganksquad.ReimuNameResolver;
import com.git.ganksquad.ReimuRuntime;
import com.git.ganksquad.ReimuTypeResolver;
import com.git.ganksquad.data.BooleanEvaluable;
import com.git.ganksquad.data.Data;
import com.git.ganksquad.data.types.ReimuType;
import com.git.ganksquad.exceptions.compiler.ReimuCompileException;
import com.git.ganksquad.exceptions.runtime.ReimuRuntimeException;

public interface Expression {

	public ReimuType typeCheck(ReimuTypeResolver resolver) throws ReimuCompileException ;

	/**
	 * Evaluate the expression
	 * @param reimuRuntime The runtime
	 * @return The result of the expression
	 */
	public Data eval(ReimuRuntime reimuRuntime) throws ReimuRuntimeException;

	public default String formatToString(Object... param) {
		return ReimuNameResolver.getFormatedName(this, param);
	}
}
