package com.git.ganksquad.expressions;

import com.git.ganksquad.ParseChecks;
import com.git.ganksquad.ReimuRuntime;
import com.git.ganksquad.ReimuTypeResolver;
import com.git.ganksquad.data.Data;
import com.git.ganksquad.data.impl.LongData;
import com.git.ganksquad.data.types.PrimitiveType;
import com.git.ganksquad.data.types.ReimuType;
import com.git.ganksquad.exceptions.compiler.ReimuCompileException;

public class IntegerLiteral implements Expression {
	
	public int value = 0;
	
	public IntegerLiteral() {}

	public IntegerLiteral(int value) {

		this.value = value;
	}
	
	public static IntegerLiteral fromString(String str) {
		
		ParseChecks.RequiredNotNull(str);

		return new IntegerLiteral(Integer.parseInt(str));
	}

	public static IntegerLiteral fromHexString(String str) {
		
		ParseChecks.RequiredNotNull(str);

		return new IntegerLiteral(Integer.parseInt(str.substring(2), 16));
	}

	public static IntegerLiteral fromBinString(String str) {
		
		ParseChecks.RequiredNotNull(str);

		return new IntegerLiteral(Integer.parseInt(str.substring(2), 2));
	}

	public static IntegerLiteral zero() {
		
		return new IntegerLiteral(0);
	}
	
	@Override
	public ReimuType typeCheck(ReimuTypeResolver resolver) throws ReimuCompileException {
		this.trace();
		return PrimitiveType.INT;
	}

	@Override
	public Data eval(ReimuRuntime runtime) {

		return new LongData(this.value);
	}
	
	@Override
	public String toString() {
		return this.formatToString(this.value);
	}
}
