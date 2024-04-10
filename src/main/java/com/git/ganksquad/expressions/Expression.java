package com.git.ganksquad.expressions;

import java.util.Arrays;

import com.git.ganksquad.ReimuRuntime;
import com.git.ganksquad.data.Data;
import com.git.ganksquad.exceptions.ReimuCompileException;
import com.git.ganksquad.exceptions.ReimuRuntimeException;

public interface Expression {

	/**
	 * Evaluate the expression
	 * @param reimuRuntime The runtime
	 * @return The result of the expression
	 */
	public Data eval(ReimuRuntime reimuRuntime) throws ReimuRuntimeException;
	
	public static interface Types {
		
		public static final byte UNKNOWN = 0;
		public static final byte NUMERIC = 1;
		public static final byte STRING = 2;
		public static final byte BOOLEAN = 3;
		public static final byte ITERABLE = 4;
		public static final byte ARRAY = 5;
		
	}

	public byte typeCheck() throws ReimuCompileException ;

	public default String formatToString(Object... param) {
		
		return String.format("%s%s", 
				this.getClass().getSimpleName(),
				Arrays.toString(param));
	}
}
