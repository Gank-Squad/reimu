package com.git.ganksquad.expressions;

import com.git.ganksquad.ParseChecks;
import com.git.ganksquad.ReimuRuntime;
import com.git.ganksquad.ReimuTypeResolver;
import com.git.ganksquad.data.Data;
import com.git.ganksquad.data.types.ReimuType;
import com.git.ganksquad.exceptions.compiler.ReimuCompileException;
import com.git.ganksquad.exceptions.runtime.ReimuRuntimeException;

public class DerefExpression implements Expression, AssignableExpression {
	
	public String symbol;
	
	public DerefExpression(String name) {

		this.symbol = name;
	}
	
	public static DerefExpression fromString(String name) {
		
		ParseChecks.RequiredNotNull(name);

		return new DerefExpression(name);
	}

	@Override
	public ReimuType typeCheck(ReimuTypeResolver resolver) throws ReimuCompileException {

		this.trace();

		return resolver.resolve(this.symbol);
	}

	@Override
	public Data evalAssign(ReimuRuntime reimuRuntime, Data d) throws ReimuRuntimeException {

		reimuRuntime.assign(this.symbol, d);

		return d;
	}

	@Override
	public Data eval(ReimuRuntime reimuRuntime) throws ReimuRuntimeException {
		
		Data r = reimuRuntime.deref(this.symbol);
		
		if(r == null) {
			throw new ReimuRuntimeException("deref was null???");
		}
		
		return r;
	}

	@Override
	public String toString() {
		return this.formatToString(this.symbol);
	}
}
