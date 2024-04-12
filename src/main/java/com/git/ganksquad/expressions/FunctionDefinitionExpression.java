package com.git.ganksquad.expressions;

import java.util.List;

import com.git.ganksquad.ParseChecks;
import com.git.ganksquad.ReimuRuntime;
import com.git.ganksquad.ReimuTypeResolver;
import com.git.ganksquad.data.Data;
import com.git.ganksquad.data.impl.FunctionData;
import com.git.ganksquad.data.impl.NoneData;
import com.git.ganksquad.exceptions.compiler.ReimuCompileException;
import com.git.ganksquad.exceptions.runtime.ReimuRuntimeException;

public class FunctionDefinitionExpression implements Expression {
	
	public ReimuType type;
	public FunctionData func;
	
	public FunctionDefinitionExpression(FunctionData data) {
		
		this.func = data;
	}
	
	public static FunctionDefinitionExpression from(ReimuType t, String name, List<String> args, BlockExpression body) {
		
		ParseChecks.RequiredNotNull(t, name, args, body);

		return new FunctionDefinitionExpression(new FunctionData(name, args, body));
	}

	@Override
	public ReimuType typeCheck(ReimuTypeResolver resolver) throws ReimuCompileException {

		resolver.declare(func.getName(), ReimuType.FUNCTION);

		return ReimuType.NONE;
	}

	@Override
	public Data eval(ReimuRuntime reimuRuntime) throws ReimuRuntimeException {
		
		this.func.scope = reimuRuntime;

		reimuRuntime.declareFunction(this.func);

		return NoneData.instance;
	}

	@Override
	public String toString() {
		return this.formatToString(func);
	}
}
