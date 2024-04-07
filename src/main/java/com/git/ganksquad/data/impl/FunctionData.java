package com.git.ganksquad.data.impl;

import java.util.List;

import com.git.ganksquad.ReimuRuntime;
import com.git.ganksquad.data.ClassKeys;
import com.git.ganksquad.data.Data;
import com.git.ganksquad.expressions.BlockExpression;

public class FunctionData implements Data {
	
	private String name;
	public List<String> params;
	public BlockExpression body;
	public ReimuRuntime scope;
	
	public FunctionData(String name, List<String> params, BlockExpression body) {
		
		this.name = name;
		this.params = params;
		this.body = body;
	}
	
	public String getName() {
		
		return resolveName(this.name, this.params.size());
	}

	public void setName(String name) {

		this.name = name;
	}

	public static String resolveName(String name, int paramCount) {
		
		return name + ';' + paramCount;
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
