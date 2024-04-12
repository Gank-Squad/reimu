package com.git.ganksquad.expressions;

import com.git.ganksquad.ParseChecks;
import com.git.ganksquad.ReimuRuntime;
import com.git.ganksquad.ReimuTypeResolver;
import com.git.ganksquad.data.Data;
import com.git.ganksquad.data.impl.BooleanData;
import com.git.ganksquad.data.types.TestReimuType;
import com.git.ganksquad.exceptions.compiler.ReimuCompileException;

public class BooleanLiteral implements Expression {

	public boolean value = false;
	
	public BooleanLiteral() {}

	public BooleanLiteral(boolean value) {

		this.value = value;
	}
	
	public static BooleanLiteral fromString(String str) {
		
		ParseChecks.RequiredNotNull(str);
		
		return new BooleanLiteral(Boolean.parseBoolean(str));
	}

	@Override
	public ReimuType typeCheck(ReimuTypeResolver resolver) throws ReimuCompileException {
		return ReimuType.BOOLEAN;
	}

	@Override
	public Data eval(ReimuRuntime runtime) {
		
		return new BooleanData(this.value);
	}

	@Override
	public String toString() {
		return this.formatToString(this.value);
	}
}
