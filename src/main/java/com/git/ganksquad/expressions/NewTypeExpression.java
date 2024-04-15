package com.git.ganksquad.expressions;

import java.util.ArrayList;
import java.util.List;

import com.git.ganksquad.ParseChecks;
import com.git.ganksquad.ReimuRuntime;
import com.git.ganksquad.ReimuTypeResolver;
import com.git.ganksquad.data.Data;
import com.git.ganksquad.data.impl.IntegerData;
import com.git.ganksquad.data.types.ArrayType;
import com.git.ganksquad.data.types.PrimitiveType;
import com.git.ganksquad.data.types.ReimuType;
import com.git.ganksquad.exceptions.compiler.ReimuCompileException;
import com.git.ganksquad.exceptions.runtime.ReimuRuntimeException;

public class NewTypeExpression implements Expression {
	
	public ArrayType type;
	public Expression params;
	
	public NewTypeExpression(ArrayType t, Expression param) {
		
		this.type = t;
		this.params = param;
	}
	
	public static NewTypeExpression from(ReimuType t,Expression i) {
		
		ParseChecks.RequiredNotNull(t, i);

		return new NewTypeExpression(new ArrayType(t), i);
	}

	@Override
	public ReimuType typeCheck(ReimuTypeResolver resolver) throws ReimuCompileException {
		return this.type;
	}

	@Override
	public Data eval(ReimuRuntime reimuRuntime) throws ReimuRuntimeException {
		
		Data d = this.params.eval(reimuRuntime);
		
		int i = ((IntegerData)d.castTo(PrimitiveType.INT)).value;
		
		this.type.size = i;

		d = this.type.getDefaultEmptyValue();
		
		this.type.size = -1;

		return d;
	}

}
