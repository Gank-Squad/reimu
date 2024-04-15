package com.git.ganksquad.expressions;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.tinylog.Logger;

import com.git.ganksquad.ParseChecks;
import com.git.ganksquad.ReimuRuntime;
import com.git.ganksquad.ReimuTypeResolver;
import com.git.ganksquad.data.Data;
import com.git.ganksquad.data.impl.FunctionData;
import com.git.ganksquad.data.types.FunctionType;
import com.git.ganksquad.data.types.ReimuType;
import com.git.ganksquad.exceptions.compiler.NotAFunctionException;
import com.git.ganksquad.exceptions.compiler.ReimuCompileException;
import com.git.ganksquad.exceptions.compiler.TypeException;
import com.git.ganksquad.exceptions.runtime.ReimuRuntimeException;

public class InvokeFunctionExpression implements Expression {
	
	public String symbol;
	public List<Expression> args ;
	public List<ReimuType> argTypes;
	
	public InvokeFunctionExpression(String symbol, List<Expression> args) {
		
		this.symbol = symbol;
		this.args = args;
		this.argTypes = new ArrayList<ReimuType>(this.args.size());
	}
	
	public static InvokeFunctionExpression from(String name, List<Expression> args) {
		
		ParseChecks.RequiredNotNull(name, args);

		return new InvokeFunctionExpression(name, args);
	}


	@Override
	public ReimuType typeCheck(ReimuTypeResolver resolver) throws ReimuCompileException {

		this.trace();
		
		for(Expression e : this.args) {

			ReimuType t = e.typeCheck(resolver);

			this.argTypes.add(t);
		}

		Logger.debug("Type checking function invoke {} {} {}", this.symbol, this.argTypes, this.args);
		ReimuType t = resolver.resolveFunction(this.symbol, this.argTypes);

		if(!(t instanceof FunctionType)) {
			
			throw new NotAFunctionException(this.symbol);
		}

		FunctionType func = (FunctionType)t;

		if(func.argumentTypes.size() != this.args.size()) {
			
			throw new TypeException("Argument number missmatch trying to invoke %s       %s", func, this);
		}

		Iterator<ReimuType> myTypes = this.argTypes.iterator();
		Iterator<ReimuType> funcTypes = func.argumentTypes.iterator();

		while(myTypes.hasNext()) {
			
			if(!funcTypes.next().isAssignableFrom(myTypes.next())) {
				
				throw new TypeException("Argument type missmatch");
			}
		}

		return ((FunctionType)t).returnType;
	}

	@Override
	public Data eval(ReimuRuntime reimuRuntime) throws ReimuRuntimeException {
		
		FunctionData data = reimuRuntime.derefFunction(this.symbol, this.argTypes);
		
		if(data.scope == null) {
			
			throw new ReimuRuntimeException("Cannot invoke function declared with no scope");
		}
		
		List<Data> argData = new ArrayList<>(this.args.size());
		
		for(int i = 0; i < this.args.size(); i++) {

			argData.add(this.args.get(i).eval(reimuRuntime).castTo(data.getType().argumentTypes.get(i)));
		}
		
		// data.scope holds the runtime from when the function was declared
		// we create a subscope of that with our parameter bindings to evaluate it
		ReimuRuntime rt = data.scope.subScope(
				data.params.iterator(),
				argData.iterator());
		
		return data.body.evalPartial(rt).castTo(data.getType().returnType);
	}

	@Override
	public String toString() {
		return this.formatToString(symbol, args);
	}
}
