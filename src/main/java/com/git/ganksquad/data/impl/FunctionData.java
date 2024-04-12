package com.git.ganksquad.data.impl;

import java.util.List;

import com.git.ganksquad.ReimuNameResolver;
import com.git.ganksquad.ReimuRuntime;
import com.git.ganksquad.data.ClassKeys;
import com.git.ganksquad.data.Data;
import com.git.ganksquad.data.types.ReimuType;
import com.git.ganksquad.expressions.BlockExpression;

public class FunctionData implements Data {
	
	private String name;
	public List<String> params;
	public List<ReimuType> paramTypes;
	public BlockExpression body;
	public ReimuRuntime scope;
	
	public FunctionData(String name, List<String> params, List<ReimuType> paramTypes, BlockExpression body) {
		
		this.name = name;
		this.params = params;
		this.paramTypes = paramTypes;
		this.body = body;
	}

	public FunctionData(ReimuRuntime rt, String name, List<String> params, List<ReimuType> paramTypes, BlockExpression body) {
		
		this(name, params, paramTypes, body);

		this.scope = rt;
	}
	
	public String getName() {
		
		return ReimuNameResolver.getFunctionName(name, paramTypes);
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
}
