package com.git.ganksquad.expressions;

import com.git.ganksquad.ReimuRuntime;
import com.git.ganksquad.data.Data;
import com.git.ganksquad.data.NativeMethod;
import com.git.ganksquad.exceptions.ReimuRuntimeException;

public class InvokeNativeExpression implements Expression {
	
	public String[] params;
	public NativeMethod<Data> nativeMethod;
	
	public InvokeNativeExpression(NativeMethod<Data> r) {

		this.nativeMethod = r;
		this.params = new String[0];
	}

	public InvokeNativeExpression(NativeMethod<Data> r, String[] params) {

		this.nativeMethod = r;
		this.params = params;
	}

	@Override
	public Data eval(ReimuRuntime reimuRuntime) throws ReimuRuntimeException {
		
		return this.nativeMethod.call(reimuRuntime, this.params);
	}

}