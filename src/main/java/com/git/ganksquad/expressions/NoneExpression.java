package com.git.ganksquad.expressions;

import com.git.ganksquad.ReimuRuntime;
import com.git.ganksquad.ReimuTypeResolver;
import com.git.ganksquad.data.Data;
import com.git.ganksquad.data.impl.NoneData;
import com.git.ganksquad.data.types.SpecialType;
import com.git.ganksquad.data.types.ReimuType;
import com.git.ganksquad.exceptions.compiler.ReimuCompileException;
import com.git.ganksquad.exceptions.runtime.ReimuRuntimeException;

public class NoneExpression implements Expression {

	public static NoneExpression get() {
		return new NoneExpression();
	}


	@Override
	public ReimuType typeCheck(ReimuTypeResolver resolver) throws ReimuCompileException {
		return SpecialType.VOID;
	}

	@Override
	public Data eval(ReimuRuntime runtime) throws ReimuRuntimeException {
		return  NoneData.instance;
	}
	
	@Override
	public String toString() {
		return this.formatToString();
	}
}
