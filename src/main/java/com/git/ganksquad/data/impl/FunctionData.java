package com.git.ganksquad.data.impl;

import java.util.List;

import com.git.ganksquad.ReimuNameResolver;
import com.git.ganksquad.ReimuRuntime;
import com.git.ganksquad.data.ClassKeys;
import com.git.ganksquad.data.Data;
import com.git.ganksquad.data.types.AggregateType;
import com.git.ganksquad.data.types.FunctionType;
import com.git.ganksquad.data.types.ReimuType;
import com.git.ganksquad.data.types.SpecialType;
import com.git.ganksquad.exceptions.runtime.CannotCastException;
import com.git.ganksquad.exceptions.runtime.ReimuRuntimeException;
import com.git.ganksquad.expressions.BlockExpression;

public class FunctionData implements Data {
	
	private String name;
	private FunctionType type;
	public List<String> params;
	public BlockExpression body;
	public ReimuRuntime scope;
	
	public FunctionData(String name,FunctionType type,  List<String> params, BlockExpression body) {
		
		this.type = type;
		this.name = name;
		this.params = params;
		this.body = body;
	}

	public FunctionData(ReimuRuntime rt, String name,FunctionType type, List<String> params, BlockExpression body) {
		
		this(name, type, params, body);

		this.scope = rt;
	}
	
	public String getName() {
		return ReimuNameResolver.getFunctionName(name, this.type.argumentTypes);
	}

	public void setName(String name) {

		this.name = name;
	}

	@Override
	public int getClassKey() {

		return ClassKeys.FUNCTION_DATA;
	}
	
	@Override
	public String toString() {
		return "function " + this.name + "(" + this.params + ")";
	}

	@Override
	public FunctionType getType() {
		return this.type;
	}

	@Override
	public Data castTo(ReimuType newType) throws ReimuRuntimeException {
		return Data.commonCast(this, newType);
	}
}
