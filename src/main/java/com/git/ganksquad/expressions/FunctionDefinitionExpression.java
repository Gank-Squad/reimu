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
import com.git.ganksquad.data.types.ReimuType;
import com.git.ganksquad.data.types.SpecialType;
import com.git.ganksquad.exceptions.compiler.ReimuCompileException;
import com.git.ganksquad.exceptions.compiler.TypeException;
import com.git.ganksquad.exceptions.runtime.ReimuRuntimeException;

public class FunctionDefinitionExpression implements Expression {
	
	public String name;
	public FunctionType funcType;
	public List<String> argNames;
	public BlockExpression body;
	
	public FunctionDefinitionExpression( ReimuType type, String name, List<String> argNames, List<ReimuType> argTypes, BlockExpression body) {
		
		this.name = name;
		this.argNames = argNames;
		this.body = body;
		this.funcType = new FunctionType(type, argTypes);
	}
	
	public static FunctionDefinitionExpression from( ReimuType type, String name, List<String> argNames, List<ReimuType> argTypes, BlockExpression body) {
		
		ParseChecks.RequiredNotNull(type, name, argNames, argTypes, body);

		return new FunctionDefinitionExpression(type, name, argNames, argTypes, body);
	}

	@Override
	public ReimuType typeCheck(ReimuTypeResolver resolver) throws ReimuCompileException {

		this.trace();
		
		this.funcType.returnType.resolve(resolver);
		
		for(ReimuType t : this.funcType.argumentTypes)
			t.resolve(resolver);

		resolver.declareFunction(name, this.funcType);
		
		ReimuTypeResolver scope = resolver.subScope(this.argNames.iterator(), this.funcType.argumentTypes.iterator());

		this.body.explicitReturn = true;

		ReimuType t = this.body.typeCheckPartial(scope);
		
		if(this.funcType.returnType != SpecialType.VOID && !this.funcType.returnType.isAssignableFrom(t)) {
			
			throw new TypeException("Function %s expects return type %s but it's body returns type %s", name, this.funcType.returnType, t);
		}

		return SpecialType.VOID;
	}

	@Override
	public Data eval(ReimuRuntime reimuRuntime) throws ReimuRuntimeException {
		
		FunctionData f = new FunctionData(reimuRuntime, name, this.funcType, argNames, body);

		reimuRuntime.declareFunction(f);

		return NoneData.instance;
	}

	@Override
	public String toString() {
		return this.formatToString(this.funcType.returnType, name, argNames, funcType.argumentTypes, body);
	}
}
