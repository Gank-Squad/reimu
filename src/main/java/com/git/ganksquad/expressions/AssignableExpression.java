package com.git.ganksquad.expressions;

import com.git.ganksquad.ReimuRuntime;
import com.git.ganksquad.data.Data;
import com.git.ganksquad.exceptions.runtime.ReimuRuntimeException;

public interface AssignableExpression {

	public Data evalAssign(ReimuRuntime runtime, Data assign) throws ReimuRuntimeException;
}
