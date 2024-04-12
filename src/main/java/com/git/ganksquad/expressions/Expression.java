package com.git.ganksquad.expressions;

import java.util.Arrays;

import com.git.ganksquad.ReimuRuntime;
import com.git.ganksquad.ReimuTypeResolver;
import com.git.ganksquad.data.BooleanEvaluable;
import com.git.ganksquad.data.Data;
import com.git.ganksquad.exceptions.compiler.ReimuCompileException;
import com.git.ganksquad.exceptions.runtime.ReimuRuntimeException;

public interface Expression {


	public static enum ReimuType implements BooleanEvaluable {
		
		NONE{
			@Override
			public boolean evalAsBool() { return false; }
		},
		UNKNOWN{
			@Override
			public boolean evalAsBool() { return true; }
		},
		NUMERIC{
			@Override
			public boolean evalAsBool() { return true; }
		},
		STRING{
			@Override
			public boolean evalAsBool() { return true; }
		},
		BOOLEAN{
			@Override
			public boolean evalAsBool() { return true; }
		},
		ARRAY{
			@Override
			public boolean evalAsBool() { return false; }
		},
		FUNCTION{
			@Override
			public boolean evalAsBool() { return false; }
		},
		ITERABLE{

			@Override
			public boolean evalAsBool() { return false; }
		};
	}


	public ReimuType typeCheck(ReimuTypeResolver resolver) throws ReimuCompileException ;

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
