package com.git.ganksquad.expressions;

import java.util.List;

import com.git.ganksquad.ParseChecks;
import com.git.ganksquad.ReimuNameResolver;
import com.git.ganksquad.ReimuRuntime;
import com.git.ganksquad.ReimuTypeResolver;
import com.git.ganksquad.data.Data;
import com.git.ganksquad.data.impl.FunctionData;
import com.git.ganksquad.data.impl.NoneData;
import com.git.ganksquad.data.types.FunctionType;
import com.git.ganksquad.data.types.SpecialType;
import com.git.ganksquad.data.types.ReimuType;
import com.git.ganksquad.exceptions.compiler.ReimuCompileException;
import com.git.ganksquad.exceptions.compiler.TypeException;
import com.git.ganksquad.exceptions.runtime.ReimuRuntimeException;

public class FunctionDefinitionExpression implements Expression {
	
	public ReimuType type;
	public String name;
	public List<String> argNames;
	public List<ReimuType> argTypes;
	public BlockExpression body;
	
	public FunctionDefinitionExpression( ReimuType type, String name, List<String> argNames, List<ReimuType> argTypes, BlockExpression body) {
		
		this.type = type;
		this.name = name;
		this.argNames = argNames;
		this.argTypes = argTypes;
		this.body = body;
	}
	
	public static FunctionDefinitionExpression from( ReimuType type, String name, List<String> argNames, List<ReimuType> argTypes, BlockExpression body) {
		
		ParseChecks.RequiredNotNull(type, name, argNames, argTypes, body);

		return new FunctionDefinitionExpression(type, name, argNames, argTypes, body);
	}

	@Override
	public ReimuType typeCheck(ReimuTypeResolver resolver) throws ReimuCompileException {

		resolver.declareFunction(name, new FunctionType(type, argTypes));
		
		ReimuType t = this.body.typeCheck(resolver);
		
		if(!this.type.isAssignableFrom(t)) {
			
			throw new TypeException("Function %s expects return type %s but it's body returns type %s", name, type, t);
		}

		return SpecialType.VOID;
	}

	@Override
	public Data eval(ReimuRuntime reimuRuntime) throws ReimuRuntimeException {
		
		FunctionData f = new FunctionData(reimuRuntime, name, argNames, argTypes, body);

		reimuRuntime.declareFunction(f);

		return NoneData.instance;
	}

	@Override
	public String toString() {
		return this.formatToString(type, name, argNames, argTypes, body);
	}
}
